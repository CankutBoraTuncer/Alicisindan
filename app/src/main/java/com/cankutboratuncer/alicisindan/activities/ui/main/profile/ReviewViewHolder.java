package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    ImageView delete;
    TextView text;
    TextView username;
    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        star1 = itemView.findViewById(R.id.rstar1);
        star2 = itemView.findViewById(R.id.rstar2);
        star3 = itemView.findViewById(R.id.rstar3);
        star4 = itemView.findViewById(R.id.rstar4);
        star5 = itemView.findViewById(R.id.rstar5);
        delete = itemView.findViewById(R.id.delete);

        username = itemView.findViewById(R.id.reviewerUsername);
        text = itemView.findViewById(R.id.reviewText);
    }
}
