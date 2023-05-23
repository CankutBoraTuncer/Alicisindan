package com.cankutboratuncer.alicisindan.activities.ui.main.forum.category;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.forum.forum.ForumFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityForumEditBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ForumEditActivity extends AppCompatActivity {

    private ActivityForumEditBinding binding;
    private String encodedImage;
    private LocalSave localSave;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setListeners();
    }

    private void init() {
        localSave = new LocalSave(getApplicationContext());
        database = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        binding.subTitle.setText(category);
        binding.image.setClipToOutline(true);
    }

    private void postForumPost() {
        HashMap<String, Object> forum = new HashMap<>();
        forum.put(Constants.KEY_FORUM_ID, UUID.randomUUID().toString());
        forum.put(Constants.KEY_FORUM_OWNER_ID, localSave.getString(Constants.KEY_USER_ID));
        forum.put(Constants.KEY_FORUM_OWNER_NAME, localSave.getString(Constants.KEY_USER_USERNAME));
        forum.put(Constants.KEY_FORUM_TITLE, binding.forumTitle.getText().toString());
        forum.put(Constants.KEY_FORUM_DESCRIPTION, binding.details.getText().toString());
        forum.put(Constants.KEY_FORUM_IMAGE, encodedImage);
        forum.put(Constants.KEY_TIMESTAMP, new Date());
        addForumPost(forum);
    }

    private void addForumPost(HashMap<String, Object> forum) {
        database.collection(Constants.KEY_COLLECTION_FORUM_POSTS).add(forum)
                .addOnSuccessListener(v -> {
                    showToast("Post successfully posted.");
                    Fragment fragment = new ForumFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).commit();
                })
                .addOnFailureListener(v -> showToast("Forum post couldn't posted."));
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.image.setImageBitmap(bitmap);
                            encodedImage = encodeImage(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private void setListeners() {
        binding.buttonPost.setOnClickListener(v -> {
            if (isValidForumDetails()) {
                postForumPost();
            }
        });
        binding.image.setOnClickListener(v -> {
            listenerFunction();
        });
        binding.change.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ForumAddCategoryActivity.class));
            finish();
        });
    }

    private void listenerFunction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }

    private Boolean isValidForumDetails() {
        if (encodedImage == null) {
            showToast("Pick an image");
            return false;
        } else if (binding.forumTitle.getText().toString().trim().isEmpty()) {
            showToast("Enter title");
            return false;
        } else if (binding.details.getText().toString().trim().isEmpty()) {
            showToast("Enter Description");
            return false;
        } else {
            return true;
        }
    }

}