package com.jvoyatz.weather.app.ui.cities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.CitiesFragmentBinding;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.models.entities.weather.WeatherCurrentConditionEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * Shows the cities saved by the user in the local db.
 * It also provides the ability to search for a certain a city
 */
@AndroidEntryPoint
public class CitiesFragment extends Fragment implements CitiesHandler{

    private WeatherViewModel mWeatherViewModel;
    private CitiesViewModel mViewModel;
    private CitiesFragmentBinding mBinding;
    private CitiesListAdapter adapter;

    @Inject
    Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CitiesFragmentBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWeatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        mViewModel = new ViewModelProvider(requireActivity()).get(CitiesViewModel.class);
        mViewModel.setCurrentCitySelectedLiveData(mWeatherViewModel.getSelectedCityEntityLiveData());

        RecyclerView recyclerView = mBinding.citiesRecyclerview;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CitiesListAdapter(CitiesListAdapter.DIFF_CALLBACK, this, mViewModel);
        recyclerView.setAdapter(adapter);

        adapter.showLoading();
        mViewModel.setTriggerRefreshFavoriteCities(mWeatherViewModel.getTriggerRefreshFavoriteCities());
        mViewModel.getFavoritesCitiesLiveData().observe(getViewLifecycleOwner(), new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(List<CityEntity> cityEntities) {
                adapter.submitList(cityEntities);
            }
        });

//        mViewModel.getWeatherForecastForSavedCities().observe(getViewLifecycleOwner(), new Observer<Map<CityEntity, WeatherCurrentConditionEntity>>() {
//            @Override
//            public void onChanged(Map<CityEntity, WeatherCurrentConditionEntity> map) {
//                Timber.d("onChanged() called with: map = [" + map + "]");
//            }
//        });

        handler.postDelayed(() -> mViewModel.refreshFavoriteCitiesList(), 750);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        adapter.setLifecycleDestroyed();
    }

    @Override
    public void onViewClicked(View view, @NonNull CityEntity item, @Nullable CityEntity selectedItem) {
        switch (view.getId()){
            case R.id.city_weather_forecast_btn:
                break;
            default:
                boolean areItemsTheSame = CitiesListAdapter.DIFF_CALLBACK.areContentsTheSame(item, selectedItem);
                if(!areItemsTheSame) {
                    mWeatherViewModel.setSelectedCityEntityLiveData(item);
                }else{
                    mWeatherViewModel.setSelectedCityEntityLiveData(null);
                }
                break;
        }
    }

    @Override
    public void onFavoriteIconClick(@NonNull CityEntity item) {
        mWeatherViewModel.updateFavoriteCity(item.getName(), item.getRegion(), item.getCountry());
    }
}