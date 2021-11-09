package com.jvoyatz.weather.app.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

import timber.log.Timber;

/**
 * Found in <@link https://github.com/googlearchive/android-ui-toolkit-demos/blob/master/DataBinding/DataBoundRecyclerView/app/src/main/java/com/example/android/databoundrecyclerview/DataBoundViewHolder.java></@link/>
 * and in <@link https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/ui/common/DataBoundViewHolder.kt/>
 *
 * A generic ViewHolder that wraps a generated ViewDataBinding class.
 *
 * Modified to observe updates in LiveData fields, if used for a particual ViewHolder.
 * ViewHolders are not able to observe changes to livedata field.
 * This is because they are not LifecycleOwners, unlike what happens in fragments or activities. Having that, we have to make the ViewHolders lifecycle owners
 * to achieve this.
 *
 * see <@link https://developer.android.com/topic/libraries/architecture/lifecycle#implementing-lco}
 *
 * @param <T> The type of the ViewDataBinding class
 */
public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder implements LifecycleOwner {

    public final T binding;
    private final LifecycleRegistry lifecycleRegistry;
    private boolean wasPaused = false;

    DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
    }


    public void markCreated() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public void markAttach() {
        Timber.d("markAttach() called : paused " + wasPaused);
        if (wasPaused) {
            lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            wasPaused = false;
        } else {
            lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        }
    }

    public void markDetach() {
        wasPaused = true;
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public void markDestroyed(){
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    /**
     * Creates a new ViewHolder for the given layout file.
     *
     * The provided layout must be using data binding.
     *
     * @param parent The RecyclerView
     * @param layoutId The layout id that should be inflated. Must use data binding
     * @param <T> The type of the Binding class that will be generated for the <code>layoutId</code>.
     * @return A new ViewHolder that has a reference to the binding class
     */
    public static <T extends ViewDataBinding> DataBoundViewHolder<T> create(ViewGroup parent, @LayoutRes int layoutId) {
        T binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new DataBoundViewHolder<>(binding);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}

