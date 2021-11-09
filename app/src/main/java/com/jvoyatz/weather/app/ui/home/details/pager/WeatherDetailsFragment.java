package com.jvoyatz.weather.app.ui.home.details.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentFullBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A fragment which provides a more detailed
 * view of the current condition of the weather in the requested
 * city.
 */
@AndroidEntryPoint
public class WeatherDetailsFragment extends Fragment {

    private WeatherDetailsPagerFragmentFullBinding mBinding;

    public WeatherDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WeatherDetailsPagerFragmentFullBinding mBinding = WeatherDetailsPagerFragmentFullBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
