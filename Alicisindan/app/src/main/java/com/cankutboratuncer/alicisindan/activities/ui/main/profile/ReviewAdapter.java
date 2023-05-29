package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.List;

import Alicisindan.AlicisindanException;
import Alicisindan.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    Context context;
    List<ReviewItem> items;
    Fragment profileFragment;
    String localID;
    boolean myReviews;
    LocalSave localSave;
    public ReviewAdapter(Context context, List<ReviewItem> items, Fragment profile, String localID, boolean myReviews)
    {
        this.context = context;
        this.items = items;
        this.profileFragment = profile;
        this.localID = localID;
        this.myReviews = myReviews;
        this.localSave = new LocalSave(this.context);
    }
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.text.setText(items.get(position).getText());
        holder.username.setText(items.get(position).getUsername());
        if (myReviews)
        {
            holder.delete.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.delete.setVisibility(View.GONE);
        }
        if (items.get(position).getRating() == 0)
        {
            holder.star1.setImageResource(R.drawable.ic_star);
            holder.star2.setImageResource(R.drawable.ic_star);
            holder.star3.setImageResource(R.drawable.ic_star);
            holder.star4.setImageResource(R.drawable.ic_star);
            holder.star5.setImageResource(R.drawable.ic_star);
        }
        else if (items.get(position).getRating() == 1)
        {
            holder.star1.setImageResource(R.drawable.ic_star_full);
            holder.star2.setImageResource(R.drawable.ic_star);
            holder.star3.setImageResource(R.drawable.ic_star);
            holder.star4.setImageResource(R.drawable.ic_star);
            holder.star5.setImageResource(R.drawable.ic_star);
        }
        else if (items.get(position).getRating() == 2)
        {
            holder.star1.setImageResource(R.drawable.ic_star_full);
            holder.star2.setImageResource(R.drawable.ic_star_full);
            holder.star3.setImageResource(R.drawable.ic_star);
            holder.star4.setImageResource(R.drawable.ic_star);
            holder.star5.setImageResource(R.drawable.ic_star);
        }
        else if (items.get(position).getRating() == 3)
        {
            holder.star1.setImageResource(R.drawable.ic_star_full);
            holder.star2.setImageResource(R.drawable.ic_star_full);
            holder.star3.setImageResource(R.drawable.ic_star_full);
            holder.star4.setImageResource(R.drawable.ic_star);
            holder.star5.setImageResource(R.drawable.ic_star);
        }
        else if (items.get(position).getRating() == 4)
        {
            holder.star1.setImageResource(R.drawable.ic_star_full);
            holder.star2.setImageResource(R.drawable.ic_star_full);
            holder.star3.setImageResource(R.drawable.ic_star_full);
            holder.star4.setImageResource(R.drawable.ic_star_full);
            holder.star5.setImageResource(R.drawable.ic_star);
        }
        else if (items.get(position).getRating() == 5)
        {
            holder.star1.setImageResource(R.drawable.ic_star_full);
            holder.star2.setImageResource(R.drawable.ic_star_full);
            holder.star3.setImageResource(R.drawable.ic_star_full);
            holder.star4.setImageResource(R.drawable.ic_star_full);
            holder.star5.setImageResource(R.drawable.ic_star_full);
        }
        holder.username.setOnClickListener(v -> {

            if (items.get(position).getUserID().equals(localID))
            {
                Fragment fragment = new ProfileFragment();
                loadFragment(fragment);
            }
            else
            {
                Fragment fragment = new OtherProfileFragment(items.get(position).getUserID());
                loadFragment(fragment);
            }
        });
        holder.delete.setOnClickListener(v-> {
            try {
                Review review = Review.findReviews(localID, items.get(position).getUserID(),"","","","","","","1")[0];
                review.deleteReview(localSave.getString(Constants.KEY_PASSWORD));
                showToast("Review successfully deleted.");
                Fragment fragment = new ReviewsIHaveWrittenFragment();
                loadFragment(fragment);
            } catch (AlicisindanException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        profileFragment.getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void showToast(String message) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
    }
}
