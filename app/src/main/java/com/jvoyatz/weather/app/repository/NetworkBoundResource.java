package com.jvoyatz.weather.app.repository;


import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;

import java.util.Objects;

import timber.log.Timber;


/**
 * A generic class that can provide a resource backed by both the cache and the network.
 *
 * This class was implemented following googlesamples' android-architecture-components.
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt">NetworkBoundResource.kt</a>
 *
 * @param <ResultType>  app local type
 * @param <RequestType> api response type
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();


    @MainThread
    protected NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    protected void init() {
        result.postValue(Resource.loading(null));

        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.postValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    protected void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }


    protected void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            if (response instanceof ApiResponse.ApiSuccessResponse) {
                processFinalSuccessResponse((ApiResponse.ApiSuccessResponse<RequestType>) response);
            } else {
                processApiResponseAsNonSuccess(response, dbSource);
            }
        });
    }

    protected void processFinalSuccessResponse(ApiResponse.ApiSuccessResponse<RequestType> response) {
        appExecutors.diskIO().execute(() -> {
            //Log.d(TAG, "processFinalSuccessResponse: " + Thread.currentThread());
            saveCallResult(processResponse(response));
            final LiveData<ResultType> db = loadFromDb();
            appExecutors.ui().execute(() -> {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                result.addSource(db, newData ->
                        setValue(Resource.success(newData)));
            });
        });

    }

    protected void processApiResponseAsNonSuccess(ApiResponse<?> response, LiveData<ResultType> dbSource) {
        if (response instanceof ApiResponse.ApiEmptyResponse) {
            appExecutors.ui().execute(() -> {
                // reload from disk whatever we had
                result.addSource(loadFromDb(), newData ->
                        setValue(Resource.success(newData)));
            });
        } else if (response instanceof ApiResponse.ApiErrorResponse) {
            ApiResponse.ApiErrorResponse<?> errorResponse = (ApiResponse.ApiErrorResponse<?>) response;
            // onFetchFailed();
            result.addSource(dbSource, newData ->
                    setValue(Resource.error(errorResponse.getErrorMessage(), newData, errorResponse.getException())));
        }
    }

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter
    private void onFetchFailed() {
        Timber.d("onFetchFailed() called");
    }

    @WorkerThread
    protected RequestType processResponse(@NonNull ApiResponse.ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
