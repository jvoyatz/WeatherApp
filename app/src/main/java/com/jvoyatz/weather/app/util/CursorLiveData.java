package com.jvoyatz.weather.app.util;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jvoyatz.weather.app.AppExecutors;

import timber.log.Timber;

/**
 * Custom Livedata used to hold Cursor objects
 */
public class CursorLiveData extends MutableLiveData<Cursor> {

    private final AppExecutors appExecutors;
    private final DataLoader dataLoader;

    public CursorLiveData(AppExecutors appExecutors, DataLoader dataLoader) {
        this.appExecutors = appExecutors;
        this.dataLoader = dataLoader;
    }

    @Override
    protected void onActive() {
        super.onActive();
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = dataLoader.load();
                if(cursor != null){
                    postValue(cursor);
                }else{
                    postValue(null);
                }
            }
        });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }


    public interface DataLoader{
        Cursor load();
    }
}
