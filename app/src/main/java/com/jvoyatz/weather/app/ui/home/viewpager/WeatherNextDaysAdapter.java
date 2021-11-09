package com.jvoyatz.weather.app.ui.home.viewpager;

import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jvoyatz.weather.app.databinding.WeatherForecastDayQuickSummaryItemBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.ui.base.DataBoundListAdapter;
import com.jvoyatz.weather.app.ui.base.DataBoundViewHolder;

public class WeatherNextDaysAdapter extends DataBoundListAdapter<WeatherDayEntity, ViewDataBinding> {

    protected WeatherNextDaysAdapter(@NonNull DiffUtil.ItemCallback<WeatherDayEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return WeatherForecastDayQuickSummaryItemBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ViewDataBinding binding, WeatherDayEntity item) {
        binding.setVariable(BR.day, item);
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
