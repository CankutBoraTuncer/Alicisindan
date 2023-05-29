package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    Context context;
    List<ReviewItem> items;
    public ReviewAdapter(Context context, List<ReviewItem> items)
    {
        this.context = context;
        this.items = items;
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
