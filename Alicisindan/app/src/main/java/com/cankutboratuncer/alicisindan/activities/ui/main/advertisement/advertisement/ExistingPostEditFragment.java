package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.login.SignInActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.HomeFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Alicisindan.AlicisindanException;
import Alicisindan.Listing;

public class ExistingPostEditFragment extends Fragment {
    private ArrayList<String> encodedImages;
    private int pointer = 0;
    private LocalSave localSave;
    private int imageRow;
    private int imageCol;
    private String adID;
    private String adType;
    private String adCategory;
    private Advertisement ad;
    private AppCompatImageButton[][] imageButtons;
    public ExistingPostEditFragment(Advertisement ad)
    {
        this.ad = ad;
        this.adID = ad.getAdvertisementID();
        this.adCategory = ad.getCategory();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_ad, container, false);
        LocalSave localSave = new LocalSave(getContext());
        TextView topPanel = view.findViewById(R.id.topPanel);
        if (ad.getType().equals("sell")) {
            topPanel.setText("I want to sell...");
        } else {
            topPanel.setText("I want to buy...");
        }
        TextView subcategory = view.findViewById(R.id.subTitle);
        EditText brand = view.findViewById(R.id.brand);
        EditText condition = view.findViewById(R.id.condition);
        EditText title = view.findViewById(R.id.productTitle);
        EditText details = view.findViewById(R.id.details);
        EditText price = view.findViewById(R.id.price);
        EditText location = view.findViewById(R.id.location);
        subcategory.setText(this.adCategory);
        brand.setText(ad.getBrand());
        condition.setText(ad.getCondition());
        title.setText(ad.getTitle());
        details.setText(ad.getDescription());
        price.setText(ad.getPrice().substring(1));
        location.setText(ad.getLocation());
        imageCol = 0;
        imageRow = 0;
        imageButtons = new AppCompatImageButton[3][3];
        imageButtons[0] = new AppCompatImageButton[]{view.findViewById(R.id.imagesRow1_1), view.findViewById(R.id.imagesRow1_2), view.findViewById(R.id.imagesRow1_3)};
        imageButtons[1] = new AppCompatImageButton[]{view.findViewById(R.id.imagesRow2_1), view.findViewById(R.id.imagesRow2_2), view.findViewById(R.id.imagesRow2_3)};
        imageButtons[2] = new AppCompatImageButton[]{view.findViewById(R.id.imagesRow3_1), view.findViewById(R.id.imagesRow3_2), view.findViewById(R.id.imagesRow3_3)};
        adjustImage();

        try {
            String[] images = Listing.getListing(adID).getListingImages();
            int imageNum = images.length;
            int count = 0;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++)
                {
                    if (imageNum > 0)
                    {
                        imageButtons[i][j].setImageBitmap(decodeImage(images[count]));
                        encodedImages = new ArrayList<>();
                        encodedImages.add(images[count]);
                        pointer++;
                        updateImageRowCol();
                        count++;
                        imageNum--;
                    }
                }
            }

        } catch (AlicisindanException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.imagesRow1_1).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow1_2).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow1_3).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow2_1).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow2_2).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow2_3).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow3_1).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow3_2).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.imagesRow3_3).setOnClickListener(v -> {
            listenerFunction();
        });
        view.findViewById(R.id.buttonDelete).setOnClickListener(v -> {
            try{
                Listing listing = Listing.getListing(adID);
                listing.deleteListing(localSave.getString(Constants.KEY_USER_ID),localSave.getString(Constants.KEY_PASSWORD));
                ad.setAdvertisementID(null);
                showToast("Listing successfully deleted.");
                Fragment fragment = new HomeFragment();
                loadFragment(fragment);
            } catch (AlicisindanException e)
            {
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        });
        view.findViewById(R.id.buttonPost).setOnClickListener(v -> {
            boolean post = false;
            if (encodedImages == null) {
                showToast("Select at least 1 image.");
            } else if (title.getText().toString().trim().isEmpty()) {
                showToast("Title cannot be empty.");
            } else if (price.getText().toString().trim().isEmpty()) {
                showToast("Price cannot be empty.");
            } else if (location.getText().toString().trim().isEmpty()) {
                showToast("Location cannot be empty.");
            } else if (brand.getText().toString().trim().isEmpty()) {
                showToast("Please select a brand.");
            } else if (condition.getText().toString().trim().isEmpty()) {
                showToast("Please select a condition.");
            } else
            {
                post = true;
            }
            if(post)
            {
                String userID = localSave.getString(Constants.KEY_USER_ID);
                String password = localSave.getString(Constants.KEY_PASSWORD);
                String productTitle = title.getText().toString();
                String productDetails = details.getText().toString();
                String productPrice = price.getText().toString();
                String productLocation = location.getText().toString();
                String productCondition = condition.getText().toString();
                String productBrand = brand.getText().toString();
                try {
                    Listing listing = Listing.getListing(ad.getAdvertisementID());
                    listing.setTitle(userID, password, productTitle);
                    listing.setDescription(userID, password, productDetails);
                    //listing.setPrice(userID, password, productPrice);
                    listing.setLocation(userID,password,productLocation);
                    listing.setCondition(userID,password,productCondition);
                    listing.setBrand(userID,password, productBrand);
                    String[] images = new String[pointer];
                    for (int i = 0; i < images.length; i++) {
                        images[i] = encodedImages.get(i);
                    }
                    listing.setListingImages(userID, password, images);
                    showToast("Listing successfully edited.");
                    Fragment fragment = new HomeFragment();
                    loadFragment(fragment);
                }
                catch (AlicisindanException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    private void listenerFunction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }
    private void adjustImage() {
        for (int row = 0; row < imageButtons[0].length; row++) {
            for (int col = 0; col < imageButtons.length; col++) {
                imageButtons[row][col].setClipToOutline(true);
            }
        }
    }
    void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageButtons[imageRow][imageCol].setImageBitmap(bitmap);
                    updateImageRowCol();
                    if (encodedImages == null) {
                        encodedImages = new ArrayList<>();
                    }
                    encodedImages.add(encodeImage(bitmap));
                    pointer++;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    private void updateImageRowCol() {
        imageCol++;
        imageRow += imageCol == 3 ? 1 : 0;
        imageCol = imageCol == 3 ? 0 : imageCol;
    }


    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 300;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static Bitmap decodeImage(String encodedImage) {
        try {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
