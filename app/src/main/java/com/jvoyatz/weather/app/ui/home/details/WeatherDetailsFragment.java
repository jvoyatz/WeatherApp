package com.jvoyatz.weather.app.ui.home.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.HomeFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Home Screen of the Weather App.
 * <p>
 * Provides info regarding the forecast of the selected city.
 */
@AndroidEntryPoint
public class WeatherDetailsFragment extends Fragment {

    private WeatherDetailsViewModel mViewModel;
    private HomeFragmentBinding mBinding;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = HomeFragmentBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeatherDetailsViewModel.class);
        WeatherDetailsPagerAdapter adapter = new WeatherDetailsPagerAdapter(getChildFragmentManager(), getViewLifecycleOwner().getLifecycle());
//        mBinding.viewpager.setAdapter(adapter);
//        tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout, mBinding.viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                String text;
//                switch (position){
//                    default:
//                    case 0:
//                        text = getString(R.string.weather_today);
//                        break;
//                    case 1:
//                        text = getString(R.string.weather_per_hour);
//                        break;
//                }
//                tab.setText(text);
//            }
//        });
//        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(tabLayoutMediator.isAttached()){
            tabLayoutMediator.detach();
        }
        mBinding = null;
    }
}