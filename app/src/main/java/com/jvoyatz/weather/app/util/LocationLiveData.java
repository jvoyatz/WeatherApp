package com.jvoyatz.weather.app.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.location.LocationListenerCompat;
import androidx.lifecycle.LiveData;

import com.jvoyatz.weather.app.models.Resource;

import java.util.ArrayList;

import timber.log.Timber;


/**
 * Using LiveData to register for location updates when user runs the app as well as for
 * changes in Location Providers action using broadcast receiver.
 * <p>
 * Livedata used because they are lifecycle-aware, so the register of LocationListener
 * is handled through OnActive & onInactive methods
 *
 *
 * Why LocationListenerCompat?
 *  <a href="https://stackoverflow.com/a/69539440/1797960"/>
 */
public class LocationLiveData extends LiveData<Resource<Location>> implements LocationListenerCompat, GpsStatus.Listener {

    public static final String PERM_FINE_LOC_ERROR = "ERROR_PERM_FINE_LOC";
    public static final String PERM_COARSE_LOC_ERROR = "ERROR_PERM_COARSE_LOC";
    public static final String PROVIDERS_DISABLED = "LOCATION_PROVIDERS_DISABLED";

    private final LocationManager locationManager;
    private GnssStatus.Callback gnssStatusCallback;

    @NonNull
    private final Context context;


    public LocationLiveData(@NonNull Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gnssStatusCallback = new GnssStatus.Callback() {
                @Override
                public void onStarted() {
                }

                @Override
                public void onStopped() {
                }

                @Override
                public void onFirstFix(int ttffMillis) {
                }

                @Override
                public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
                    // Timber.d("onSatelliteStatusChanged() called with: status = [" + status + "]");
                }
            };
        }
        final Location lastKnownLocation = getLastKnownLocation();
        if (lastKnownLocation != null) {
            postValue(Resource.success(lastKnownLocation));
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        start();
        registerReceiver();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        stop();
        unregisterReceiver();
    }

    public void onLocationChanged(@NonNull Location location) {
        Timber.d("onLocationChanged() called with: location = [" + location + "]");
        postValue(Resource.success(location));
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }


    /**
     * Start listening for updates
     * If permission is not allowed, then it posts an error message
     */
    public void start() {
        final boolean isLocationProviderEnabled = Utils.isLocationProviderEnabled(context);
        final boolean hasPermission = Utils.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasPermission && isLocationProviderEnabled) {
            try {
                ArrayList<String> providers = getProviders();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && gnssStatusCallback != null) {
                    locationManager.registerGnssStatusCallback(gnssStatusCallback);
                } else {
                    locationManager.addGpsStatusListener(this);
                }

                if (!Objects.isEmpty(providers)) {
                    for (int i = 0; i < providers.size(); i++) {
                        locationManager.requestLocationUpdates(providers.get(i), 5 * 60 * 1000, 500, this);
                    }
                } else {
                    postValue(Resource.error(PROVIDERS_DISABLED, null, null));
                }
            } catch (SecurityException e) {
                Timber.e(e);
                postValue(Resource.error(PERM_FINE_LOC_ERROR, null, null));
            }
        } else if (!hasPermission) {
            postValue(Resource.error(PERM_FINE_LOC_ERROR, null, null));
        } else {
            postValue(Resource.error(PROVIDERS_DISABLED, null, null));
        }
    }

    /**
     * Stop listening to updates
     */
    public void stop() {
        if (locationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && gnssStatusCallback != null) {
                locationManager.unregisterGnssStatusCallback(gnssStatusCallback);
            } else {
                locationManager.removeGpsStatusListener(this);
            }
            locationManager.removeUpdates(this);
        }
    }

    /**
     * Our app expects to register itself for listening to location updates
     * for 2 providers, GPS and NETWORK
     *
     * Returns a list with their names, if available by the device
     */
    public ArrayList<String> getProviders() {
        ArrayList<String> providers = new ArrayList<>(2);
        //gps provider
        String provider = getProviderName(LocationManager.GPS_PROVIDER);
        if (provider != null)
            providers.add(provider);

        //network provider
        provider = getProviderName(LocationManager.NETWORK_PROVIDER);
        if (provider != null)
            providers.add(provider);

        return providers;
    }


    /**
     * Returns a provider matching the given criteria
     */
    private String getProviderName(String provider) {
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAltitudeRequired(true);

        if (/*TextUtils.isEmpty(provider) || */TextUtils.equals(provider, LocationManager.GPS_PROVIDER)) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
        } else if (TextUtils.equals(provider, LocationManager.NETWORK_PROVIDER)) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        return locationManager.getBestProvider(criteria, true);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
            case GpsStatus.GPS_EVENT_STARTED:
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                break;
        }
    }

    /**
     * Makes a check in order to know what is the current provider status,
     * either enabled or not.
     */
    public void checkLocationProviderStatus() {
        final boolean locationEnabled = Utils.isLocationProviderEnabled(context);
        if (locationEnabled) {
            start();
        } else {
            stop();
        }
    }

    /**
     * Returns last known location of this device
     *
     * @return
     * @throws SecurityException
     */
    @SuppressLint("MissingPermission")
    public Location getLastKnownLocation() {
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        if (Utils.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                && Utils.hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION))
            return locationManager.getLastKnownLocation(provider);
        else
            return null;
    }

    /**
     * Receiver for listening to GPS providers changes
     */
    private final BroadcastReceiver gpsProviderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Timber.d("onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
            checkLocationProviderStatus();
        }
    };

    /**
     * Register Broadcast receivers
     */
    private void registerReceiver() {
        context.registerReceiver(gpsProviderReceiver,
                new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    /**
     * Unregister receiver
     */
    private void unregisterReceiver() {
        context.unregisterReceiver(gpsProviderReceiver);
    }
}
