package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.login.SignInActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityPostEditBinding;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Alicisindan.Listing;

public class PostEditActivity extends AppCompatActivity {

    private ActivityPostEditBinding binding;
    private ArrayList<String> encodedImages;
    private int pointer = 0;
    private LocalSave localSave;
    private int imageRow;
    private int imageCol;
    String category;
    String brand;
    String condition;
    Spinner conditionSpinner;
    AutoCompleteTextView locationText;
    private AppCompatImageButton[][] imageButtons;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        localSave = new LocalSave(getApplicationContext());
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        type = intent.getStringExtra("type");
        if (type.equals("sell")) {
            binding.topPanel.setText("I want to sell...");
        } else {
            binding.topPanel.setText("I want to buy...");
        }
        binding.subTitle.setText(category);
        locationText = findViewById(R.id.postEditActivity_location);
        List<String> str = new ArrayList<String>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.getAssets().open("cities.txt")));
            String line = in.readLine();
            while (line != null) {
                str.add(line);
                line = in.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, str);
        //Getting the instance of AutoCompleteTextView
        locationText.setThreshold(2);//will start working from first character
        locationText.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        initUI();
        initImageButton();
        setListeners();
    }

    private void initUI() {
        conditionSpinner = findViewById(R.id.postEditActivity_condition);
        ArrayAdapter conditionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constants.CONDITION_POST);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);
    }

    private void initImageButton() {
        imageCol = 0;
        imageRow = 0;
        imageButtons = new AppCompatImageButton[3][3];
        imageButtons[0] = new AppCompatImageButton[]{binding.imagesRow11, binding.imagesRow12, binding.imagesRow13};
        imageButtons[1] = new AppCompatImageButton[]{binding.imagesRow21, binding.imagesRow22, binding.imagesRow23};
        imageButtons[2] = new AppCompatImageButton[]{binding.imagesRow31, binding.imagesRow32, binding.imagesRow33};
        adjustImage();
    }

    private void adjustImage() {
        for (int row = 0; row < imageButtons[0].length; row++) {
            for (int col = 0; col < imageButtons.length; col++) {
                imageButtons[row][col].setClipToOutline(true);
            }
        }
    }

    private void updateImageRowCol() {
        imageCol++;
        imageRow += imageCol == 3 ? 1 : 0;
        imageCol = imageCol == 3 ? 0 : imageCol;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
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

    private void setListeners() {
        binding.buttonPost.setOnClickListener(v -> {
            try {
                if (isValidPostDetails()) {
                    postAdd();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.change.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PostAddCategoryActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            finish();
        });
        binding.imagesRow11.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow12.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow13.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow21.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow22.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow23.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow31.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow32.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.imagesRow33.setOnClickListener(v -> {
            listenerFunction();
        });
    }

    private void postAdd() throws Exception {
        String userID = localSave.getString(Constants.KEY_USER_ID);
        // When user is not logged in:
        if (userID == null) {
            showToast("You have to log in first.");
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        }
        String password = localSave.getString(Constants.KEY_PASSWORD);
        String productTitle = binding.productTitle.getText().toString();
        String details = binding.details.getText().toString();
        String price = binding.price.getText().toString();
        String location = locationText.getText().toString();
        String condition = conditionSpinner.getSelectedItem().toString();
        String brand = binding.brand.getText().toString();
        Listing listing = new Listing(userID, type, productTitle, details, price, category, location, condition, brand);
        listing.addListing(userID, password);
        String[] images = new String[pointer];
        for (int i = 0; i < images.length; i++) {
            images[i] = encodedImages.get(i);
        }
        listing.setListingImages(userID, password, images);
        showToast("Add successfully posted.");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        MainActivity.type = type;
        MainActivity.category = category;
        MainActivity.condition = condition;
        MainActivity.location = location;
        MainActivity.price = price;
        MainActivity.comingFromPostAdd = true;
        startActivity(intent);
        finish();
    }

    private void listenerFunction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }

    private Boolean isValidPostDetails() {
        if (encodedImages == null) {
            showToast("Select at least 1 image.");
            return false;
        } else if (binding.productTitle.getText().toString().trim().isEmpty()) {
            showToast("Title cannot be empty.");
            return false;
        } else if (binding.price.getText().toString().trim().isEmpty()) {
            showToast("Price cannot be empty.");
            return false;
        } else if (locationText.getText().toString().trim().isEmpty()) {
            showToast("Location cannot be empty.");
            return false;
        } else if (binding.brand.getText().toString().trim().isEmpty()) {
            showToast("Please select a brand.");
            return false;
        } else if (conditionSpinner.getSelectedItem().toString().trim().isEmpty()) {
            showToast("Please select a condition.");
            return false;
        } else {
            return true;
        }
    }

}