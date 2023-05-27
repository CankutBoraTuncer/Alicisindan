package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Alicisindan.AlicisindanException;
import Alicisindan.Review;

public class OtherProfileFragment extends Fragment {
    private String otherUserId;
    private ShapeableImageView profilePic;
    public OtherProfileFragment(String id)
    {
        this.otherUserId = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile, container, false);
        TextView userName = view.findViewById(R.id.otherProfileFragment_textView_fullName);
        TextView userUsername = view.findViewById(R.id.otherProfileFragment_textView_username);
        TextView userLocation = view.findViewById(R.id.otherUserLocation);
        CardView userPosts = view.findViewById(R.id.otherProfileFragment_cardView_posts);
        CardView rate = view.findViewById(R.id.otherProfileFragment_cardView_rate);
        TextView ratingAv = view.findViewById(R.id.ratingAverage);
        TextView numOfReviews = view.findViewById(R.id.numOfReviews);
        RecyclerView recyclerView = view.findViewById(R.id.reviews);
        String name = "";
        String username = "";
        String location = "";
        profilePic = view.findViewById(R.id.profileFragment_imageView_profilePicture);
        try {
            name = Alicisindan.User.getUser(otherUserId).getName() + " " + Alicisindan.User.getUser(otherUserId).getSurname();
            username = Alicisindan.User.getUser(otherUserId).getUsername();
            location = Alicisindan.User.getUser(otherUserId).getAddress();
            profilePic.setImageBitmap( decodeImage(Alicisindan.User.getUser(otherUserId).getUserImage()));

            ratingAv.setText(String.format("%.2f", Review.getRatingAverageFor(otherUserId)[0]) + "/5.00");

            int reviewNum = Review.findReviews(null, otherUserId, "", "", "", "", "NewestFirst", "0","").length;
            numOfReviews.setText("(" + reviewNum + " reviews)");
            Review[] reviews = Review.findReviews(null, otherUserId, "", "", "", "", "NewestFirst", "0","");
            List<ReviewItem> items = new ArrayList<ReviewItem>();
            for (int i = 0; i < reviewNum; i++)
            {
                items.add(new ReviewItem(Integer.parseInt(reviews[i].getRating()), reviews[i].getText(), reviews[i].getAuthorID()));
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new ReviewAdapter(getContext(), items));
        } catch (AlicisindanException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        userName.setText(name);
        userUsername.setText(username);
        userLocation.setText(location);
        userPosts.setOnClickListener(view21 -> {
            Fragment fragment = new OtherProfilePostsFragment(this.otherUserId);
            loadFragment(fragment);
        } );
        rate.setOnClickListener(view22 -> {
            Fragment fragment = new ReviewFragment(otherUserId);
            loadFragment(fragment);
        });

        return view;
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

    public static Bitmap decodeImage(String encodedImage) {
        try {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}
