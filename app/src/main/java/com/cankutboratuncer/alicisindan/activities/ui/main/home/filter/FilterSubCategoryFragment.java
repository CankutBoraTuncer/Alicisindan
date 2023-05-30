package com.cankutboratuncer.alicisindan.activities.ui.main.home.filter;

import android.content.Intent;
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
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.PostEditActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryListener;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostCategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterSubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterSubCategoryFragment extends Fragment implements CategoryListener {
    private static final String ARG_FILTER_CATEGORY = "filter_category";
    private static final String ARG_FILTER_CONDITION = "filter_condition";
    private static final String ARG_SORTING_METHOD = "filter_sortingMethod";
    private static final String ARG_LOCATION = "filter_location";
    private static final String ARG_MIN_PRICE = "filter_minPrice";
    private static final String ARG_MAX_PRICE = "filter_maxPrice";
    private static final String ARG_TYPE = "type";

    String categoryForFilter;
    String conditionForFilter;
    String sortingMethodForFilter;
    String locationForFilter;
    String minPriceForFilter;
    String maxPriceForFilter;
    String type;


    public FilterSubCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryForFilter Parameter 1.
     * @return A new instance of fragment FilterSubCategoryFragment.
     */
    public static FilterSubCategoryFragment newInstance(String type, String categoryForFilter, String conditionForFilter, String sortingMethodForFilter, String locationForFilter, String minPriceForFilter, String maxPriceForFilter) {
        FilterSubCategoryFragment fragment = new FilterSubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putString(ARG_FILTER_CATEGORY, categoryForFilter);
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
                categoryForFilter = getArguments().getString(ARG_FILTER_CATEGORY);
            } catch (Exception e) {
                categoryForFilter = null;
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
        View view = inflater.inflate(R.layout.fragment_filter_sub_category, container, false);
        List<AllCategories> subCategories = Constants.createSubCategories(categoryForFilter);
        PostCategoryAdapter usersAdapter = new PostCategoryAdapter(subCategories, this);
        RecyclerView categoriesRecyclerView = view.findViewById(R.id.filterSubCategoryFragment_subCategoriesRecyclerView);
        categoriesRecyclerView.setAdapter(usersAdapter);
        categoriesRecyclerView.setVisibility(View.VISIBLE);
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onUserClicked(String subCategory) {
        Bundle args = new Bundle();
        Fragment fragment = FilterFragment.newInstance(categoryForFilter, subCategory, type, conditionForFilter, sortingMethodForFilter, locationForFilter, minPriceForFilter, maxPriceForFilter);
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