package com.jvoyatz.weather.app.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.databinding.HomeFragmentNextDayItemBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.ui.base.BaseHandler;
import com.jvoyatz.weather.app.ui.base.DataBoundListAdapter;

import timber.log.Timber;

public class WeatherNextDaysAdapter extends DataBoundListAdapter<WeatherDayEntity, ViewDataBinding> {
    private final HomeHandler handler;
    private AppExecutors appExecutors;

    protected WeatherNextDaysAdapter(@NonNull DiffUtil.ItemCallback<WeatherDayEntity> diffCallback, HomeHandler handler) {
        super(diffCallback);
        this.handler = handler;
       // setHasStableIds(true);
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return HomeFragmentNextDayItemBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ViewDataBinding binding, WeatherDayEntity item, int position) {
        binding.setVariable(BR.day, item);
        binding.setVariable(BR.appExecutors, appExecutors);
        binding.setVariable(BR.position, position);
        binding.setVariable(BR.handler, handler);

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
