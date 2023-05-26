package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;

import java.util.ArrayList;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder> {

    private final AdvertisementInterface advertisementInterface;
    ArrayList<Advertisement> advertisements;

    public AdvertisementAdapter(ArrayList<Advertisement> advertisements, AdvertisementInterface advertisementInterface) {
        this.advertisements = advertisements;
        this.advertisementInterface = advertisementInterface;
    }

    @Override
    public int getItemCount() {
        return this.advertisements.size();
    }

    @NonNull
    @Override
    public AdvertisementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement, parent, false);
        return new AdvertisementViewHolder(view, advertisementInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementViewHolder holder, int position) {
        holder.bind(advertisements.get(position));
    }

    static class AdvertisementViewHolder extends RecyclerView.ViewHolder {

        TextView advertisementTitle, advertisementIntent, advertisementTag;
        ImageView advertisementImage, favoriteButton;
        boolean isFavorited;

        public AdvertisementViewHolder(@NonNull View itemView, AdvertisementInterface advertisementInterface) {
            super(itemView);
            this.advertisementTitle = itemView.findViewById(R.id.itemAdvertisement_textView_title);
            this.advertisementImage = itemView.findViewById(R.id.itemAdvertisement_imageView_image);
            this.advertisementIntent = itemView.findViewById(R.id.itemAdvertisement_textView_intent);
            this.advertisementTag = itemView.findViewById(R.id.itemAdvertisement_textView_tag);
            this.favoriteButton = itemView.findViewById(R.id.itemAdvertisement_imageView_favorite);
            this.isFavorited = false;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (advertisementInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            advertisementInterface.onAdvertisementClick(pos);
                        }
                    }
                }
            });

            favoriteButton.setOnClickListener(v->{
                if(isFavorited){
                    isFavorited = false;
                    favoriteButton.setBackgroundResource(R.drawable.ic_star);
                } else {
                    isFavorited = true;
                    favoriteButton.setBackgroundResource(R.drawable.ic_star_full);
                }

            });
        }

        public void bind(Advertisement advertisement) {
            this.advertisementTitle.setText(advertisement.getTitle());
            if(advertisement.getImage() != null){
                Bitmap image = getBitmapFromEncodedString(advertisement.getImage());
                this.advertisementImage.setImageBitmap(image);
            }
            if(advertisement.getType().equals("sell")){
                this.advertisementTag.setText("Sell");
                this.advertisementTag.setBackgroundResource(R.drawable.background_sell_tag);
                this.advertisementIntent.setText((advertisement.getUsername() + " wants to sell"));
            } else if(advertisement.getType().equals("buy")){
                this.advertisementTag.setText("Buy");
                this.advertisementTag.setBackgroundResource(R.drawable.background_buy_tag);
                this.advertisementIntent.setText("The user ");
                this.advertisementIntent.setText((advertisement.getUsername() + " wants to buy"));
            }

        }

        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            if (encodedImage != null) {
                byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } else {
                return null;
            }
        }
    }
}

