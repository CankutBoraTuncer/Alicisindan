package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.login.SignInActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.OtherProfileFragment;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.activities.ChatActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Alicisindan.Listing;
import Alicisindan.User;

public class AdvertisementFragment extends Fragment implements AdvertisementInterface {

    private static final String ADVERTISEMENT_ID = "Advertisement ID";
    ArrayList<Advertisement> advertisements;
    private Advertisement advertisement;

    private View view;
    private ViewPager2 imageSlider;
    ArrayList<ImageItem> imageItemArrayList;
    TextView username;
    private String advertisementID;
    private LocalSave localSave;
    private ImageView add_remove_favorite;

    //
    private static User user = null;
    private static String password = null;
    private static String[] favorites = null;

    public static Alicisindan.User getUser_Advertisementfragment() {return user;}
    public static String getPassword() {return password;}
    public static String[] getFavorites_AdvertisementFragment() {return favorites;}
    public static void setFavorites_AdvertisementFragment(String[] favorites) {AdvertisementFragment.favorites = favorites;}
    //
    BackgroundTask backgroundTask;
    Handler handler;

    public AdvertisementFragment() {
    }


    public static AdvertisementFragment newInstance(String advertisementID) {
        AdvertisementFragment fragment = new AdvertisementFragment();
        Bundle args = new Bundle();
        args.putString(ADVERTISEMENT_ID, advertisementID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localSave = new LocalSave(getContext());

        try {
            user = Alicisindan.User.getUser(localSave.getString(Constants.KEY_USER_ID));
            password = localSave.getString(Constants.KEY_PASSWORD);
            favorites = user.getFavorites();
            if (favorites == null) {
                favorites = new String[]{"e"};
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (favorites == null) {
                favorites = new String[]{"e"};
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_advertisement, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(true, view);
        advertisements = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());
        backgroundTask = new BackgroundTask(getContext());
        backgroundTask.execute();
    }

    public void loadAdvertisement() {
        Bundle args = getArguments();
        if (args != null) {
            advertisementID = args.getString(Constants.KEY_ADVERTISEMENT_ID);
            String advertisementUsername = args.getString(Constants.KEY_ADVERTISEMENT_USERNAME);
            try {

                Listing clickedListing = Listing.getListing(advertisementID);
                String advertisementTitle = clickedListing.getTitle();
                String advertisementDescription = clickedListing.getDescription();
                String advertisementPrice = "$" + clickedListing.getPrice();
                String advertisementLocation = clickedListing.getLocation();
                String advertisementBrand = clickedListing.getBrand();
                String[] advertisementImages = clickedListing.getListingImages();
                String advertisementCategory = clickedListing.getCategory();
                String advertisementCondition = clickedListing.getCondition();
                String advertisementOwnerID = clickedListing.getOwnerID();
                String advertisementType = clickedListing.getType();

                advertisement = new Advertisement(advertisementTitle, advertisementDescription, advertisementImages, advertisementPrice, advertisementID, advertisementLocation, advertisementOwnerID, advertisementUsername, advertisementBrand, advertisementType, advertisementCategory, advertisementCondition);
                loading(false, view);
            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> showToast("Couldn't load advertisement"));
            }
        }
    }

    public void initUI() {
        String userID = localSave.getString(Constants.KEY_USER_ID);
        if (userID == null) {
            view.findViewById(R.id.buttonMessage).setVisibility(View.GONE);
            view.findViewById(R.id.buttonEdit).setVisibility(View.GONE);
        } else if (advertisement.getUserID().equals(userID)) {
            view.findViewById(R.id.buttonMessage).setVisibility(View.GONE);
            view.findViewById(R.id.buttonEdit).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.buttonMessage).setVisibility(View.VISIBLE);
            view.findViewById(R.id.buttonEdit).setVisibility(View.GONE);
        }

        TextView productTitle = view.findViewById(R.id.productTitle);
        productTitle.setText(advertisement.getTitle());
        TextView productPrice = view.findViewById(R.id.productPrice);
        productPrice.setText(advertisement.getPrice());
        TextView productDetails = view.findViewById(R.id.productDetails);
        productDetails.setText(advertisement.getDescription());
        TextView productLocation = view.findViewById(R.id.productLocation);
        productLocation.setText(advertisement.getLocation());
        TextView productCategory = view.findViewById(R.id.productCategory);
        productCategory.setText(advertisement.getCategory());
        TextView productBrand = view.findViewById(R.id.productBrand);
        productBrand.setText(advertisement.getBrand());
        TextView productCondition = view.findViewById(R.id.productCondition);
        productCondition.setText(advertisement.getCondition());
//        TextView category = view.findViewById(R.id.productCategory);
        username = view.findViewById(R.id.username);
        username.setText(advertisement.getUsername());

        // favorite button - text
        add_remove_favorite = (ImageView) view.findViewById(R.id.itemAdvertisement_imageView_favorite);

        boolean inFavorites = false;
        for (String id : favorites) {
            if (advertisement.getAdvertisementID().equals(id)) {
                inFavorites = true;

                break;
            }
        }

        if (!inFavorites) {
            add_remove_favorite.setImageResource(R.drawable.ic_star);
        }
        else {
            add_remove_favorite.setImageResource(R.drawable.ic_star_full);
        }

        initImgSlider();
    }

    public void initImgSlider() {
        imageSlider = view.findViewById(R.id.advertisementFragment_imageSlider);
        String[] images = advertisement.getImages();
        int x = 0;
        for (int i = 0; i < images.length; i++) {
            if (images[i].length() > 10) {
                x++;
            }
        }
        Log.d("Images:", "x = " + x);
        String[] newImages = new String[x];
        newImages = Arrays.copyOfRange(images, 0, x);
        advertisement.setImages(newImages);

        imageItemArrayList = new ArrayList<>();
        for (int i = 0; i < newImages.length; i++) {
            Drawable d = new BitmapDrawable(getResources(), decodeImage(images[i]));
            imageItemArrayList.add(new ImageItem(d));
        }
        ImageAdapter imageAdapter = new ImageAdapter(imageItemArrayList);
        imageSlider.setAdapter(imageAdapter);
    }

    public void initListeners() {
        view.findViewById(R.id.buttonMessage).setOnClickListener(v -> {
            if (localSave.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
                Bundle args = new Bundle();
                args.putString(Constants.KEY_ADVERTISEMENT_TITLE, advertisement.getTitle());
                args.putString(Constants.KEY_ADVERTISEMENT_USERID, advertisement.getUserID());
                args.putString(Constants.KEY_ADVERTISEMENT_USERNAME, advertisement.getUsername());
                args.putString(Constants.KEY_ADVERTISEMENT_ID, advertisement.getAdvertisementID());
                args.putString(Constants.KEY_ADVERTISEMENT_LOCATION, advertisement.getLocation());
                args.putString(Constants.KEY_ADVERTISEMENT_PRICE, advertisement.getPrice());
                args.putString(Constants.KEY_ADVERTISEMENT_IMAGE, advertisement.getPreviewImage());
                args.putString(Constants.KEY_ADVERTISEMENT_TYPE, advertisement.getType());
                args.putString(Constants.KEY_SENDER_ID, localSave.getString(Constants.KEY_USER_ID));
                args.putString(Constants.KEY_RECEIVER_ID, advertisement.getUserID());

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            } else {
                showToast("You have to sign in first");
                localSave.putBoolean(Constants.KEY_IS_USER_SKIP, false);
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });
        view.findViewById(R.id.buttonEdit).setOnClickListener(v25 -> {
            Fragment fragment = new ExistingPostEditFragment(advertisement);
            loadFragment(fragment);
        });
        username.setOnClickListener(view20 -> {
            Fragment profile = new OtherProfileFragment(advertisement.getUserID());
            loadFragment(profile);
        });

        // favorite text - button click listener
        view.findViewById(R.id.itemAdvertisement_imageView_favorite).setOnClickListener(fav -> {
            boolean inFavorites = false;
            for (String id : favorites) {
                if (advertisement.getAdvertisementID().equals(id)) {
                    inFavorites = true;

                    break;
                }
            }

            if (!inFavorites) {
                try {
                    user.addFavorite(localSave.getString(Constants.KEY_PASSWORD) ,advertisement.getAdvertisementID());
                    add_remove_favorite.setImageResource(R.drawable.ic_star_full);
                    favorites = user.getFavorites();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    user.removeFavorite(localSave.getString(Constants.KEY_PASSWORD) ,advertisement.getAdvertisementID());
                    add_remove_favorite.setImageResource(R.drawable.ic_star);
                    favorites = user.getFavorites();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
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

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Bitmap decodeImage(String encodedImage) {
        try {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loading(boolean isLoading, View view) {
        if (isLoading) {
            view.findViewById(R.id.advertisementFragment_page).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.advertisementFragment_progressBar).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.advertisementFragment_page).setVisibility(View.VISIBLE);
            view.findViewById(R.id.advertisementFragment_progressBar).setVisibility(View.INVISIBLE);
        }
    }

    private void initSimilar() {
        LinearLayoutManager horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force height of viewHolder here, this will override layout_height from xml
                lp.width = (int) (getWidth() / 2.5);
                return true;
            }
        };

        RecyclerView recyclerViewForAdvertisements = view.findViewById(R.id.relatedProducts);
        recyclerViewForAdvertisements.setLayoutManager(horizontalRecyclerViewLayoutManager);
        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(advertisements, this);
        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);
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
            fetchDataFromDatabase();
            Log.d("Data", "Fetching Data: complete ");
            return "Done";
        }

        @Override
        protected void onPostExecute(final String data) {
            Log.d("onPostExecute", "begin");
            Activity activity = getActivity();
            if (activity != null) {
                handler.post(() -> ((Activity) context).runOnUiThread(() -> {
                    initUI();
                    initListeners();
                    initSimilar();
                    Log.d("Adverts", "update");
                    loading(false, view);
                    Log.d("onPostExecute", "end");
                }));
            }
        }

        private void fetchDataFromDatabase() {
            Bundle args = getArguments();
            if (args != null) {
                String[][] listings;
                advertisementID = args.getString(Constants.KEY_ADVERTISEMENT_ID);
                String advertisementUsername = args.getString(Constants.KEY_ADVERTISEMENT_USERNAME);
                Listing clickedListing = null;
                String advertisementTitle = null;
                String advertisementDescription = null;
                String advertisementPrice = null;
                String advertisementLocation = null;
                String advertisementBrand = null;
                String[] advertisementImages = null;
                String advertisementCategory = null;
                String advertisementCondition = null;
                String advertisementOwnerID = null;
                String advertisementType = null;
                try {
                    clickedListing = Listing.getListing(advertisementID);
                    advertisementTitle = clickedListing.getTitle();
                    advertisementDescription = clickedListing.getDescription();
                    advertisementPrice = "$" + clickedListing.getPrice();
                    advertisementLocation = clickedListing.getLocation();
                    advertisementBrand = clickedListing.getBrand();
                    advertisementImages = clickedListing.getListingImages();
                    advertisementCategory = clickedListing.getCategory();
                    advertisementCondition = clickedListing.getCondition();
                    advertisementOwnerID = clickedListing.getOwnerID();
                    advertisementType = clickedListing.getType();
                    advertisement = new Advertisement(advertisementTitle, advertisementDescription, advertisementImages, advertisementPrice, advertisementID, advertisementLocation, advertisementOwnerID, advertisementUsername, advertisementBrand, advertisementType, advertisementCategory, advertisementCondition);
                } catch (Exception e) {
                    showToast("Error while loading advertisement details!");
                    e.printStackTrace();
                    advertisement = null;
                }
                try {
                    listings = Listing.findListingShowcases(null, advertisementCategory, null, null, null, advertisementCondition, Integer.toString(Integer.parseInt(advertisementPrice.substring(1)) / 2), Integer.toString(Integer.parseInt(advertisementPrice.substring(1)) * 2), null, null, null, "10");
                    if (listings.length < 6) {
                        listings = Listing.findListingShowcases(null, advertisementCategory, null, null, null, advertisementCondition, null, null, null, null, null, "10");
                    }
                    if (listings.length < 6) {
                        listings = Listing.findListingShowcases(null, advertisementCategory, null, null, null, null, null, null, null, null, null, "10");
                    }
                    if (listings.length < 6) {
                        String newCategory;
                        try {
                            String[] parts = advertisementCategory.split("/");
                            newCategory = parts[0] + "%";
                            listings = Listing.findListingShowcases(null, newCategory, null, null, null, null, null, null, null, null, null, "10");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

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
                        String price = listing[6];

                        if (!ID.equals(advertisementID)) {
                            advertisements.add(new Advertisement(title, null, new String[]{image}, price, ID, null, userID, username, null, type, null, null));
                        }
                    }
                } catch (Exception e) {
                    requireActivity().runOnUiThread(() -> {
                        e.printStackTrace();
                        showToast("Server Error" + e.getMessage());
                    });
                }
            }
        }
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

}



