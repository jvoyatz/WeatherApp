package com.jvoyatz.weather.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Pair;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.databinding.ActivityWeatherBinding;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.api.WeatherResponse;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.storage.CitiesCursorAdapter;
import com.jvoyatz.weather.app.util.AbsentLiveData;
import com.jvoyatz.weather.app.util.AbsentObserver;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * The only Activity defined for this app. It has SEARCH action its intent-filter declared
 * so as to receive the queries submitted inside here and handle them.
 *
 * @AndroidEntryPoint annotation is used so as for dependencies defined in Hilt components or modules, to be provided
 * in this activity. A hilt component will be generated for this class.
 */
@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity implements CitiesCursorAdapter.OnCityClickListener{

    @Inject
    SearchRecentSuggestions suggestions;
    private WeatherViewModel mWeatherViewModel;
    private CitiesCursorAdapter mAdapter;
    private SearchView searchView;

    @Inject
    WorldWeatherAPI worldWeatherAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @NonNull ActivityWeatherBinding mBinding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolbar);

        // top level destinations
        // home, saved cities
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.citiesFragment).build();
        //When using <fragmentContainerView> we need get NavHostFragment using getSupportFragmentManager()
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = ((NavHostFragment) Objects.requireNonNull(fragment)).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = mBinding.searchview;
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        //searchView.setIconified(false);


        //setting color of text
        EditText editText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
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

        handleIntent(getIntent());

        mWeatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        mWeatherViewModel.getCitiesSuggestions().observe(this, new Observer<Resource<Cursor>>() {
            @Override
            public void onChanged(Resource<Cursor> cursorResource) {
                Timber.d("onChanged() called with: cursorResource = [" + cursorResource + "]");
                try{
                    switch (cursorResource.status){
                        case SUCCESS:
//                            Cursor cursor = cursorResource.data;
//                            Cursor oldCursor = mAdapter.swapCursor(cursor);
//                            if(oldCursor != null)
//                                oldCursor.close();

                            mAdapter = new CitiesCursorAdapter(getApplicationContext(), cursorResource.data, searchView, WeatherActivity.this);
                            searchView.setSuggestionsAdapter(mAdapter);
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
            }
        });

        mWeatherViewModel.getFavoriteCityResultLiveData().observe(this, pair -> {
            if(pair != null && pair.first){
                mWeatherViewModel.searchForCitiesSuggestions(mBinding.searchview.getQuery().toString());
                if(pair.second != null && pair.second)
                    Toast.makeText(WeatherActivity.this, R.string.city_favorite_success, Toast.LENGTH_SHORT).show();
                else if(pair.second != null && !pair.second)
                    Toast.makeText(WeatherActivity.this, R.string.city_not_favorite_success, Toast.LENGTH_SHORT).show();
            }else if(pair != null && !pair.first){
                Toast.makeText(WeatherActivity.this, R.string.city_favorite_error, Toast.LENGTH_SHORT).show();
            }
        });

        mWeatherViewModel.getCurrentCityLiveData().observe(this, AbsentObserver.create());

        worldWeatherAPI.getWeatherForecast("paris", 5, "yes", "json").observe(this, new Observer<ApiResponse<WeatherResponse>>() {
            @Override
            public void onChanged(ApiResponse<WeatherResponse> weatherResponseApiResponse) {

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.weather_activity_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /**
     *  Handles submitted queries via the standard ACTION_SEARCH Intent
     */
    private void handleIntent(Intent intent) {
        Timber.d("handleIntent() called with: intent = [" + intent + "]");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

        }
    }

    @Override
    public void onCitySelected(@NonNull String cityName, @NonNull String region, @NonNull String country, boolean storeAsFavorite){
        mWeatherViewModel.markCityAsFavorite(cityName, region, country);
    }
}