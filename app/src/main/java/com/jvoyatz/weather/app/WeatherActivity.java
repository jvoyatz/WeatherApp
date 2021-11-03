package com.jvoyatz.weather.app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jvoyatz.weather.app.databinding.ActivityWeatherBinding;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Retrofit;

/**
 * The only Activity defined for this app.
 * It contains a core part
 * This fragment is aware of the possible destinations defined in the nav_graph.xml and
 *
 * @AndroidEntryPoint annotation is used so as for dependencies defined in Hilt components or modules, to be provided
 * in this activity. A hilt component will be generated for this class.
 */
@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    @Inject
    Retrofit retrofit;
    @ApplicationContext
    Context context;
    private ActivityWeatherBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Log.d(TAG, "onCreate: " + context);

        // top level destinations
        // home, saved cities
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.citiesFragment).build();
        //When using <fragmentContainerView> we need get NavHostFragment using getSupportFragmentManager()
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = ((NavHostFragment) Objects.requireNonNull(fragment)).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);
    }
}