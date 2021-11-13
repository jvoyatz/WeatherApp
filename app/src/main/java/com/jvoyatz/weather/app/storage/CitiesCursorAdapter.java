package com.jvoyatz.weather.app.storage;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.CursorAdapter;

import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.ActivityWeatherSearchSuggestionItemBinding;


import timber.log.Timber;

public class CitiesCursorAdapter extends CursorAdapter {

    private ActivityWeatherSearchSuggestionItemBinding mBinding;
    private final LayoutInflater mLayoutInflater;
    private final CitySuggestionsHandler mHandler;

    public CitiesCursorAdapter(Context context, Cursor cursor, CitySuggestionsHandler mListener) {
        // autoRequery – If true the adapter will call requery() on the cursor
        // whenever it changes so the most recent data is always displayed. Using true here is discouraged.
        super(context, cursor, false);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mHandler = mListener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        mBinding = ActivityWeatherSearchSuggestionItemBinding.inflate(mLayoutInflater, parent, false);
        return mBinding.getRoot();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try{
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String region = cursor.getString(cursor.getColumnIndexOrThrow("region"));
            String country = cursor.getString(cursor.getColumnIndexOrThrow("country"));
            boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow("isFavorite")) > 0;
            //String regionCountryStr = region + ", " + country;

            mBinding.setCity(name);
            mBinding.setRegion(region);
            mBinding.setCountry(country);
            mBinding.setHandler(mHandler);

            //mBinding.cityName.setText(name);
            //mBinding.cityRegionCountry.setText(regionCountryStr);

            if(isFavorite){
                mBinding.cityAddIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_favorite_black_24dp));
            }
            //mBinding.cityAddIcon.setOnClickListener(v -> );

//            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // mListener.on(name, false);
//                }
//            });
        }catch (Exception e){
            Timber.e(e);
        }
    }



    /**
     * Listener implemented by WeatherActivity, invoked when user clicks on a suggested item
     */
    public interface CitySuggestionsHandler {
        void onSuggestedCitySelected(@NonNull String cityName, @NonNull String region, @NonNull String country, boolean storeAsFavorite);
    }
}
