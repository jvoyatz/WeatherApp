package com.jvoyatz.weather.app.ui.home.details;

import android.os.Bundle;
import android.util.Log;
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
import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * Home Screen of the Weather App.
 * <p>
 * Provides info regarding the forecast of the selected city.
 */
@AndroidEntryPoint
public class WeatherDetailsPagerFragment extends Fragment {

    private WeatherDetailsPagerFragmentBinding mBinding;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = WeatherDetailsPagerFragmentBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int dayIndex = WeatherDetailsPagerFragmentArgs.fromBundle(getArguments()).getDayIndex();

        WeatherDetailsViewModel mViewModel = new ViewModelProvider(requireActivity()).get(WeatherDetailsViewModel.class);
        WeatherViewModel weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        WeatherDetailsPagerAdapter adapter = new WeatherDetailsPagerAdapter(getChildFragmentManager(), getViewLifecycleOwner().getLifecycle(), dayIndex);
        mBinding.weatherDetailsPager.setAdapter(adapter);
        tabLayoutMediator = new TabLayoutMediator(mBinding.weatherDetailsTabLayout, mBinding.weatherDetailsPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String text;
                switch (position){
                    default:
                    case 0:
                        text = getString(R.string.weather_today);
                        break;
                    case 1:
                        text = getString(R.string.weather_per_hour);
                        break;
                }
                tab.setText(text);
            }
        });
        tabLayoutMediator.attach();

        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setViewmodel(mViewModel);

        mViewModel.getWeatherEntityLiveData(weatherViewModel.getWeatherResponseLiveData(), dayIndex)
                .observe(getViewLifecycleOwner(), weatherEntity -> {});
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