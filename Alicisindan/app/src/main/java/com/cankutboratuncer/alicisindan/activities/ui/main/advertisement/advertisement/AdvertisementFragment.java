package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.login.SignInActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.OtherProfileFragment;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.activities.ChatActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import Alicisindan.Listing;

public class AdvertisementFragment extends Fragment implements AdvertisementInterface {

    private static final String ADVERTISEMENT_ID = "Advertisement ID";
    ArrayList<Advertisement> advertisements;

    private View view;
    private ViewPager2 imageSlider;
    ArrayList<ImageItem> imageItemArrayList;
    TextView username;
    private Advertisement advertisement;
    private String advertisementID;
    private LocalSave localSave;
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
        handler = new Handler(Looper.getMainLooper());
        backgroundTask = new BackgroundTask(getContext());
        backgroundTask.execute();
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
                    Log.d("Adverts", "update");
                    loading(false, view);
                    Log.d("onPostExecute", "end");
                }));
            }
        }

        private void fetchDataFromDatabase() {
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
                    requireActivity().runOnUiThread(() -> showToast("Advertisement couldn't loaded"));
                }
            }
        }
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
                requireActivity().runOnUiThread(() -> showToast("Advertisement couldn't loaded"));
            }
        }
    }

    public void initUI() {
        if (advertisement.getUserID().equals(localSave.getString(Constants.KEY_USER_ID))) {
            view.findViewById(R.id.layoutMessage).setVisibility(View.GONE);
            view.findViewById(R.id.layoutEdit).setVisibility(View.VISIBLE);
        }
        else
        {
            view.findViewById(R.id.layoutMessage).setVisibility(View.VISIBLE);
            view.findViewById(R.id.layoutEdit).setVisibility(View.GONE);
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
            view.findViewById(R.id.advertisementFragment_progressBar).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.advertisementFragment_progressBar).setVisibility(View.INVISIBLE);
        }
    }
}

//        LinearLayoutManager horizontalRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
//            @Override
//            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
//                // force height of viewHolder here, this will override layout_height from xml
//                lp.width = (int) (getWidth() / 2.5);
//                return true;
//            }
//        };
//
//        advertisements = AdvertisementTest.advertisements;
//        RecyclerView recyclerViewForAdvertisements = view.findViewById(R.id.relatedProducts);
//        recyclerViewForAdvertisements.setLayoutManager(horizontalRecyclerViewLayoutManager);
//        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(advertisements, this);
//        recyclerViewForAdvertisements.setAdapter(advertisementAdapter);

//        ViewPager2 advertisement_viewPager2 = view.findViewById(R.id.advertisementFragment_viewPager);
//        images.add(R.drawable.ic_launcher_background);
//        images.add(R.drawable.ic_launcher_foreground);
//        images.add(R.drawable.ic_launcher_background);
//        images.add(R.drawable.ic_launcher_foreground);
//        AdvertisementImageSliderAdapter MyAdapter = new AdvertisementImageSliderAdapter(this.getContext(), images);
//
//        advertisement_viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//
//        advertisement_viewPager2.setAdapter(MyAdapter);
//        advertisement_viewPager2.setOffscreenPageLimit(3);
//
//        float pageMargin = getResources().getDimensionPixelOffset(com.intuit.sdp.R.dimen._24sdp);
//        float pageOffset = getResources().getDimensionPixelOffset(com.intuit.sdp.R.dimen._24sdp);
//
//        advertisement_viewPager2.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (advertisement_viewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                if (ViewCompat.getLayoutDirection(advertisement_viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                    page.setTranslationX(-myOffset);
//                } else {
//                    page.setTranslationX(myOffset);
//                }
//            } else {
//                page.setTranslationY(myOffset);
//            }
//        });