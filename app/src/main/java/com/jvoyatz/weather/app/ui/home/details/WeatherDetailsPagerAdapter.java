package com.jvoyatz.weather.app.ui.home.details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jvoyatz.weather.app.ui.home.details.pager.WeatherDetailsFullFragment;
import com.jvoyatz.weather.app.ui.home.details.pager.WeatherDetailsHourlyListFragment;

/**
 * Holds child fragment representing each screen
 */
public class WeatherDetailsPagerAdapter extends FragmentStateAdapter {

    public WeatherDetailsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            default:
            case 0:
                return new WeatherDetailsFullFragment();
            case 1:
                return new WeatherDetailsHourlyListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
