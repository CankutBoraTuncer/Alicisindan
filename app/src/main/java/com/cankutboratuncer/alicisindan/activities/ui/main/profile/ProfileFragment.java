package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

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
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.login.SignInActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.activities.utilities.Util;
import com.cankutboratuncer.alicisindan.databinding.ActivityPostEditBinding;
import com.cankutboratuncer.alicisindan.databinding.FragmentProfileBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

import Alicisindan.AlicisindanException;
import Alicisindan.User;


public class ProfileFragment extends Fragment {

    private String encodedImage;
    private LocalSave localSave;
    private Alicisindan.User user;
    private ShapeableImageView profilePic;
    private AppCompatImageButton[][] imageButtons;

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
                    InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    profilePic.setImageBitmap(bitmap);
                    encodedImage = encodeImage(bitmap);
                    try {
                        Alicisindan.User user = Alicisindan.User.getUser(localSave.getString(Constants.KEY_USER_ID));
                        user.setImage(localSave.getString(Constants.KEY_PASSWORD), encodedImage);
                        localSave.putString(Constants.KEY_USER_IMAGE, encodedImage);
                    } catch (AlicisindanException e)
                    {
                        e.printStackTrace();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        localSave =  new LocalSave(getContext());
        CardView cardView_myPosts = view.findViewById(R.id.profileFragment_cardView_myPosts);
        CardView cardView_messages = view.findViewById(R.id.profileFragment_cardView_messages);
        CardView cardView_account = view.findViewById(R.id.profileFragment_cardView_account);
        CardView cardView_favorite = view.findViewById(R.id.profileFragment_cardView_favorite);
        CardView cardView_help = view.findViewById(R.id.profileFragment_cardView_help);
        CardView cardView_logOut = view.findViewById(R.id.profileFragment_cardView_logOut);
        CardView cardView_reviews = view.findViewById(R.id.profileFragment_cardView_reviews);
        CardView cardView_reviews2 = view.findViewById(R.id.profileFragment_cardView_reviews2);
        profilePic = view.findViewById(R.id.profileFragment_imageView_profilePicture);
        TextView privacyPolicy = view.findViewById(R.id.privacyPolicy);
        String userID = localSave.getString(Constants.KEY_USER_ID);
        // When user is logged in:
        if (userID != null) {
            cardView_myPosts.setOnClickListener(view11 -> {
                Fragment fragment = new MyPostsFragment();
                loadFragment(fragment);
            });
            cardView_messages.setOnClickListener(view12 -> {
                Fragment fragment = new MessagesFragment();
                loadFragment(fragment);
            });
            cardView_account.setOnClickListener(view13 -> {
                Fragment fragment = new AccountFragment();
                loadFragment(fragment);
            });
            cardView_favorite.setOnClickListener(view14 -> {
                Fragment fragment = new FavoritesFragment();
                loadFragment(fragment);
            });
            cardView_help.setOnClickListener(view15 -> {
                Fragment fragment = new HelpFragment();
                loadFragment(fragment);
            });
            cardView_logOut.setOnClickListener(view16 -> {
                LocalSave localSave = new LocalSave(getContext());
                localSave.clear();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            });
            privacyPolicy.setOnClickListener(view22 -> {
                Fragment fragment = new PrivacyFragment();
                loadFragment(fragment);
            });
            cardView_reviews.setOnClickListener(view25 -> {
                Fragment fragment = new MyReviewsFragment();
                loadFragment(fragment);
            });
            cardView_reviews2.setOnClickListener(view26 -> {
                Fragment fragment = new ReviewsIHaveWrittenFragment();
                loadFragment(fragment);
            });
            profilePic.setOnClickListener(view17 -> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            });
            try {
                Alicisindan.User user;

                String image = localSave.getString(Constants.KEY_USER_IMAGE);
                if(image != null){
                    profilePic.setImageBitmap( Util.decodeImage(image));
                } else {
                    image = Util.drawableToString(getResources(), R.drawable.default_user_img);
                }
                profilePic.setImageBitmap(Util.decodeImage(image));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            TextView username = view.findViewById(R.id.profileFragment_textView_fullName);
            username.setText(localSave.getString(Constants.KEY_USER_NAME));
            TextView email = view.findViewById(R.id.profileFragment_textView_email);
            email.setText(localSave.getString(Constants.KEY_USER_EMAIL));
        } else {
            cardView_account.setVisibility(View.GONE);
            cardView_favorite.setVisibility(View.GONE);
            cardView_messages.setVisibility(View.GONE);
            cardView_myPosts.setVisibility(View.GONE);
            cardView_reviews.setVisibility(View.GONE);
            cardView_reviews2.setVisibility(View.GONE);
            cardView_help.setOnClickListener(view15 -> {
                Fragment fragment = new HelpFragment();
                loadFragment(fragment);
            });
            TextView logOut_text = view.findViewById(R.id.profileFragment_logOut_text);
            logOut_text.setText("Login");
            cardView_logOut.setOnClickListener(view16 -> {
                LocalSave localSave = new LocalSave(getContext());
                localSave.clear();
                Intent intent = new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
            });
            privacyPolicy.setOnClickListener(view22 -> {
                Fragment fragment = new PrivacyFragment();
                loadFragment(fragment);
            });
            TextView username = view.findViewById(R.id.profileFragment_textView_fullName);
            username.setVisibility(View.GONE);
            TextView email = view.findViewById(R.id.profileFragment_textView_email);
            email.setVisibility(View.GONE);
        }
        return view;
    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}
