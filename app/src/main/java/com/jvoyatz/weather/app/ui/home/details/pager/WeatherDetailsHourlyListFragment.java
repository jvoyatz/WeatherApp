package com.jvoyatz.weather.app.ui.home.details.pager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.WeatherDetailsPagerFragmentHourlyListBinding;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.ui.home.details.WeatherDetailsViewModel;
import com.jvoyatz.weather.app.util.Objects;
import com.jvoyatz.weather.app.util.Utils;

import java.util.List;

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
    @Inject
    Handler handler;

    private WeatherDetailsPagerFragmentHourlyListBinding mBinding;
    private WeatherDetailsViewModel mViewModel;
    private WeatherHourItemsAdapter adapter;


    public WeatherDetailsHourlyListFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mBinding = WeatherDetailsPagerFragmentHourlyListBinding.inflate(inflater, container, false);
         return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(mBinding.bottomsheet.bottomSheetLayout);

        adapter = new WeatherHourItemsAdapter(WeatherHourItemsAdapter.DIFF_CALLBACK, appExecutors, this);
        mBinding.weatherDayDetailsRecyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        mBinding.weatherDayDetailsRecyclerview.addItemDecoration(dividerItemDecoration);

        mViewModel = new ViewModelProvider(requireActivity()).get(WeatherDetailsViewModel.class);

        adapter.showLoading();
        mViewModel.getWeatherDayHourEntityLiveData().observe(getViewLifecycleOwner(), new Observer<List<WeatherDayHourEntity>>() {
            @Override
            public void onChanged(List<WeatherDayHourEntity> entities) {
                try{
                    adapter.submitList(entities);
                    if(Objects.isEmpty(entities)){
                        mBinding.hourCoordinatorLayout.setVisibility(View.GONE);
                    }else{

                        handler.postDelayed(() -> {
                            onViewClicked(null, adapter.getCurrentList().get(4));
                        }, 500);
                        mBinding.hourCoordinatorLayout.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    Timber.e(e);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        adapter.setLifecycleDestroyed();
    }

    @Override
    public void onViewClicked(View view, WeatherDayHourEntity entity) {
        Timber.d("onViewClicked() called with: view = [" + view + "], entity = [" + entity.getTime() + "]");
        mBinding.bottomsheet.setHour(entity);
        if(mViewModel.weatherEntityLiveData != null && mViewModel.weatherEntityLiveData.getValue() != null) {
            final WeatherEntity data = mViewModel.weatherEntityLiveData.getValue().data;

            if (data != null && data.getTimezone() != null) {
                String time = Utils.formatHourDateTime(Utils.parseFullStr(entity.getTime()));
                mBinding.bottomsheet.setDay(time);
            }
        }
    }

//    final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
//
//        @Override
//        public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            Timber.d("onStateChanged() called with: bottomSheet = [" + bottomSheet + "], newState = [" + newState + "]");
//            switch (newState){
//                case 3:
//                case 6:
////                        final Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.card);
////                        TransitionDrawable crossfader = new TransitionDrawable(new Drawable[]{drawable, null});
////                        mBinding.bottomsheet.bottomSheetLayoutConstr.setBackground(drawable);
////                        crossfader.startTransition(1000);
//
//                    break;
//                case 4:
//                    //mBinding.bottomsheet.bottomSheetLayoutConstr.setBackground(null);
//                    break;
//            }
//        }
//
//        @Override
//        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//        }
//    };
//        sheetBehavior.addBottomSheetCallback(bottomSheetCallback);
}