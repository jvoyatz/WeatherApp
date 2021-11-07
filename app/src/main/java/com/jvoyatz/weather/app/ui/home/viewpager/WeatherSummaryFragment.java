package com.jvoyatz.weather.app.ui.home.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.FragmentWeatherSummaryBinding;

/**
 * Fragment which shows a screen containing
 * details for the current forecast.
 */
public class WeatherSummaryFragment extends Fragment {

    private FragmentWeatherSummaryBinding mBinding;

    public static WeatherSummaryFragment newInstance() {
        return new WeatherSummaryFragment();
    }

    public WeatherSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentWeatherSummaryBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}