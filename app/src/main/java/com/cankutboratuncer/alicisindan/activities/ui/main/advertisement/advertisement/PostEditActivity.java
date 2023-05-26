package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.HomeFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityPostEditBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Alicisindan.Listing;

public class PostEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityPostEditBinding binding;
    private String encodedImage;
    private LocalSave localSave;
    private int imageRow;
    private int imageCol;
    String category;
    String brand;
    String condition;
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
        if(type.equals("sell")){
            binding.topPanel.setText("I want to sell...");
        } else{
            binding.topPanel.setText("I want to buy...");
        }
        binding.subTitle.setText(category);
        initImageButton();
        initSpinners();
        setListeners();

    }

    private void initSpinners() {
        Spinner spinnerBrand = binding.brand;
        Spinner spinnerCondition = binding.condition;

        spinnerBrand.setOnItemSelectedListener(this);
        spinnerCondition.setOnItemSelectedListener(this);

        ArrayAdapter brandAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constants.CAR_CAR_BRAND);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(brandAdapter);

        ArrayAdapter conditionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constants.CONDITION);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(conditionAdapter);

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
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
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
                    encodedImage = encodeImage(bitmap);
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
                throw new RuntimeException(e);
            }
        });
        binding.change.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PostAddCategoryActivity.class));
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
        String password = localSave.getString(Constants.KEY_PASSWORD);
        String productTitle = binding.productTitle.getText().toString();
        String details = binding.details.getText().toString();
        String price = binding.price.getText().toString();
        String location = binding.location.getText().toString();

        Listing listing = new Listing(userID, type, productTitle, details, price, category, location, condition, "AKShdj");
        listing.addListing(userID, password);
        Log.d("şişko", binding.productTitle.getText().toString());
        String[] images = {encodedImage};
        listing.setListingImages(userID, password, images);
        showToast("Add successfully posted");
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void listenerFunction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == binding.brand){
            brand = adapterView.getItemAtPosition(i).toString();
        } else if (adapterView == binding.condition){
            condition = adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private Boolean isValidPostDetails() {
        if (encodedImage == null) {
            showToast("Select at least 1 image");
            return false;
        } else if (binding.productTitle.getText().toString().trim().isEmpty()) {
            showToast("Title cannot be empty");
            return false;
        } else if (binding.price.getText().toString().trim().isEmpty()) {
            showToast("Price cannot be empty");
            return false;
        } else if (binding.location.getText().toString().trim().isEmpty()) {
            showToast("Location cannot be empty");
            return false;
        }
//         else if (binding.brand.getCount() == 0) {
//            showToast("Please select a brand");
//            return false;
//        } else if (binding.condition.getCount() == 0) {
//            showToast("Please select a condition");
//            return false;
        else {
            return true;
        }
    }

}