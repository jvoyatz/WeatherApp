package com.jvoyatz.weather.app.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.databinding.HomeFragmentNextDayItemBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.ui.base.DataBoundListAdapter;

public class WeatherNextDaysAdapter extends DataBoundListAdapter<WeatherDayEntity, ViewDataBinding> {
    public AppExecutors appExecutors;
    protected WeatherNextDaysAdapter(@NonNull DiffUtil.ItemCallback<WeatherDayEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return HomeFragmentNextDayItemBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ViewDataBinding binding, WeatherDayEntity item) {
        binding.setVariable(BR.day, item);
        binding.setVariable(BR.appExecutors, appExecutors);
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
