package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.ArrayList;
import java.util.List;

import Alicisindan.AlicisindanException;
import Alicisindan.Review;

public class ReviewsIHaveWrittenFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);
        LocalSave localSave = new LocalSave(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.myReviewsFragment_recycleview);
        try {
            int reviewNum = Review.findReviews(localSave.getString(Constants.KEY_USER_ID),null , "", "", "", "", "NewestFirst", "0","").length;
            Review[] reviews = Review.findReviews(localSave.getString(Constants.KEY_USER_ID), null, "", "", "", "", "NewestFirst", "0","");
            List<ReviewItem> items = new ArrayList<ReviewItem>();
            for (int i = 0; i < reviewNum; i++)
            {
                items.add(new ReviewItem(Integer.parseInt(reviews[i].getRating()), reviews[i].getText(), reviews[i].getReviewedID()));
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new ReviewAdapter(getContext(), items, this, localSave.getString(Constants.KEY_USER_ID)));
        } catch (AlicisindanException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
