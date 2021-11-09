package com.jvoyatz.weather.app.ui;

import static com.jvoyatz.weather.app.ui.cities.CitiesListAdapter.DIFF_CALLBACK;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.ui.cities.CitiesHandler;

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
    }
}
