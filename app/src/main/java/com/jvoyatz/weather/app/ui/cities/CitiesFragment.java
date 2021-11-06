package com.jvoyatz.weather.app.ui.cities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jvoyatz.weather.app.databinding.CitiesFragmentBinding;
import com.jvoyatz.weather.app.models.Resource;
import com.jvoyatz.weather.app.models.entities.CityEntity;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * Shows the cities saved by the user in the local db.
 * It also provides the ability to search for a certain a city
 */
@AndroidEntryPoint
public class CitiesFragment extends Fragment {

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

        mViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);
        mViewModel.getSearchLiveData().observe(getViewLifecycleOwner(), new Observer<Resource<List<CityEntity>>>() {
            @Override
            public void onChanged(Resource<List<CityEntity>> listResource) {
                Timber.d("onChanged() called with: listResource = [" + listResource + "]");
            }
        });


        mViewModel.triggerSearch("paris");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}