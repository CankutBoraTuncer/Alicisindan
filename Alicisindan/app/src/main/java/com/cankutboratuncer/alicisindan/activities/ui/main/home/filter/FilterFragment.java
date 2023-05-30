package com.cankutboratuncer.alicisindan.activities.ui.main.home.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.BuyFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.HomeFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.SellFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;


public class FilterFragment extends Fragment {
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_SUBCATEGORY = "subCategory";
    private static final String ARG_TYPE = "type";
    private static final String ARG_CONDITION = "condition";
    private static final String ARG_SORTING_METHOD = "sortingMethod";
    private static final String ARG_COUNTRY = "country";
    private static final String ARG_CITY = "city";
    private static final String ARG_MIN_PRICE = "minPrice";
    private static final String ARG_MAX_PRICE = "maxPrice";

    private String category;
    private String subCategory;
    private String type;
    private String condition;
    private String sortingMethod;
    private String country;
    private String city;
    private String minPrice;
    private String maxPrice;

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category    Parameter 1.
     * @param subCategory Parameter 2.
     * @return A new instance of fragment filterFragment.
     */
    public static FilterFragment newInstance(String category, String subCategory, String type, String condition, String sortingMethod, String country, String city, String minPrice, String maxPrice) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putString(ARG_SUBCATEGORY, subCategory);
        args.putString(ARG_TYPE, type);
        args.putString(ARG_CONDITION, condition);
        args.putString(ARG_SORTING_METHOD, sortingMethod);
        args.putString(ARG_COUNTRY, country);
        args.putString(ARG_CITY, city);
        args.putString(ARG_MIN_PRICE, minPrice);
        args.putString(ARG_MAX_PRICE, maxPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
            subCategory = getArguments().getString(ARG_SUBCATEGORY);
            type = getArguments().getString(ARG_TYPE);
            condition = getArguments().getString(ARG_CONDITION);
            sortingMethod = getArguments().getString(ARG_SORTING_METHOD);
            country = getArguments().getString(ARG_COUNTRY);
            city = getArguments().getString(ARG_CITY);
            minPrice = getArguments().getString(ARG_MIN_PRICE);
            maxPrice = getArguments().getString(ARG_MAX_PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        Spinner conditionSpinner = view.findViewById(R.id.filterFragment_condition);
        Spinner sortingSpinner = view.findViewById(R.id.filterFragment_sortBy);
        EditText countryEditText = view.findViewById(R.id.filterFragment_editText_country);
        EditText cityEditText = view.findViewById(R.id.filterFragment_editText_city);
        EditText minPriceEditText = view.findViewById(R.id.filterFragment_minPrice);
        EditText maxPriceEditText = view.findViewById(R.id.filterFragment_maxPrice);

        ArrayAdapter conditionAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, Constants.CONDITION_FILTER);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);
        if (condition != null) {
            int spinnerPosition = conditionAdapter.getPosition(condition);
            conditionSpinner.setSelection(spinnerPosition);
        }

        ArrayAdapter sortingAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, Constants.SORTING_METHODS);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(sortingAdapter);
        if (sortingMethod != null) {
            int spinnerPosition = sortingAdapter.getPosition(sortingMethod);
            sortingSpinner.setSelection(spinnerPosition);
        }
        if (country != null) {
            countryEditText.setText(country);
        }
        if (city != null) {
            cityEditText.setText(city);
        }
        if (minPrice != null) {
            minPriceEditText.setText(minPrice);
        }
        if (maxPrice != null) {
            maxPriceEditText.setText(maxPrice);
        }

        TextView category_textView = view.findViewById(R.id.filterFragment_subTitle);
        if (category != null) {
            if (subCategory != null) {
                category_textView.setText(category + "/" + subCategory);
            } else {
                category_textView.setText(category);
            }
        }

        Button searchButton = (Button) view.findViewById(R.id.filterFragment_buttonPost);
        TextView changeButton = view.findViewById(R.id.filterFragment_changeButton);
        searchButton.setOnClickListener(v -> {
            condition = conditionSpinner.getSelectedItem().toString();
            if (condition.equals("Any")) {
                condition = null;
            }
            sortingMethod = sortingSpinner.getSelectedItem().toString();
            country = countryEditText.getText().toString();
            city = cityEditText.getText().toString();
            minPrice = minPriceEditText.getText().toString();
            maxPrice = maxPriceEditText.getText().toString();
            Log.d("Filter", "Category: " + category);
            Log.d("Filter", "Subcategory: " + subCategory);
            Log.d("Filter", "Condition: " + condition);
            Log.d("Filter", "SortingMethod: " + sortingMethod);
            Log.d("Filter", "Country: " + country);
            Log.d("Filter", "City: " + city);
            Log.d("Filter", "MinPrice: " + minPrice);
            Log.d("Filter", "MaxPrice: " + maxPrice);

            Fragment fragment = HomeFragment.newInstance(category, subCategory, condition, sortingMethod, country, city, minPrice, maxPrice, true);

            if (type.equals("buy")) {
                fragment = BuyFragment.newInstance(category, subCategory, condition, sortingMethod, country, city, minPrice, maxPrice, true);
            }
            if (type.equals("sell")) {
                fragment = SellFragment.newInstance(category, subCategory, condition, sortingMethod, country, city, minPrice, maxPrice, true);
            }
            loadFragment(fragment);
        });
        changeButton.setOnClickListener(v -> {
            Fragment fragment = FilterCategoryFragment.newInstance(type, null, null, null, null, null, null, null);
            loadFragment(fragment);
        });
        TextView removeButton = view.findViewById(R.id.filterFragment_removeButton);
        removeButton.setOnClickListener(v -> {
            category = null;
            subCategory = null;
            category_textView.setText("None");
        });
        TextView resetButton = view.findViewById(R.id.filterFragment_resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingMethod = "NewestFirst";
                int spinnerPosition = sortingAdapter.getPosition(sortingMethod);
                sortingSpinner.setSelection(spinnerPosition);

                condition = "Any";
                spinnerPosition = conditionAdapter.getPosition(condition);
                conditionSpinner.setSelection(spinnerPosition);

                country = null;
                countryEditText.setText(country);
                city = null;
                countryEditText.setText(city);
                minPrice = null;
                countryEditText.setText(minPrice);
                maxPrice = null;
                countryEditText.setText(maxPrice);
            }
        });
        return view;
    }

    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}