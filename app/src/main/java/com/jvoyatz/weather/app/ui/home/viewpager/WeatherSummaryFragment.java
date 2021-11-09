package com.jvoyatz.weather.app.ui.home.viewpager;

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

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.FragmentWeatherSummaryBinding;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragment which shows a screen containing
 * details for the current forecast.
 */
@AndroidEntryPoint
public class WeatherSummaryFragment extends Fragment {
    @Inject
    AppExecutors appExecutors;
    private FragmentWeatherSummaryBinding mBinding;
    private WeatherViewModel mWeatherViewModel;

    public static WeatherSummaryFragment newInstance() {
        return new WeatherSummaryFragment();
    }

    public WeatherSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentWeatherSummaryBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWeatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        mBinding.setViewmodel(mWeatherViewModel);

        final WeatherNextDaysAdapter adapter = new WeatherNextDaysAdapter(WeatherNextDaysAdapter.DAYS_DIFF_CALLBACK);
        adapter.appExecutors = appExecutors;
        mBinding.recyclerView.setAdapter(adapter);

        mWeatherViewModel.getWeatherResponseLiveData().observe(getViewLifecycleOwner(), new Observer<Resource<WeatherEntity>>() {
            @Override
            public void onChanged(Resource<WeatherEntity> weatherEntityResource) {
                if(weatherEntityResource != null)
                    adapter.submitList(weatherEntityResource.data.getWeather());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}