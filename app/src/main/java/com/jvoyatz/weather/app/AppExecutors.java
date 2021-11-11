package com.jvoyatz.weather.app;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Thread pools used to execute tasks needed by the app.
 *
 * Tasks regarding network requests have their execution happening in a different thread pool than
 * tasks which need to save data locally
 */
@Singleton
public class AppExecutors {

    //not used since we don't need many network requests
    //private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    //pool size : 1, tasks are being executed sequentially
    private final ExecutorService diskIO;
    //thread pool which can executes 2 tasks in parallel
    private final ExecutorService networkIO;
    //executes runnables on the UI thread
    private final UiThreadExecutor ui;
    private final ScheduledExecutorService periodicIO;

    @Inject
    public AppExecutors() {
        PriorityThreadFactory priorityThreadFactory = new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        diskIO = Executors.newSingleThreadExecutor(priorityThreadFactory);
        networkIO = Executors.newFixedThreadPool(2, priorityThreadFactory);
        ui = new UiThreadExecutor();
        periodicIO = Executors.newSingleThreadScheduledExecutor();
    }

    public ExecutorService diskIO() {
        return diskIO;
    }

    public ExecutorService networkIO() {
        return networkIO;
    }

    public Executor ui() {
        return ui;
    }

    public ScheduledExecutorService periodicIO() {
        return periodicIO;
    }

    public void destroy(){
        try{
            diskIO.shutdown();
            networkIO.shutdown();
            periodicIO.shutdown();
        }catch (Exception e){
            Timber.e(e);
        }
        finally {
            if(!diskIO.isShutdown()){
                diskIO.shutdownNow();
            }

            if(!networkIO.isShutdown()){
                networkIO.shutdownNow();
            }

            if(!periodicIO.isShutdown()){
                periodicIO.shutdownNow();
            }
        }
    }

    /**
     * Provides the ability to execute code on the applications main thread (ui thread)
     */
    private final static class UiThreadExecutor implements Executor {
        private final Handler uiThreadHandler;
        public UiThreadExecutor() {
            uiThreadHandler = new Handler(Looper.getMainLooper());
        }

        /**
         * Executes a Runnable on UI thread.
         * @param command a Runnable instance which contains the code to be executed
         */
        @Override
        public void execute(@NonNull Runnable command) {
            uiThreadHandler.post(command);
        }
    }

    /**
     * Sets the priority of background threads.
     * ThreadPool executors accept ThreadFactory as parameters
     *
     * Why we need to set thread priority?
     * The threads created by application by default have the same priority as the UI thread.
     * Consequently, they compete the UI thread in order to allocate the processor having impact on the performance of UI thread.
     */
    private final static class PriorityThreadFactory implements ThreadFactory {
        private final int threadPriority;

        public PriorityThreadFactory(int threadPriority) {
            this.threadPriority = threadPriority;
        }

        @Override
        @NonNull
        public Thread newThread(Runnable runnable) {
            final Thread thread = new Thread(() -> {
                try {
                    Process.setThreadPriority(threadPriority);
                } catch (Throwable t) {
                    Timber.e(t);
                }
                runnable.run();
            });
            thread.setUncaughtExceptionHandler((t, e) -> Timber.e(e, "thread %s", t));
            return thread;
        }
    }
}
