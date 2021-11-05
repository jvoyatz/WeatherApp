package com.jvoyatz.weather.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jvoyatz.weather.app.databinding.ActivityWeatherBinding;
import com.jvoyatz.weather.app.util.AndroidUtil;

import java.util.Objects;

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
public class WeatherActivity extends AppCompatActivity {

    private SearchRecentSuggestions suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @NonNull ActivityWeatherBinding mBinding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
      // setSupportActionBar(mBinding.toolbar);
        // top level destinations
        // home, saved cities
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.citiesFragment).build();
        //When using <fragmentContainerView> we need get NavHostFragment using getSupportFragmentManager()
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = ((NavHostFragment) Objects.requireNonNull(fragment)).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_activity_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);


        EditText editText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);

        //searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                try {
                    Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
                    int index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
                    String suggestion = cursor.getString(index);
                    if(!TextUtils.isEmpty(suggestion)){
                        searchView.setQuery(suggestion, false);
                        return true;
                    }
                } catch (IllegalArgumentException e) {
                    Timber.e(e);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
    }
}