package com.jvoyatz.weather.app.ui;

import static com.jvoyatz.weather.app.ui.cities.CitiesListAdapter.DIFF_CALLBACK;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.ui.cities.CitiesHandler;
import com.jvoyatz.weather.app.util.Objects;
import com.jvoyatz.weather.app.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import timber.log.Timber;

public class CustomBindingAdapter {
    @BindingAdapter(value = {"bind:isFavorite"})
    public static void favoriteCityImageView(@NonNull ImageView imgView, Boolean isFavorite) {
        if(isFavorite){
            imgView.setImageDrawable(ContextCompat.getDrawable(imgView.getContext(), R.drawable.baseline_favorite_black_24dp));
        }else{
            imgView.setImageDrawable(ContextCompat.getDrawable(imgView.getContext(), R.drawable.baseline_favorite_border_black_24dp));
        }
    }

    @BindingAdapter(value = {"item", "currentSelectedItem"})
    public static void setSelected(View view, CityEntity item, CityEntity currentSelectedItem) {
        if (currentSelectedItem != null) {
            boolean areItemsTheSame = DIFF_CALLBACK.areItemsTheSame(item, currentSelectedItem);
            view.setSelected(areItemsTheSame);
        }else{
            view.setSelected(false);
        }

        TextView t;

    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        if(!TextUtils.isEmpty(url)){
            Glide.with(view.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
        }
    }

    @BindingAdapter({"imageUrl", "appExecutors"})
    public static void loadImage(ImageView view, List<WeatherDayHourEntity> hours, AppExecutors appExecutors) {
        if(!Objects.isEmpty(hours)){
            appExecutors.networkIO().execute(() -> {
                final WeatherDayHourEntity hourEntity = Utils.getClosestWeatherDayHourEntity(hours, System.currentTimeMillis());
                if(hourEntity != null && !TextUtils.isEmpty(hourEntity.getWeatherIconUrl())){
                    appExecutors.ui().execute(() -> Glide.with(view.getContext())
                            .load(hourEntity.getWeatherIconUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(view));
                }
            });
        }
    }

    @BindingAdapter({"degrees"})
    public static void rotateDrawable(View view, String degrees){
        if(view instanceof TextView){
            final TextView textView = (TextView) view;
            final BitmapDrawable bitmap = Utils.rotateDrawable(view.getContext(), R.drawable.ic_wind_direction, degrees);
            if(bitmap != null){
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, bitmap, null);
            }
        }else{
            final BitmapDrawable bitmap = Utils.rotateDrawable(view.getContext(), R.drawable.ic_wind_direction, degrees);
            final ImageView imageView = (ImageView) view;
            imageView.setImageDrawable(bitmap);
        }
    }
}
