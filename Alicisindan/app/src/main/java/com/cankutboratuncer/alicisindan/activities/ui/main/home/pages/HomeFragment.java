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
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostTypeActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.filter.FilterCategoryFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.filter.FilterFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.filter.FilterSubCategoryFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Alicisindan.Listing;

public class HomeFragment extends Fragment implements AdvertisementInterface, CategoryInterface {
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
    ViewModelAdvertisement viewModelAdvertisement;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String categoryForFilter, String subCategoryForFilter, String brandForFilter, String conditionForFilter) {
        HomeFragment fragment = new HomeFragment();
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
        viewModelAdvertisement = new ViewModelProvider((requireActivity())).get(ViewModelAdvertisement.class);
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        advertisements = new ArrayList<>();
        recyclerViewForAdvertisements = view.findViewById(R.id.homeFragment_recyclerView_advertisements);
        recyclerViewForCategories = view.findViewById(R.id.homeFragment_recyclerView_categories);
        swipeRefreshLayout = view.findViewById(R.id.homeFragment_recyclerView_container);
        initCategories();
        initListeners();
        return view;
    }

    private void initCategories() {
        horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() / 6);
                return true;
            }
        };
        categories = CategoryTest.categories;
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        recyclerViewForCategories.setAdapter(categoryAdapter);
        recyclerViewForCategories.setLayoutManager(horizontalRecyclerViewLayoutManager);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(true, view);
        if(viewModelAdvertisement.getAdvertisements_home() != null){
            advertisements = viewModelAdvertisement.getAdvertisements_home();
            initUI(advertisements);
        } else {
            refreshList();
        }
    }

    public void initUI(ArrayList<Advertisement> advertisements) {
        Log.d("UI", "initialize");
        advertisementAdapter = new AdvertisementAdapter(advertisements, this);
        recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
        loading(false,view);
    }

    public void refreshList() {
        loading(true, view);
        handler = new Handler(Looper.getMainLooper());
        backgroundTask = new BackgroundTask(getContext());
        backgroundTask.execute();
    }

    public void initListeners() {
        Log.d("Listener", "initialize");
        view.findViewById(R.id.homeFragment_buttonCreatePost).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PostTypeActivity.class));
        });
        TextView textView_seeAll = view.findViewById(R.id.homeFragment_textView_seeAll);
        textView_seeAll.setOnClickListener(view -> {
            Fragment fragment = new CategoryFragment();
            loadFragment(fragment);
        });
        TextView textView_filter = view.findViewById(R.id.homeFragment_textView_filter);
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
        args.putString(Constants.KEY_ADVERTISEMENT_ID, advertisement.getAdvertisementID());
        args.putString(Constants.KEY_ADVERTISEMENT_USERNAME, advertisement.getUsername());
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
            Log.d("BackgroundTask", "Initialized");
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Simulate the database retrieval process
            Log.d("Data", "Fetching Data: start");
            advertisements = new ArrayList<>();
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
                    initUI(advertisements);
                    viewModelAdvertisement.setAdvertisements_home(advertisements);
                    Log.d("Adverts", "update");
                    loading(false, view);
                    Log.d("onPostExecute", "end");
                }));
            }
        }


        /**
         * Used to create the homepage Listing cards.
         * <p>
         * Pulls only the necessary data and creates a new Advertisement object with them.
         * Other parameters of the object will be null.
         */
        private void fetchDataFromDatabase(String categoryForFilter, String subCategoryForFilter, String brandForFilter, String conditionForFilter) {
            String[][] listings;
            try {
                Log.d("Data:Server", "findListingShowcases:begin. " + categoryForFilter + "/" + subCategoryForFilter);
                if (categoryForFilter == null) {
                    listings = Listing.findListingShowcases(null, null, null, null, null, conditionForFilter, null, null, null, null, null, "50");
                } else {
                    listings = Listing.findListingShowcases(null, categoryForFilter + "/" + subCategoryForFilter, null, null, null, conditionForFilter, null, null, null, null, null, "50");
                }
                Log.d("Data:Server", "findListing:end. Pulled " + listings.length + " listings");
                advertisements.clear();

                for (String[] listing : listings) {
                    if (listing[0] == null) {
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

                    advertisements.add(new Advertisement(title, null, new String[]{image}, null, ID, null, userID, username, null, type, null, null));
                }
            } catch (Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        showToast("Server Error" + e.getMessage());
                    }
                });

            }
        }
    }


    private void loading(boolean isLoading, View view) {
        if (isLoading) {
            view.findViewById(R.id.homeFragment_recyclerView_advertisements).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.homeFragment_progressBar).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.homeFragment_recyclerView_advertisements).setVisibility(View.VISIBLE);
            view.findViewById(R.id.homeFragment_progressBar).setVisibility(View.INVISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //    public void createSearchBar(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        RecyclerView recyclerViewForAdvertisements = view.findViewById(R.id.homeFragment_recyclerView_advertisements);
//        searchView = view.findViewById(R.id.homeFragment_searchBar);
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//            @Override
//            public boolean onQueryTextSubmit(String query)
//            {
//                findFromList(query, inflater, container, savedInstanceState);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText)
//            {
//                findFromList(newText, inflater, container, savedInstanceState);
//                return true;
//            }
//        });
//    }

//    public void findFromList(String searchedText, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        try {
//            View view = inflater.inflate(R.layout.fragment_home, container, false);
//            RecyclerView recyclerViewForAdvertisements = view.findViewById(R.id.homeFragment_recyclerView_advertisements);
//            String text = searchedText;
//            Listing[] listings;
//            ArrayList<Advertisement> newAdvertisements = new ArrayList<Advertisement>();
//            String title;
//            String description;
//            String image;
//            String price;
//            String ID;
//            String location;
//            String userID;
//            String username;
//            String brand;
//            listings = Listing.findListings("", "", "", text, "", "", "", "", "", "", "", "10");
//            for (int i = 0; i < listings.length; i++) {
//                Listing listing = listings[i];
//                title = listing.getTitle();
//                description = listing.getDescription();
//                image = "0";
//                price = listing.getPrice();
//                location = listing.getLocation();
//                ID = listing.getID();
//                userID = listing.getOwnerID();
//                brand = listing.getBrand();
//                try {
//                    User user = User.getUser(userID);
//                    username = user.getUsername();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//                newAdvertisements.add(new Advertisement(title, description, image, price, ID, location, userID, username, brand));
//            }
//            recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
//            AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(newAdvertisements, this);
//            recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}