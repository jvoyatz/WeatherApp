package com.jvoyatz.weather.app.ui.home.details.pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentHourlyListBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.ui.home.details.WeatherDetailsPagerAdapter;
import com.jvoyatz.weather.app.ui.home.details.WeatherDetailsViewModel;
import com.jvoyatz.weather.app.util.AbsentObserver;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * A screen which shows to the user a list of
 * hourly weather items
 */
@AndroidEntryPoint
public class WeatherDetailsHourlyListFragment extends Fragment {
    private static String DAY_INDEX_TAG = "DAY_INDEX_TAG";
    @Inject
    AppExecutors appExecutors;
    private WeatherDetailsPagerFragmentHourlyListBinding mBinding;

    public WeatherDetailsHourlyListFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mBinding = WeatherDetailsPagerFragmentHourlyListBinding.inflate(inflater, container, false);
         return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherHourItemsAdapter adapter = new WeatherHourItemsAdapter(WeatherHourItemsAdapter.DIFF_CALLBACK, appExecutors);
        mBinding.weatherDayDetailsRecyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        mBinding.weatherDayDetailsRecyclerview.addItemDecoration(dividerItemDecoration);

        WeatherDetailsViewModel mViewModel = new ViewModelProvider(requireActivity()).get(WeatherDetailsViewModel.class);

        mViewModel.getWeatherDayHourEntityLiveData().observe(getViewLifecycleOwner(), weatherDayHourEntities -> {
            adapter.submitList(weatherDayHourEntities);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}