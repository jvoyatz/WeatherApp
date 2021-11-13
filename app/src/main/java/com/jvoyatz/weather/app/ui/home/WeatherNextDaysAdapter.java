package com.jvoyatz.weather.app.ui.home;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.HomeFragmentNextDayItemBinding;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.ui.base.BaseHandler;
import com.jvoyatz.weather.app.ui.base.DataBoundListAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class WeatherNextDaysAdapter extends DataBoundListAdapter<WeatherDayEntity, ViewDataBinding> {
    private final HomeHandler handler;
    private AppExecutors appExecutors;

    public static final int EMPTY_ID = 1;
    public static final String EMPTY_STR = "EMPTY";

    protected WeatherNextDaysAdapter(@NonNull DiffUtil.ItemCallback<WeatherDayEntity> diffCallback, HomeHandler handler) {
        super(diffCallback);
        this.handler = handler;
       // setHasStableIds(true);
    }


    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case LOADING_ID:
                return DataBindingUtil.inflate(inflater, R.layout.recyclerview_loading_item, parent, false);
            case EMPTY_ID:
                return DataBindingUtil.inflate(inflater, R.layout.recyclerview_exhausted_item, parent, false);
            default:
                return HomeFragmentNextDayItemBinding.inflate(inflater, parent, false);
        }
    }

    /**
     * Overriding this method to know the type of the view during processing
     * and execute the appropriate tasks for binding and setting its content
     *
     * @param position index as added in {@link ListAdapter#getCurrentList()}
     *
     * @return an integer defining the viewtype, custom fields used in this class
     */
    @Override
    public int getItemViewType(int position) {
        WeatherDayEntity item = getItem(position);

        if(TextUtils.equals(item.getDate(), EMPTY_STR)){
            viewType = EMPTY_ID;
        }else if(TextUtils.equals(item.getDate(), LOADING_STR)){
            viewType = LOADING_ID;
        }else{
            viewType = super.getItemViewType(position);
        }

        return viewType;
    }

    @Override
    protected void bind(ViewDataBinding binding, WeatherDayEntity item, int position) {
        switch (viewType){
            case EMPTY_ID:
                binding.setVariable(BR.text, binding.getRoot().getContext().getString(R.string.next_days_list_empty));
                break;
            default:
                binding.setVariable(BR.day, item);
                binding.setVariable(BR.appExecutors, appExecutors);
                binding.setVariable(BR.position, position);
                binding.setVariable(BR.handler, handler);
                break;
        }
    }

    /**
     * We override this method to manipulate the content of the list parameter
     * either to add an item when list is empty, in order to inform the user
     * that no favorites found or to add a 'header' item on the shown list
     *
     * @param list user's favorite cities
     */
    @Override
    public void submitList(@Nullable List<WeatherDayEntity> list) {
        if(list == null || list.isEmpty()) {
            if (list == null) {
                list = new ArrayList<>(1);
            }
            list.add(WeatherDayEntity.builder().withDate(EMPTY_STR).build());
        }
        super.submitList(list);
    }

    /**
     * adds a loading item in the list for this recyclerview
     */
    public void showLoading() {
        ArrayList<WeatherDayEntity> list = new ArrayList<>(1);
        list.add(WeatherDayEntity.builder().withDate(LOADING_STR).build());
        submitList(list);
    }

    public void setAppExecutors(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public static  DiffUtil.ItemCallback<WeatherDayEntity> DAYS_DIFF_CALLBACK = new DiffUtil.ItemCallback<WeatherDayEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull WeatherDayEntity oldItem, @NonNull WeatherDayEntity newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeatherDayEntity oldItem, @NonNull WeatherDayEntity newItem) {
            return false;
        }
    };
}
