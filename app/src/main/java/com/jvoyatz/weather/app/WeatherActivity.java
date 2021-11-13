package com.jvoyatz.weather.app;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jvoyatz.weather.app.databinding.ActivityWeatherBinding;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.storage.CitiesCursorAdapter;
import com.jvoyatz.weather.app.util.AbsentObserver;
import com.jvoyatz.weather.app.util.CrossFader;
import com.jvoyatz.weather.app.util.LocationLiveData;
import com.jvoyatz.weather.app.util.Utils;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * The only Activity defined for this app. It has SEARCH action its intent-filter declared
 * so as to receive the queries submitted inside here and handle them.
 *
 * AndroidEntryPoint annotation is used so as for dependencies defined in Hilt components or modules, to be provided
 * in this activity. A hilt component will be generated for this class.
 */
@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity implements CitiesCursorAdapter.OnSuggestedCityClickListener {

    @Inject
    SearchRecentSuggestions suggestions;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private WeatherViewModel mWeatherViewModel;
    private CitiesCursorAdapter mAdapter;

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                        Timber.d("check %s", result);

                        Boolean isFineLocationGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                        Boolean isCoarseLocationGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);

                        if ((isFineLocationGranted != null && isFineLocationGranted)  ||
                                (isCoarseLocationGranted != null && isCoarseLocationGranted)) {
                            mWeatherViewModel.startListeningLocationUpdates();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @NonNull ActivityWeatherBinding mBinding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //setSupportActionBar(mBinding.toolbar);
        initSearchView(mBinding.searchview);

        final CrossFader crossFader = new CrossFader(mBinding.crossfadeView, mBinding.getRoot(), 1000, this);
        crossFader.crossfade();

        mWeatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // top level destinations
        // home, saved cities
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.citiesFragment).build();
        //When using <fragmentContainerView> we need get NavHostFragment using getSupportFragmentManager()
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = ((NavHostFragment) Objects.requireNonNull(fragment)).getNavController();
       // NavigationUI.setupWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.weatherDetailsFragment:
                        mBinding.searchview.setVisibility(View.GONE);
                        break;
                    default:
                        mBinding.searchview.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //observations
        mWeatherViewModel.getCitiesSuggestions().observe(this, cursorResource -> {
            try{
                switch (cursorResource.status){
                    case SUCCESS:
                        mAdapter = new CitiesCursorAdapter(getApplicationContext(), cursorResource.data, mBinding.searchview, WeatherActivity.this);
                        mBinding.searchview.setSuggestionsAdapter(mAdapter);
                        break;
                    case ERROR:
                        Toast.makeText(WeatherActivity.this, R.string.cities_suggestions_no_results_found, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }catch (Exception e){
                Timber.e(e);
            }
        });

        //observing adding city action result
        mWeatherViewModel.getFavoriteCityResultLiveData().observe(this, pair -> {
            if(pair != null && pair.first){
                mWeatherViewModel.searchForCitiesSuggestions(mBinding.searchview.getQuery().toString());
                mWeatherViewModel.setTriggerRefreshFavoriteCities();
                if(pair.second != null && pair.second)
                    Toast.makeText(WeatherActivity.this, R.string.city_favorite_success, Toast.LENGTH_SHORT).show();
                else if(pair.second != null)
                    Toast.makeText(WeatherActivity.this, R.string.city_not_favorite_success, Toast.LENGTH_SHORT).show();
            }else if(pair != null){
                Toast.makeText(WeatherActivity.this, R.string.city_favorite_error, Toast.LENGTH_SHORT).show();
            }
        });

        //location wrapped in a Resource instance
        mWeatherViewModel.getLocationLiveData().observe(this, new Observer<Resource<Location>>() {
            @Override
            public void onChanged(Resource<Location> resource) {
                if(resource != null){
                    switch (resource.status){
                        case SUCCESS:
                            break;
                        case ERROR:
                            final String message = resource.message;
                            if(TextUtils.equals(message, LocationLiveData.PERM_FINE_LOC_ERROR)){
                                acquirePermissions();
                            }else if(TextUtils.equals(message, LocationLiveData.PROVIDERS_DISABLED)){
                                Toast.makeText(getApplicationContext(), R.string.location_providers_not_enabled, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case LOADING:
                            break;
                    }
                }
            }
        });

        mWeatherViewModel.getCityEntityLiveData().observe(this, new Observer<CityEntity>() {
            @Override
            public void onChanged(CityEntity cityEntity) {
                Timber.d("onChanged() called with: cityEntity = [" + cityEntity + "]");
            }
        });
        mWeatherViewModel.getSelectedCityEntityLiveData().observe(this, AbsentObserver.create());
        mWeatherViewModel.getWeatherResponseLiveData().observe(this, AbsentObserver.create());

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    /**
     * Calls the insertCity method from {@link WeatherViewModel} in order to add
     * this city to the local db.
     * @param cityName name of the city
     * @param region the region where it belongs
     * @param country the country where city belongs
     * @param storeAsFavorite declaring whether this city will be added or removed from the db (if already added)
     */
    @Override
    public void onSuggestedCitySelected(@NonNull String cityName, @NonNull String region, @NonNull String country, boolean storeAsFavorite){
        Timber.d("onSuggestedCitySelected() called with: cityName = [" + cityName + "], region = [" + region + "], country = [" + country + "], storeAsFavorite = [" + storeAsFavorite + "]");
        mWeatherViewModel.updateFavoriteCity(cityName, region, country);
    }

    public void initSearchView(SearchView searchView){
        //setting color of text
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }
            @Override
            public boolean onSuggestionClick(int position) {
                try{
                    Cursor cursor = (Cursor) mAdapter.getItem(position);
                    String txt = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    searchView.setQuery(txt, true);
                    return true;
                }catch (Exception e){
                    Timber.e(e);
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() < 3 ) {
                    return false;
                }
                mWeatherViewModel.searchForCitiesSuggestions(newText);
                return true;
            }
        });
    }

//    private void acquirePermissions() {
//        Context applicationContext = getApplicationContext();
//        if (Utils.hasPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
//                || PermissionUtils.isPermissionAcquired(applicationContext, Manifest.permission.CAMERA)) {
//            Log.d(TAG, "acquirePermissions() called#granted");
//            // You can use the API that requires the permission.
//            // performAction(...);
//        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Log.d(TAG, "acquirePermissions() called#should");
//            permissionsDialog = DialogUtils.getDialog(this, R.string.msg_warning,
//                    R.string.msg_permission, R.string.msg_ok,
//                    R.string.msg_cancel,
//                    getString(R.string.msg_no_thanks), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            launchers.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA});
//                        }
//                    }, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//            permissionsDialog.show();
//
//        } else {
//            Log.d(TAG, "acquirePermissions() called#requestpermissions");
//            // You can directly ask for the permission.
//            //ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE);
//            launchers.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA});
//        }
//    }

    private void acquirePermissions() {
        Context applicationContext = getApplicationContext();
        if (Utils.hasPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Timber.d("acquirePermissions() called#granted");
            mWeatherViewModel.startListeningLocationUpdates();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Timber.d("acquirePermissions() called#should");
    //            permissionsDialog = DialogUtils.getDialog(this, R.string.msg_warning,
    //                    R.string.msg_permission, R.string.msg_ok,
    //                    R.string.msg_cancel,
    //                    getString(R.string.msg_no_thanks), new DialogInterface.OnClickListener() {
    //                        @Override
    //                        public void onClick(DialogInterface dialog, int which) {
    //                            launchers.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA});
    //                        }
    //                    }, new DialogInterface.OnClickListener() {
    //                        @Override
    //                        public void onClick(DialogInterface dialog, int which) {
    //
    //                        }
    //                    });
    //            permissionsDialog.show();

        } else {
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }
}