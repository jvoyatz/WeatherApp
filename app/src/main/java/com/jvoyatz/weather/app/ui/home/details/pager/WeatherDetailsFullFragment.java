package com.jvoyatz.weather.app.ui.home.details.pager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentFullBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.ui.home.details.WeatherDetailsViewModel;
import com.jvoyatz.weather.app.util.AbsentObserver;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * A fragment which provides a more detailed
 * view of the current condition of the weather in the requested
 * city.
 */
@AndroidEntryPoint
public class WeatherDetailsFullFragment extends Fragment {

    private WeatherDetailsPagerFragmentFullBinding mBinding;

    public WeatherDetailsFullFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = WeatherDetailsPagerFragmentFullBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherDetailsViewModel weatherDetailsViewModel = new ViewModelProvider(requireActivity()).get(WeatherDetailsViewModel.class);

        weatherDetailsViewModel.getWeatherCurrentDayEntityLiveData()
                .observe(getViewLifecycleOwner(), weatherDayEntity -> AbsentObserver.create());

        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setViewmodel(weatherDetailsViewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
