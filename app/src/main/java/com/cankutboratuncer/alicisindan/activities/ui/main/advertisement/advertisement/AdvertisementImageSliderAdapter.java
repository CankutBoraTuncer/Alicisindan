package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;

import java.util.ArrayList;

public class AdvertisementImageSliderAdapter extends RecyclerView.Adapter<AdvertisementImageSliderAdapter.ImageSliderViewHolder> {

    private final Context context;
    ArrayList<Integer> images;

    public AdvertisementImageSliderAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_advertisement_image, parent, false);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.imgBanner.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBanner;

        public ImageSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.itemAdvertisementImage_imageView);
        }
    }
}
