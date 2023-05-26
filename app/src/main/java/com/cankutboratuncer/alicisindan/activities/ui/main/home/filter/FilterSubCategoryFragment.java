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
import com.cankutboratuncer.alicisindan.activities.data.database.CategoryTest;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.PostEditActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryListener;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostCategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterSubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterSubCategoryFragment extends Fragment implements CategoryListener {
    private static final String ARG_CATEGORY = "category";

    private String category;

    public FilterSubCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @return A new instance of fragment FilterSubCategoryFragment.
     */
    public static FilterSubCategoryFragment newInstance(String category) {
        FilterSubCategoryFragment fragment = new FilterSubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_sub_category, container, false);
        List<AllCategories> subCategories = CategoryTest.createSubCategories(category);
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
        Fragment fragment = FilterFragment.newInstance(category, subCategory);
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