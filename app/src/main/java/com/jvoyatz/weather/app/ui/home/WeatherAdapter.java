package com.jvoyatz.weather.app.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jvoyatz.weather.app.ui.home.viewpager.WeatherSummaryFragment;

/**
 * Holds child fragment representing each screen
 */
public class WeatherAdapter extends FragmentStateAdapter {

    public WeatherAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
            case 1:
            default:
                return WeatherSummaryFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
