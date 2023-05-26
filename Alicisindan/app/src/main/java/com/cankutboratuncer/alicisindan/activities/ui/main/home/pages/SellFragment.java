package com.cankutboratuncer.alicisindan.activities.ui.main.home.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.data.database.CategoryTest;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementInterface;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryInterface;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostTypeActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.filter.FilterCategoryFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.filter.FilterFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.filter.FilterSubCategoryFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Alicisindan.Listing;

public class SellFragment extends Fragment implements AdvertisementInterface, CategoryInterface {
    // Give TAGS to the variables used for filtering
    private static final String ARG_FILTER_CATEGORY = "filter_category";
    private static final String ARG_FILTER_SUBCATEGORY = "filter_subCategory";
    private static final String ARG_FILTER_BRAND = "filter_brand";
    private static final String ARG_FILTER_CONDITION = "filter_condition";

    // Strings to be used in filtering
    String categoryForFilter;
    String subCategoryForFilter;
    String brandForFilter;
    String conditionForFilter;
    ArrayList<Advertisement> advertisements;
    View view;
    ArrayList<AllCategories> categories;

    RecyclerView recyclerViewForAdvertisements;
    RecyclerView recyclerViewForCategories;
    LinearLayoutManager horizontalRecyclerViewLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    AdvertisementAdapter advertisementAdapter;
    BackgroundTask backgroundTask;
    Handler handler;

    public SellFragment() {
    }

    public static SellFragment newInstance(String categoryForFilter, String subCategoryForFilter, String brandForFilter, String conditionForFilter) {
        SellFragment fragment = new SellFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILTER_CATEGORY, categoryForFilter);
        args.putString(ARG_FILTER_SUBCATEGORY, subCategoryForFilter);
        args.putString(ARG_FILTER_BRAND, brandForFilter);
        args.putString(ARG_FILTER_CONDITION, conditionForFilter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                categoryForFilter = getArguments().getString(ARG_FILTER_CATEGORY);
            } catch (Exception e) {
                categoryForFilter = null;
            }
            try {
                subCategoryForFilter = getArguments().getString(ARG_FILTER_SUBCATEGORY);
            } catch (Exception e) {
                subCategoryForFilter = null;
            }
            try {
                brandForFilter = getArguments().getString(ARG_FILTER_BRAND);
            } catch (Exception e) {
                brandForFilter = null;
            }
            try {
                conditionForFilter = getArguments().getString(ARG_FILTER_CONDITION);
            } catch (Exception e) {
                conditionForFilter = null;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sell, container, false);
        backgroundTask = new BackgroundTask(getContext());
        backgroundTask.execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("UI", "initialize");
        initUI();
        Log.d("Listener", "initialize");
        initListeners();
        refreshList();
    }

    public void initUI() {
        categories = CategoryTest.categories;
        Log.d("Category", "size: " + categories.size());
        recyclerViewForCategories = view.findViewById(R.id.sellFragment_recyclerView_categories);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        recyclerViewForCategories.setAdapter(categoryAdapter);
        horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() / 6);
                return true;
            }
        };
        recyclerViewForCategories.setLayoutManager(horizontalRecyclerViewLayoutManager);

        swipeRefreshLayout = view.findViewById(R.id.sellFragment_recyclerView_container);

        advertisements = new ArrayList<>();
        recyclerViewForAdvertisements = view.findViewById(R.id.sellFragment_recyclerView_advertisements);
        advertisementAdapter = new AdvertisementAdapter(advertisements, this);
        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
        recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public void refreshList() {
        loading(true, view);
        handler = new Handler(Looper.getMainLooper());
        backgroundTask.cancel(false);
        backgroundTask = new BackgroundTask(getContext());
        backgroundTask.execute();
    }

    public void initListeners() {
        view.findViewById(R.id.sellFragment_buttonCreatePost).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PostTypeActivity.class));
        });
        TextView textView_seeAll = view.findViewById(R.id.sellFragment_textView_seeAll);
        textView_seeAll.setOnClickListener(view -> {
            Fragment fragment = new CategoryFragment();
            loadFragment(fragment);
        });
        TextView textView_filter = view.findViewById(R.id.sellFragment_textView_filter);
        textView_filter.setOnClickListener(view -> {
            Fragment fragment;
            if (categoryForFilter == null) {
                fragment = FilterCategoryFragment.newInstance();
                Log.d("Category", "null");
            } else {
                fragment = FilterFragment.newInstance(categoryForFilter, subCategoryForFilter);
                Log.d("Category", "non-null");
            }
            loadFragment(fragment);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            refreshList();
        });
    }

    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCategoryClick(int position) {
        Category category = (Category) categories.get(position);
        Fragment fragment = FilterSubCategoryFragment.newInstance(category.getName());
        loadFragment(fragment);
    }

    @Override
    public void onAdvertisementClick(int position) {
        Bundle args = new Bundle();
        Fragment fragment = AdvertisementFragment.newInstance(advertisements.get(position).getAdvertisementID());
        Advertisement advertisement = advertisements.get(position);

        // Load the rest of Listing details.
        try {
            Listing clickedListing = Listing.getListing(advertisement.getAdvertisementID());
            advertisement.setDescription(clickedListing.getDescription());
            advertisement.setImages(clickedListing.getListingImages());
            advertisement.setPrice(clickedListing.getPrice());
            advertisement.setLocation(clickedListing.getLocation());
            advertisement.setBrand(clickedListing.getBrand());
            advertisement.setCategory(clickedListing.getCategory());
            advertisement.setCondition(clickedListing.getCondition());
        }
        catch (Exception e) {
            showToast("Server Error");
        }
        args.putString("ID", advertisement.getAdvertisementID());
        args.putString("title", advertisement.getTitle());
        args.putString("price", advertisement.getPrice());
        args.putString("description", advertisement.getDescription());
        args.putString("location", advertisement.getLocation());
        args.putStringArray("images", advertisement.getImages());
        args.putString("userID", advertisement.getUserID());
        args.putString("username", advertisement.getUsername());
        args.putString("brand", advertisement.getBrand());
        args.putString("type", advertisement.getType());
        args.putString("category", advertisement.getCategory());
        args.putString("condition", advertisement.getCondition());
        fragment.setArguments(args);
        loadFragment(fragment);
    }


    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {
        Context context;

        public BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Simulate the database retrieval process
            Log.d("Data", "Fetching Data: start");
            fetchDataFromDatabase(categoryForFilter, subCategoryForFilter, brandForFilter, conditionForFilter);
            Log.d("Data", "Fetching Data: complete. Ad count: " + advertisements.size());
            return "Done";
        }

        @Override
        protected void onPostExecute(final String data) {
            Log.d("onPostExecute", "begin");
            Activity activity = getActivity();
            if (activity != null) {
                handler.post(() -> ((Activity) context).runOnUiThread(() -> {
                    advertisementAdapter.notifyDataSetChanged();
                    Log.d("Adverts", "update");
                    loading(false, view);
                    Log.d("onPostExecute", "end");
                }));
            }
        }

        private void fetchDataFromDatabase(String categoryForFilter, String subCategoryForFilter, String brandForFilter, String conditionForFilter) {
            String[][] listings;
            try {
                Log.d("Data:Server", "findListingShowcases:begin. " + categoryForFilter + "/" + subCategoryForFilter);
                if (categoryForFilter == null) {
                    listings = Listing.findListingShowcases(null, null, null, null, "sell", conditionForFilter, null, null, null, null, null, "50");
                } else {
                    listings = Listing.findListingShowcases(null, categoryForFilter + "/" + subCategoryForFilter, null, null, "sell", conditionForFilter, null, null, null, null, null, "50");
                }
                Log.d("Data:Server", "findListing:end. Pulled " + listings.length + " listings");
                advertisements.clear();
            } catch (Exception e) {
                e.printStackTrace();;
                throw new RuntimeException(e);
            }

            for (String[] listing : listings) {
                if(listing[0] == null) {
                    continue;
                }

                String userID = listing[0];
                String username = listing[1];
                String ID = listing[2];
                String image = listing[3];
                if (image == null) {
                    Bitmap icon = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.img_baby, null)).getBitmap();
                    image = encodeImage(icon);
                }
                String type = listing[4];
                String title = listing[5];

                advertisements.add(new Advertisement(title, null, new String[] {image}, null, ID, null, userID, username, null, type, null, null));
            }
        }
    }

    private void loading(boolean isLoading, View view) {
        if (isLoading) {
            view.findViewById(R.id.sellFragment_recyclerView_advertisements).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.sellFragment_progressBar).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.sellFragment_recyclerView_advertisements).setVisibility(View.VISIBLE);
            view.findViewById(R.id.sellFragment_progressBar).setVisibility(View.INVISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}