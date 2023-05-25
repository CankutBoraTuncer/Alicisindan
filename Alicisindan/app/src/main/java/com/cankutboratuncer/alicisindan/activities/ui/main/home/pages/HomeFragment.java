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
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostTypeActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.category.CategoryFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Alicisindan.AlicisindanException;
import Alicisindan.Listing;
import Alicisindan.User;

public class HomeFragment extends Fragment implements AdvertisementInterface {

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
    SwipeRefreshLayout swipeRefreshLayout;
    private ViewModelAdvertisement viewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int advertisementID) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewForAdvertisements = view.findViewById(R.id.homeFragment_recyclerView_advertisements);
        recyclerViewForCategories = view.findViewById(R.id.homeFragment_recyclerView_categories);
        swipeRefreshLayout = view.findViewById(R.id.homeFragment_recyclerView_container);
        initCategories();
        initListeners();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.getAdvertisements_home() != null) {
            advertisements = viewModel.getAdvertisements_home();
            initUI(viewModel.getAdvertisements_home());
            Log.d("Tadaaa", "Here");
        } else {
            refreshList();
        }
    }

    public void refreshList() {
        loading(true, view);
        handler = new Handler(Looper.getMainLooper());
        new BackgroundTask(getContext(), this).execute();
    }

    private void initUI(ArrayList<Advertisement> advertisements) {
        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(advertisements, this);
        recyclerViewForAdvertisements.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
        loading(false, view);
    }

    public void initCategories() {
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

    public void initListeners() {
        view.findViewById(R.id.buttonCreatePost).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PostTypeActivity.class));
        });
        TextView textView_seeAll = view.findViewById(R.id.homeFragment_textView_seeAll);
        textView_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CategoryFragment();
                loadFragment(fragment);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                refreshList();

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

        // Load the rest of Listing details.
        try {
            Listing clickedListing = Listing.getListing(advertisement.getAdvertisementID());
            advertisement.setDescription(clickedListing.getDescription());
            //TODO
            // ADVERTISEMENT CLASS DOESN'T SUPPORT MULTIPLE IMAGES YET!?
            //advertisement.setImages(clickedListing.getImages());
            advertisement.setPrice(clickedListing.getPrice());
            advertisement.setLocation(clickedListing.getLocation());
            advertisement.setBrand(clickedListing.getBrand());
        }
        catch (Exception e) {
            showToast("Server Error");
        }


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
                                viewModel.setAdvertisements_home(advertisements);
                                Log.d("Tadaaa", "adsadsa");
                            }
                        });
                    }
                });
            }
        }


        /**
         * Used to create the homepage Listing cards.
         *
         * Pulls only the necessary data and creates a new Advertisement object with them.
         * Other parameters of the object will be null.
         */
        private void fetchDataFromDatabase() {
            try {
                String[][] listings = Listing.findListingShowcases(null, null, null, null, null, null, null, null, null, null, null, "50");

                for (String[] listing : listings) {
                    if(listing[0] == null) {
                        continue;
                    }

                    userID = listing[0];
                    username = listing[1];
                    ID = listing[2];
                    image = listing[3];
                    if (image == null) {
                        Bitmap icon = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.img_baby, null)).getBitmap();
                        image = encodeImage(icon);
                    }
                    type = listing[4];
                    title = listing[5];

                    advertisements.add(new Advertisement(title, null, image, null, ID, null, userID, username, null, type));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void loading(boolean isLoading, View view) {
        if (isLoading) {
            view.findViewById(R.id.homeFragment_recyclerView_advertisements).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.homeFragment_recyclerView_advertisements).setVisibility(View.VISIBLE);
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

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}