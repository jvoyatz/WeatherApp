package com.jvoyatz.weather.app.ui.home;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.jvoyatz.weather.app.AppExecutors;
import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.HomeFragmentBinding;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.weather.WeatherDayEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherEntity;
import com.jvoyatz.weather.app.ui.base.BaseHandler;
import com.jvoyatz.weather.app.util.CrossFader;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * Fragment which shows a screen containing
 * details for the current forecast.
 */
@AndroidEntryPoint
public class HomeFragment extends Fragment implements HomeHandler {

    @Inject
    AppExecutors appExecutors;
    @Inject
    Handler handler;

    private HomeFragmentBinding mBinding;
    private WeatherViewModel mWeatherViewModel;
    private WeatherNextDaysAdapter adapter;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = HomeFragmentBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mWeatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        mBinding.setViewmodel(mWeatherViewModel);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setHandler(this);

        adapter = new WeatherNextDaysAdapter(WeatherNextDaysAdapter.DAYS_DIFF_CALLBACK, this);
        adapter.setAppExecutors(appExecutors);
        mBinding.recyclerView.setAdapter(adapter);

        adapter.showLoading();
        mWeatherViewModel.getWeatherResponseLiveData().observe(getViewLifecycleOwner(), resource -> {
            if(resource != null ) {
                switch (resource.status){
                    case LOADING:
                        adapter.showLoading();
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), R.string.next_days_list_error, Toast.LENGTH_SHORT).show();
//                              break;
                    case SUCCESS:
                        adapter.submitList(resource.data.getWeather());
                        break;
                }
            }else{
                adapter.submitList(null);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        adapter.setLifecycleDestroyed();
        adapter = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewClicked(View view, int position) {
        final NavController navController = Navigation.findNavController(requireView());
        switch (view.getId()){
            case R.id.weather_more_btn:
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToWeatherDetailsFragment(0));
                break;
            case R.id.weather_day_cardview:
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToWeatherDetailsFragment(position));
                break;
        }
    }

}