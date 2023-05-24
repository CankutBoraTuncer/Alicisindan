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

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.data.database.CategoryTest;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementInterface;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Alicisindan.Listing;
import Alicisindan.User;

public class BuyFragment extends Fragment implements AdvertisementInterface {

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
    RecyclerView recyclerViewForAdvertisements;
    RecyclerView recyclerViewForCategories;
    Handler handler;
    LinearLayoutManager horizontalRecyclerViewLayoutManager;
    private ViewModelAdvertisement viewModel;
    public BuyFragment() {
    }

    public static BuyFragment newInstance(int advertisementID) {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider((requireActivity())).get(ViewModelAdvertisement.class);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        recyclerViewForAdvertisements = view.findViewById(R.id.buyFragment_recyclerView_advertisements);
        recyclerViewForCategories = view.findViewById(R.id.buyFragment_recyclerView_categories);
        initCategories();
        initListeners();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.getAdvertisements_buy() != null) {
            advertisements = viewModel.getAdvertisements_buy();
            initUI(viewModel.getAdvertisements_buy());
            Log.d("Tadaaa", "Here");
        } else {
            loading(true, view);
            handler = new Handler(Looper.getMainLooper());
            new BackgroundTask(getContext(), this).execute();
        }
    }

    private void initUI(ArrayList<Advertisement> advertisements) {
        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(advertisements, this);
        recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
        loading(false, view);
    }

    public void initCategories(){
        horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() / 4);
                return true;
            }
        };
        categories = CategoryTest.categories;
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        recyclerViewForCategories.setAdapter(categoryAdapter);
        recyclerViewForCategories.setLayoutManager(horizontalRecyclerViewLayoutManager);
    }

    public void initListeners(){
        view.findViewById(R.id.buttonCreatePost).setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("type", "buy");
            Intent intent = new Intent(getContext(), PostAddCategoryActivity.class);
            intent.putExtras(args);
            startActivity(intent);
        });
        TextView textView_seeAll = view.findViewById(R.id.buyFragment_textView_seeAll);
        textView_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CategoryFragment();
                loadFragment(fragment);
            }
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
    public void onItemClick(int position) {
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

        public BackgroundTask(Context context, AdvertisementInterface advertisementInterface) {
            this.context = context;
            this.advertisementInterface = advertisementInterface;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Simulate the database retrieval process
            horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                    // force height of viewHolder here, this will override layout_height from xml
                    lp.width = (int) (getWidth() / 4);
                    return true;
                }
            };
            advertisements = new ArrayList<>();
            // Retrieve data from the database
            fetchDataFromDatabase();
            categories = CategoryTest.categories;
            return "Done";
        }

        @Override
        protected void onPostExecute(final String data) {
            Activity activity = getActivity();
            if (activity != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Update the UI with the retrieved data
                        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(advertisements, advertisementInterface);
                        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
                        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
                        recyclerViewForCategories.setAdapter(categoryAdapter);

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                recyclerViewForCategories.setLayoutManager(horizontalRecyclerViewLayoutManager);
                                loading(false, view);
                                viewModel.setAdvertisements_buy(advertisements);
                                Log.d("Tadaaa", "adsadsa");
                            }
                        });
                    }
                });
            }
        }

        private void fetchDataFromDatabase() {
            Listing[] listings;
            try {
                listings = Listing.findListings(null, null, null, null, "buy", null, null, null, null, null, null, "50");
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