package com.jvoyatz.weather.app.ui.home.details.pager;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.BR;
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentHourlyListItemBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.ui.base.DataBoundListAdapter;
import com.jvoyatz.weather.app.util.Objects;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class WeatherHourItemsAdapter extends DataBoundListAdapter<WeatherDayHourEntity, WeatherDetailsPagerFragmentHourlyListItemBinding> {
    public static final int DAY_ID = 1;
    public static final int SUNRISE_ID = 2;
    public static final int SUNSET_ID = 3;
    public static final int NO_ITEMS_ID = 4;
    public static final String DAY_STR = "DAY-WEATHER";
    public static final String DAY_SUNRISE = "DAY-SUNRISE";
    public static final String DAY_SUNSET = "DAY-SUNSET";
    public static final String NO_ITEMS= "NO_ITEMS";

    private final AppExecutors appExecutors;

    protected WeatherHourItemsAdapter(@NonNull DiffUtil.ItemCallback<WeatherDayHourEntity> diffCallback, AppExecutors appExecutors) {
        super(diffCallback);
        this.appExecutors = appExecutors;
    }

    @Override
    public int getItemViewType(int position) {
        final WeatherDayHourEntity item = getItem(position);

        if(TextUtils.equals(item.getWeatherCode(), DAY_STR)){
            viewType = DAY_ID;
        }else if(TextUtils.equals(item.getWeatherCode(), DAY_SUNRISE)){
            viewType = SUNRISE_ID;
        }else if(TextUtils.equals(item.getWeatherCode(), DAY_SUNSET)){
            viewType = SUNSET_ID;
        }else if(TextUtils.equals(item.getWeatherCode(), NO_ITEMS)){
            viewType = NO_ITEMS_ID;
        }else{
            viewType = 0;
        }

        return viewType;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        Timber.d("no items" + viewType);
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case DAY_ID:
                return DataBindingUtil.inflate(inflater, R.layout.weather_details_pager_fragment_hourly_list_day, parent, false);
            case SUNRISE_ID:
                return DataBindingUtil.inflate(inflater, R.layout.weather_details_pager_fragment_hourly_list_sunrise, parent, false);
            case SUNSET_ID:
                return DataBindingUtil.inflate(inflater, R.layout.weather_details_pager_fragment_hourly_list_sunset, parent, false);
            case NO_ITEMS_ID:
                return DataBindingUtil.inflate(inflater, R.layout.recyclerview_exhausted_item, parent, false);
            default:
                return DataBindingUtil.inflate(inflater, R.layout.weather_details_pager_fragment_hourly_list_item, parent, false);
        }
    }

    @Override
    protected void bind(ViewDataBinding binding, WeatherDayHourEntity item) {
        final Context context = binding.getRoot().getContext();
        switch (viewType){
            case SUNRISE_ID:
            case SUNSET_ID:
            case DAY_ID:
            default:
                binding.setVariable(BR.hour, item);
                break;
            case NO_ITEMS_ID:
                binding.setVariable(BR.text, context.getString(R.string.no_weather_hourly_data_found));
                break;
        }
    }

    @Override
    public void submitList(@Nullable List<WeatherDayHourEntity> list) {
        if(Objects.isEmpty(list)){
            list = new ArrayList<>(1);
            list.add(WeatherDayHourEntity.builder().withWeatherCode(NO_ITEMS).build());
        }
        super.submitList(list);
    }
    
    public static final DiffUtil.ItemCallback<WeatherDayHourEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<WeatherDayHourEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull WeatherDayHourEntity oldItem, @NonNull WeatherDayHourEntity newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeatherDayHourEntity oldItem, @NonNull WeatherDayHourEntity newItem) {
            return false;
        }
    };
}
