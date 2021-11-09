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
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.ui.cities.CitiesHandler;
import com.jvoyatz.weather.app.util.Utils;

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
        Timber.d("loadImage() called with: view = [" + view + "], url = [" + url + "]");
        if(TextUtils.isEmpty(url)){

        }else{
            Glide.with(view.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
        }
    }

    @BindingAdapter({"degrees"})
    public static void rotateDrawable(View view, String degrees){
        Timber.d("rotateDrawable() called with: degrees = [" + degrees + "]");
        if(view instanceof TextView){
            final TextView textView = (TextView) view;
            final BitmapDrawable bitmap = Utils.rotateDrawable(view.getContext(), R.drawable.ic_wind_direction, degrees);
            Timber.d("rotateDrawable: bitmap " + bitmap);
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
