package com.jvoyatz.weather.app.ui.home.details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jvoyatz.weather.app.ui.home.details.pager.WeatherDetailsFullFragment;

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
            case 0:
            case 1:
            default:
                //return HomeFragment.newInstance();
                return new WeatherDetailsFullFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
