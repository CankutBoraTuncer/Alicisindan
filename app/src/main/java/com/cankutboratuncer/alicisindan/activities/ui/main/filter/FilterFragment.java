package com.cankutboratuncer.alicisindan.activities.ui.main.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementInterface;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.activities.utilities.Filter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment implements AdvertisementInterface {

    ArrayList<Advertisement> advertisements;
    ArrayList<Category> categories;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(int advertisementID) {
        FilterFragment fragment = new FilterFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        EditText editText_location = view.findViewById(R.id.filterFragment_editText_location);
        EditText editText_type = view.findViewById(R.id.filterFragment_editText_type);
        EditText editText_category = view.findViewById(R.id.filterFragment_editText_category);
        EditText editText_brand = view.findViewById(R.id.filterFragment_editText_brand);
        EditText editText_color = view.findViewById(R.id.filterFragment_editText_color);
        Spinner spinner_sortingMethod = view.findViewById(R.id.filterFragment_spinner_sortingMethod);
        Button button_filter = view.findViewById(R.id.filterFragment_button_filter);

        button_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter filter = new Filter(editText_location.getText().toString(), editText_type.getText().toString(), editText_category.getText().toString(), editText_brand.getText().toString(), editText_color.getText().toString(), spinner_sortingMethod.getSelectedItem().toString());

                // TODO: AdvertisementHandler(filter);
                Fragment fragment = new FilterResultsFragment();
                loadFragment(fragment);
            }
        });

        LinearLayoutManager horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() / 4);
                return true;
            }
        };

        return view;
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onAdvertisementClick(int position) {
        Fragment fragment = AdvertisementFragment.newInstance(advertisements.get(position).getAdvertisementID());
        loadFragment(fragment);
    }
}