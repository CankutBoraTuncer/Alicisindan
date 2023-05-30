package com.cankutboratuncer.alicisindan.activities.ui.main.home.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryListener;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostCategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterCategoryFragment extends Fragment implements CategoryListener {
    private static final String ARG_FILTER_SUBCATEGORY = "filter_subCategory";
    private static final String ARG_FILTER_CONDITION = "filter_condition";
    private static final String ARG_SORTING_METHOD = "filter_sortingMethod";
    private static final String ARG_LOCATION = "filter_location";
    private static final String ARG_MIN_PRICE = "filter_minPrice";
    private static final String ARG_MAX_PRICE = "filter_maxPrice";
    private static final String ARG_TYPE = "type";

    String subCategoryForFilter;
    String conditionForFilter;
    String sortingMethodForFilter;
    String locationForFilter;
    String minPriceForFilter;
    String maxPriceForFilter;
    String type;

    public FilterCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterCategoryFragment.
     */
    public static FilterCategoryFragment newInstance(String type, String subCategoryForFilter, String conditionForFilter, String sortingMethodForFilter, String locationForFilter, String minPriceForFilter, String maxPriceForFilter) {
        FilterCategoryFragment fragment = new FilterCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putString(ARG_FILTER_SUBCATEGORY, subCategoryForFilter);
        args.putString(ARG_FILTER_CONDITION, conditionForFilter);
        args.putString(ARG_SORTING_METHOD, sortingMethodForFilter);
        args.putString(ARG_LOCATION, locationForFilter);
        args.putString(ARG_MIN_PRICE, minPriceForFilter);
        args.putString(ARG_MAX_PRICE, maxPriceForFilter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                type = getArguments().getString(ARG_TYPE);
            } catch (Exception e) {
                type = null;
            }
            try {
                subCategoryForFilter = getArguments().getString(ARG_FILTER_SUBCATEGORY);
            } catch (Exception e) {
                subCategoryForFilter = null;
            }
            try {
                conditionForFilter = getArguments().getString(ARG_FILTER_CONDITION);
            } catch (Exception e) {
                conditionForFilter = null;
            }
            try {
                sortingMethodForFilter = getArguments().getString(ARG_SORTING_METHOD);
            } catch (Exception e) {
                sortingMethodForFilter = null;
            }
            try {
                locationForFilter = getArguments().getString(ARG_LOCATION);
            } catch (Exception e) {
                locationForFilter = null;
            }
            try {
                minPriceForFilter = getArguments().getString(ARG_MIN_PRICE);
            } catch (Exception e) {
                minPriceForFilter = null;
            }
            try {
                maxPriceForFilter = getArguments().getString(ARG_MAX_PRICE);
            } catch (Exception e) {
                maxPriceForFilter = null;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_category, container, false);
        List<AllCategories> categories = Constants.categories;
        PostCategoryAdapter usersAdapter = new PostCategoryAdapter(categories, this);
        RecyclerView categoriesRecyclerView = view.findViewById(R.id.filterCategoryFragment_categoriesRecyclerView);
        categoriesRecyclerView.setAdapter(usersAdapter);
        categoriesRecyclerView.setVisibility(View.VISIBLE);
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    public void onUserClicked(String category) {
        Fragment fragment = FilterSubCategoryFragment.newInstance(type, category, conditionForFilter, sortingMethodForFilter, locationForFilter, minPriceForFilter, maxPriceForFilter);
        loadFragment(fragment);
    }

    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}