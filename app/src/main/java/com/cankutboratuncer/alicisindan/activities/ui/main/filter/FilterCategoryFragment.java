package com.cankutboratuncer.alicisindan.activities.ui.main.filter;

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
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryListener;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostCategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterCategoryFragment extends Fragment implements CategoryListener {

    public FilterCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterCategoryFragment.
     */
    public static FilterCategoryFragment newInstance() {
        FilterCategoryFragment fragment = new FilterCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_category, container, false);
        List<AllCategories> categories = CategoryTest.categories;
        PostCategoryAdapter usersAdapter = new PostCategoryAdapter(categories, this);
        RecyclerView categoriesRecyclerView = view.findViewById(R.id.filterCategoryFragment_categoriesRecyclerView);
        categoriesRecyclerView.setAdapter(usersAdapter);
        categoriesRecyclerView.setVisibility(View.VISIBLE);
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    public void onUserClicked(String category) {
        Fragment fragment = FilterSubCategoryFragment.newInstance(category);
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