package com.cankutboratuncer.alicisindan.activities.ui.main.buy;

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

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
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
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddSubCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostTypeActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.category.CategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.category.CategoryFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.ViewModelAdvertisement;
import com.cankutboratuncer.alicisindan.activities.ui.main.filter.FilterSubCategoryFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Alicisindan.Listing;
import Alicisindan.User;

public class BuyFragment extends Fragment implements AdvertisementInterface, CategoryInterface {

    private static final String ARG_FILTER_CATEGORY = "filter_category";
    private static final String ARG_FILTER_SUBCATEGORY = "filter_subCategory";
    private static final String ARG_FILTER_BRAND = "filter_brand";
    private static final String ARG_FILTER_CONDITION = "filter_condition";
    ArrayList<Advertisement> advertisements;
    View view;
    ArrayList<AllCategories> categories;
    String title;
    String description;
    String image;
    String price;
    String ID;
    String location;
    String userID;
    String username;
    String brand;
    String type;

    String categoryForFilter;
    String subCategoryForFilter;
    String brandForFilter;
    String conditionForFilter;
    RecyclerView recyclerViewForAdvertisements;
    RecyclerView recyclerViewForCategories;
    LinearLayoutManager horizontalRecyclerViewLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    AdvertisementAdapter advertisementAdapter;
    BackgroundTask backgroundTask;

    public BuyFragment() {
    }

    public static BuyFragment newInstance(String categoryForFilter, String subCategoryForFilter, String brandForFilter, String conditionForFilter) {
        BuyFragment fragment = new BuyFragment();
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
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        backgroundTask = new BackgroundTask(getContext(), this, this);
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
        Log.d("Category", "size: "+ categories.size());
        recyclerViewForCategories = view.findViewById(R.id.buyFragment_recyclerView_categories);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        recyclerViewForCategories.setAdapter(categoryAdapter);
        horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() / 4);
                return true;
            }
        };
        recyclerViewForCategories.setLayoutManager(horizontalRecyclerViewLayoutManager);

        swipeRefreshLayout = view.findViewById(R.id.buyFragment_recyclerView_container);

        advertisements = new ArrayList<>();
        recyclerViewForAdvertisements = view.findViewById(R.id.buyFragment_recyclerView_advertisements);
        advertisementAdapter = new AdvertisementAdapter(advertisements, this);
        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
        recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public void refreshList() {
        loading(true, view);
        backgroundTask.fetchDataFromDatabase(categoryForFilter, subCategoryForFilter, brandForFilter, conditionForFilter);
        advertisementAdapter.notifyDataSetChanged();
        loading(false, view);
    }

    public void initListeners() {
        view.findViewById(R.id.buttonCreatePost).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PostTypeActivity.class));
        });
        TextView textView_seeAll = view.findViewById(R.id.buyFragment_textView_seeAll);
        textView_seeAll.setOnClickListener(view -> {
            Fragment fragment = new CategoryFragment();
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
        Bundle args = new Bundle();
        Category category = (Category) categories.get(position);
        Fragment fragment = FilterSubCategoryFragment.newInstance(category.getName());
        loadFragment(fragment);
    }

    @Override
    public void onAdvertisementClick(int position) {
        Bundle args = new Bundle();
        Fragment fragment = AdvertisementFragment.newInstance(advertisements.get(position).getAdvertisementID());
        Advertisement advertisement = advertisements.get(position);
        args.putString("ID", advertisement.getAdvertisementID());
        args.putString("title", advertisement.getTitle());
        args.putString("price", advertisement.getPrice());
        args.putString("description", advertisement.getDescription());
        args.putString("location", advertisement.getLocation());
        args.putString("image", advertisement.getImage());
        args.putString("userID", advertisement.getUserID());
        args.putString("username", advertisement.getUsername());
        args.putString("brand", advertisement.getBrand());
        args.putString("type", advertisement.getType());
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
        AdvertisementInterface advertisementInterface;
        CategoryInterface categoryInterface;

        public BackgroundTask(Context context, AdvertisementInterface advertisementInterface, CategoryInterface categoryInterface) {
            Log.d("BackgroundTask", "Initialized");
            this.context = context;
            this.advertisementInterface = advertisementInterface;
            this.categoryInterface = categoryInterface;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Simulate the database retrieval process
            // Retrieve data from the database
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
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) context).runOnUiThread(() -> {
                            advertisementAdapter.notifyDataSetChanged();
                            loading(false, view);
                            Log.d("onPostExecute", "end");
                        });
                    }
                });
            }
        }

        private void fetchDataFromDatabase(String categoryForFilter, String subCategoryForFilter, String brandForFilter, String conditionForFilter) {
            Listing[] listings;
            try {
                Log.d("Data:Server", "findListing:begin. " + categoryForFilter + "/" + subCategoryForFilter);
                if (categoryForFilter == null) {
                    listings = Listing.findListings(null, null, null, null, "buy", conditionForFilter, null, null, null, null, null, "50");
                } else {
                    listings = Listing.findListings(null, categoryForFilter + "/" + subCategoryForFilter, null, null, "buy", conditionForFilter, null, null, null, null, null, "50");
                }
                Log.d("Data:Server", "findListing:end. Pulled " + listings.length + " listings");
                advertisements.clear();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (Listing listing : listings) {
                title = listing.getTitle();
                location = listing.getLocation();
                description = listing.getDescription();
                userID = listing.getOwnerID();
                brand = listing.getBrand();
                type = listing.getType();
                try {
                    User user = User.getUser(userID);
                    username = user.getUsername();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                try {
                    image = listing.getListingsFirstImage();
                    if (image == null) {
                        Bitmap icon = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.img_baby, null)).getBitmap();
                        image = encodeImage(icon);
                    }
                } catch (Exception ignored) {
                } finally {
                    if (image == null) {
                        Bitmap icon = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.img_baby, null)).getBitmap();
                        image = encodeImage(icon);
                    }
                }
                price = listing.getPrice();
                ID = listing.getID();
                advertisements.add(new Advertisement(title, description, image, price, ID, location, userID, username, brand, type));
            }
        }
    }

    private void loading(boolean isLoading, View view) {
        if (isLoading) {
            view.findViewById(R.id.buyFragment_recyclerView_advertisements).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.buyFragment_recyclerView_advertisements).setVisibility(View.VISIBLE);
            view.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }
    }
}