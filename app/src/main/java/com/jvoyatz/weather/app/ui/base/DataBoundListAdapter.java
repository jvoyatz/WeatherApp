package com.jvoyatz.weather.app.ui.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jvoyatz.weather.app.ui.cities.CitiesViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


/**
 * Generic Adapter as used in Android-Components repo, in GithubBrowserSample.
 * see here <@link https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/ui/common/DataBoundListAdapter.kt}
 *
 * In this sample's class, the class definition is as shown in the next line
 *      class DataBoundListAdapter<T, V extends ViewDataBinding> extends ListAdapter<T, DataBoundViewHolder<V>> {
 *
 * But here, we modify the class definiton in order our methods
 *  a) createBinding,
 *  b) bind,
 *  as well {@link ListAdapter} methods such as
 *  {@link ListAdapter#onBindViewHolder(RecyclerView.ViewHolder, int)} not to be limited
 *  to take only on LayoutBinding, but more than one when we want to
 *  have a recyclerview with multiple viewTypes.
 *
 *  That means, that the default method definitions would be:
 *  1)  abstract V createBinding(ViewGroup parent, int viewType);
 *  2) void onBindViewHolder(DataBoundViewHolder<ViewDataBinding> holder, int position) {
 *
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
 */
public abstract class DataBoundListAdapter<T, V extends ViewDataBinding> extends ListAdapter<T, DataBoundViewHolder<ViewDataBinding>> {

    //keeping the value of the ViewType in a field
    //so as to be able to pass different values
    //when executing binding
    //however sometimes it may not be needed, in a case of single layout adapter
    protected int viewType;
    private final List<DataBoundViewHolder<ViewDataBinding>> viewHolders = new ArrayList<>();

    protected DataBoundListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @Override
    public final DataBoundViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = createBinding(parent, viewType);
        DataBoundViewHolder<ViewDataBinding> holder = new DataBoundViewHolder<>(binding);
        holder.markCreated();
        holder.binding.setLifecycleOwner(holder);
        viewHolders.add(holder);

        return holder;
    }

    /**
     * Abstract method, since we want several adapters having different design, for instance
     * either single or more than one view types etc.
     */
    protected abstract ViewDataBinding createBinding(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(DataBoundViewHolder<ViewDataBinding> holder, int position) {
        bind(holder.binding, getItem(position), position);
        holder.binding.executePendingBindings();
    }

    /**
     * Each screen adapter might have different fields set, or
     * may need to set more than one variables in its layout.
     */
    protected abstract void bind(ViewDataBinding binding, T item, int position);

    @Override
    public void onViewAttachedToWindow(@NonNull DataBoundViewHolder<ViewDataBinding> holder) {
        super.onViewAttachedToWindow(holder);
        holder.markAttach();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull DataBoundViewHolder<ViewDataBinding> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.markDetach();
    }

    /**
     * Call this method in {@link Fragment#onDestroyView()}
     */
    public void setLifecycleDestroyed() {
        if (!viewHolders.isEmpty()) {
            for (DataBoundViewHolder<ViewDataBinding> viewHolder : viewHolders) {
                viewHolder.markDestroyed();
            }
            viewHolders.clear();
        }
    }
}
