package com.jvoyatz.weather.app.ui.home.details.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentHourlyListBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.ui.home.details.WeatherDetailsViewModel;
import com.jvoyatz.weather.app.util.Utils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * A screen which shows to the user a list of
 * hourly weather items
 */
@AndroidEntryPoint
public class WeatherDetailsHourlyListFragment extends Fragment implements WeatherHourItemsHandler {


    @Inject
    AppExecutors appExecutors;
    private WeatherDetailsPagerFragmentHourlyListBinding mBinding;
    private WeatherDetailsViewModel mViewModel;

    public WeatherDetailsHourlyListFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mBinding = WeatherDetailsPagerFragmentHourlyListBinding.inflate(inflater, container, false);
         return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(mBinding.bottomsheet.bottomSheetLayout);

        WeatherHourItemsAdapter adapter = new WeatherHourItemsAdapter(WeatherHourItemsAdapter.DIFF_CALLBACK, appExecutors, this);
        mBinding.weatherDayDetailsRecyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        mBinding.weatherDayDetailsRecyclerview.addItemDecoration(dividerItemDecoration);

        mViewModel = new ViewModelProvider(requireActivity()).get(WeatherDetailsViewModel.class);

        mViewModel.getWeatherDayHourEntityLiveData().observe(getViewLifecycleOwner(), adapter::submitList);


        final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Timber.d("onStateChanged() called with: bottomSheet = [" + bottomSheet + "], newState = [" + newState + "]");
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Timber.d("onSlide() called with: bottomSheet = [" + bottomSheet + "], slideOffset = [" + slideOffset + "]");
            }
        };
        sheetBehavior.addBottomSheetCallback(bottomSheetCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onViewClicked(View view, WeatherDayHourEntity entity) {
        mBinding.bottomsheet.setHour(entity);
        if(mViewModel.weatherEntityLiveData != null && mViewModel.weatherEntityLiveData.getValue() != null) {
            final WeatherEntity data = mViewModel.weatherEntityLiveData.getValue().data;

            if (data != null && data.getTimezone() != null) {
                String time = Utils.formatHourDateTime(Utils.parseFullStr(entity.getTime()));
                mBinding.bottomsheet.setDay(time);
            }
        }
    }
}