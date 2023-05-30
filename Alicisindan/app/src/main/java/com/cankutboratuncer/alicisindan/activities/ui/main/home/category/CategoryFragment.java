package com.cankutboratuncer.alicisindan.activities.ui.main.home.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryInterface;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements CategoryInterface {

    private static final String ARG_TYPE = "param1";
    private String type;
    private ArrayList<AllCategories> categories;
    private ArrayList<AllCategories> searchResultCategories;
    private CategoryExpandedAdapter categoryAdapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter 1.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        RecyclerView recyclerViewForCategories = view.findViewById(R.id.categoryFragment_recyclerView_categories);
        categories = new ArrayList<>(Constants.categories);
        searchResultCategories = new ArrayList<>(Constants.categories);
        categoryAdapter = new CategoryExpandedAdapter(searchResultCategories, this);
        recyclerViewForCategories.setAdapter(categoryAdapter);
        LinearLayoutManager horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewForCategories.setLayoutManager(horizontalRecyclerViewLayoutManager);

        SearchView searchView = view.findViewById(R.id.categoryFragment_searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Ooga", "buga");
                search(newText);
                String out = "";
                for (int i = 0; i < searchResultCategories.size(); i++) {
                    out += searchResultCategories.get(i).getName() + " ";
                }
                Log.d("Ooga", out);
                return false;
            }
        });

        return view;
    }

    public void onCategoryClick(int position) {
        Category category = (Category) categories.get(position);
        Fragment fragment = SubCategoryFragment.newInstance(type, category.getName());
        loadFragment(fragment);
    }

    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    void search(String query) {
        searchResultCategories.clear();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().toLowerCase().contains(query.toLowerCase())) {
                Log.d(categories.get(i).getName(), "contains: " + query);
                searchResultCategories.add(categories.get(i));
            }
        }
        requireActivity().runOnUiThread(() -> categoryAdapter.notifyDataSetChanged());
    }
}