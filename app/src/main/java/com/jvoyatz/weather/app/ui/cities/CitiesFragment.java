package com.jvoyatz.weather.app.ui.cities;

import android.os.Bundle;
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

import com.jvoyatz.weather.app.WeatherViewModel;
import com.jvoyatz.weather.app.databinding.CitiesFragmentBinding;
import com.jvoyatz.weather.app.models.entities.CityEntity;

import java.util.List;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CitiesFragmentBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWeatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        mViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);
        mViewModel.setCurrentCitySelectedLiveData(mWeatherViewModel.getSelectedCityEntityLiveData());

        RecyclerView recyclerView = mBinding.citiesRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CitiesListAdapter adapter = new CitiesListAdapter(CitiesListAdapter.DIFF_CALLBACK, this, mViewModel);

        recyclerView.setAdapter(adapter);

        mViewModel.getFavoritesCitiesLiveData().observe(getViewLifecycleOwner(), new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(List<CityEntity> cityEntities) {
                Timber.d("onChanged() called with: cityEntities = [" + cityEntities + "]");
                adapter.submitList(cityEntities);
            }
        });

        mWeatherViewModel.getFavoriteCityResultLiveData().observe(getViewLifecycleOwner(), new Observer<Pair<Boolean, Boolean>>() {
            @Override
            public void onChanged(Pair<Boolean, Boolean> pair) {
                Timber.d("onChanged() called with: pair = [" + pair + "]");
                if(pair != null){
                    mViewModel.refreshFavoriteCitiesList();
                }
            }
        });
        mViewModel.refreshFavoriteCitiesList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onViewClicked(@NonNull CityEntity item, @Nullable CityEntity selectedItem) {
        boolean areItemsTheSame = CitiesListAdapter.DIFF_CALLBACK.areContentsTheSame(item, selectedItem);
        if(!areItemsTheSame) {
            mWeatherViewModel.setSelectedCityEntityLiveData(item);
        }else{
            mWeatherViewModel.setSelectedCityEntityLiveData(null);
        }
    }


    @Override
    public void onFavoriteIconClick(@NonNull CityEntity item) {
        mWeatherViewModel.updateFavoriteCity(item.getName(), item.getRegion(), item.getCountry());
    }
}