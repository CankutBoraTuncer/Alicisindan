package com.cankutboratuncer.alicisindan.activities.ui.main.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.buy.BuyFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_SUBCATEGORY = "subCategory";

    // TODO: Rename and change types of parameters
    private String category;
    private String subCategory;
    private String brand;
    private String condition;

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @param subCategory Parameter 2.
     * @return A new instance of fragment filterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String category, String subCategory) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putString(ARG_SUBCATEGORY, subCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
            subCategory = getArguments().getString(ARG_SUBCATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        Spinner brandSpinner = view.findViewById(R.id.filterFragment_brand);
        Spinner conditionSpinner = view.findViewById(R.id.filterFragment_condition);

        ArrayAdapter conditionAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, Constants.CONDITION);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        ArrayAdapter brandAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, Constants.CAR_CAR_BRAND);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        TextView category_textView = view.findViewById(R.id.filterFragment_subTitle);
        category_textView.setText(category + "/" + subCategory);

        Button searchButton = (Button) view.findViewById(R.id.filterFragment_buttonPost);
        TextView changeButton = view.findViewById(R.id.filterFragment_changeButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand = brandSpinner.getSelectedItem().toString();
                if (brand.equals("None")) {
                    brand = null;
                }
                condition = conditionSpinner.getSelectedItem().toString();
                if (condition.equals("None")) {
                    condition = null;
                }

                Bundle args = new Bundle();
                Fragment fragment = BuyFragment.newInstance(category, subCategory, brand, condition);
                loadFragment(fragment);
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