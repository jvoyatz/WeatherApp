package com.jvoyatz.weather.app.ui.cities;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jvoyatz.weather.app.R;
import com.jvoyatz.weather.app.databinding.CitiesFragmentRecyclerviewItemBinding;
import com.jvoyatz.weather.app.models.entities.CityEntity;
import com.jvoyatz.weather.app.ui.base.DataBoundListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ListAdapter for CitiesFragment. It uses ListAdapter, an extension of RecyclerViewAdapter, for representing list data in a RecyclerView.
 *
 * This recyclerview here, supports multiple view types in its content.
 *
 * The common item layout,for displaying favorite cities along with a header layout placed on the top of the RecyclerView.
 * If list is empty, then we know about that by overriding certain methods in this class, and displaying an appropriate
 * item notifying us that our list is empty.
 *
 * It also takes an argument in the constructor a {@link java.util.logging.Handler} instance, used
 * to handle click events through databinding.
 */
public class CitiesListAdapter extends DataBoundListAdapter<CityEntity, CitiesFragmentRecyclerviewItemBinding> {
    public static final int EMPTY_ID = 1;
    public static final int HEADER_ID = 2;
    public static final String EMPTY_STR = "EMPTY";
    public static final String HEADER_STR = "HEADER";

    private final CitiesHandler handler;
    private final CitiesViewModel citiesViewModel;

    protected CitiesListAdapter(@NonNull DiffUtil.ItemCallback<CityEntity> diffCallback, CitiesHandler handler, CitiesViewModel citiesViewModel ) {
        super(diffCallback);
        this.handler = handler;
        this.citiesViewModel = citiesViewModel;
    }

    /**
     * Overriding this method to know the type of the view during processing
     * and execute the appropriate tasks for binding and setting its content
     *
     * @param position index as added in {@link ListAdapter#getCurrentList()}
     *
     * @return an integer defining the viewtype, custom fields used in this class
     */
    @Override
    public int getItemViewType(int position) {
        CityEntity item = getItem(position);

        if(TextUtils.equals(item.getName(), EMPTY_STR)){
            viewType = EMPTY_ID;
        }else if(TextUtils.equals(item.getName(), HEADER_STR)){
            viewType = HEADER_ID;
        }else if(TextUtils.equals(item.getName(), LOADING_STR)){
            viewType = LOADING_ID;
        }else{
            viewType = super.getItemViewType(position);
        }

        return viewType;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case LOADING_ID:
                return DataBindingUtil.inflate(inflater, R.layout.recyclerview_loading_item, parent, false);
            case EMPTY_ID:
                return DataBindingUtil.inflate(inflater, R.layout.recyclerview_exhausted_item, parent, false);
            case HEADER_ID:
                return DataBindingUtil.inflate(inflater, R.layout.cities_fragment_recyclerview_header, parent, false);
        }
        return CitiesFragmentRecyclerviewItemBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ViewDataBinding binding, CityEntity item, int position) {
        switch (viewType){
            case HEADER_ID:
                binding.setVariable(BR.text, binding.getRoot().getContext().getString(R.string.cities_favorites_header));
                break;
            case EMPTY_ID:
                binding.setVariable(BR.text, binding.getRoot().getContext().getString(R.string.cities_no_favorite_found));
                break;
            default:
                binding.setVariable(BR.city, item);
                binding.setVariable(BR.handler, handler);
                binding.setVariable(BR.viewmodel, citiesViewModel);
                // binding.setVariable(BR.)
                break;
        }
    }

    /**
     * adds a loading item in the list for this recyclerview
     */
    public void showLoading() {
        ArrayList<CityEntity> list = new ArrayList<>(1);
        list.add(CityEntity.builder().withName(LOADING_STR).build());
        submitList(list);
    }

    /**
     * We override this method to manipulate the content of the list parameter
     * either to add an item when list is empty, in order to inform the user
     * that no favorites found or to add a 'header' item on the shown list
     *
     * @param list user's favorite cities
     */
    @Override
    public void submitList(@Nullable List<CityEntity> list) {
        if(list == null || list.isEmpty()){
            if(list == null){
                list = new ArrayList<>(1);
            }
            list.add(CityEntity.builder().withName(EMPTY_STR).build());
        }/*else{
            list.add(0, CityEntity.builder().withName(HEADER_STR).build());
        }*/
        super.submitList(list);
    }

    /**
     * {@link CityEntity} uses 3 fields as primary keys, no id utilized.
     * So we check {@link CityEntity#getName()}, {@link CityEntity#getRegion()}, {@link CityEntity#getCountry()}
     * to know if provided item are the same
     */
    public static final DiffUtil.ItemCallback<CityEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<CityEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull CityEntity oldItem, @NonNull CityEntity newItem) {
            return TextUtils.equals(oldItem.getName(), newItem.getName())
                    && TextUtils.equals(oldItem.getCountry(), newItem.getCountry())
                    && TextUtils.equals(oldItem.getRegion(), newItem.getRegion());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CityEntity oldItem, @NonNull CityEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
}
