package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import Alicisindan.AlicisindanException;
import Alicisindan.Review;

public class ReviewFragment extends Fragment {
    private String userId;
    public ReviewFragment(String id)
    {
        this.userId = id;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        LocalSave localSave =  new LocalSave(getContext());
        AtomicInteger rating = new AtomicInteger();
        rating.set(0);
        TextView username = view.findViewById(R.id.user);
        ImageView star1 = view.findViewById(R.id.star1);
        ImageView star2 = view.findViewById(R.id.star2);
        ImageView star3 = view.findViewById(R.id.star3);
        ImageView star4 = view.findViewById(R.id.star4);
        ImageView star5 = view.findViewById(R.id.star5);
        EditText details = view.findViewById(R.id.details);
        AppCompatButton button = view.findViewById(R.id.buttonPost);
        try {
            username.setText(Alicisindan.User.getUser(userId).getUsername());
        } catch (AlicisindanException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        star1.setOnClickListener(vi1 -> {
            star1.setImageResource(R.drawable.ic_star_full);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star);
            rating.set(1);
        });
        star2.setOnClickListener(vi2 -> {
            star1.setImageResource(R.drawable.ic_star_full);
            star2.setImageResource(R.drawable.ic_star_full);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star);
            rating.set(2);
        });
        star3.setOnClickListener(vi3 -> {
            star1.setImageResource(R.drawable.ic_star_full);
            star2.setImageResource(R.drawable.ic_star_full);
            star3.setImageResource(R.drawable.ic_star_full);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star);
            rating.set(3);
        });
        star4.setOnClickListener(vi4 -> {
            star1.setImageResource(R.drawable.ic_star_full);
            star2.setImageResource(R.drawable.ic_star_full);
            star3.setImageResource(R.drawable.ic_star_full);
            star4.setImageResource(R.drawable.ic_star_full);
            star5.setImageResource(R.drawable.ic_star);
            rating.set(4);
        });
        star5.setOnClickListener(vi5 -> {
            star1.setImageResource(R.drawable.ic_star_full);
            star2.setImageResource(R.drawable.ic_star_full);
            star3.setImageResource(R.drawable.ic_star_full);
            star4.setImageResource(R.drawable.ic_star_full);
            star5.setImageResource(R.drawable.ic_star_full);
            rating.set(5);
        });
        button.setOnClickListener(vi6 -> {
            String rate = rating.get() + "";
            String text = details.getText().toString();
            try {
                Review review = new Review(localSave.getString(Constants.KEY_USER_ID), userId, rate, "", text );
                review.addReview(localSave.getString(Constants.KEY_PASSWORD));
                showToast("Your rating is saved.");
                Fragment fragment = new OtherProfileFragment(userId);
                loadFragment(fragment);
            } catch (AlicisindanException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        });
        return view;
    }
    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
