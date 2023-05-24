package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.ArrayList;

import Alicisindan.User;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder> {

    private final AdvertisementInterface advertisementInterface;
    ArrayList<Advertisement> advertisements; int position;

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
        return new AdvertisementViewHolder(view, advertisementInterface, advertisements.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementViewHolder holder, @SuppressLint("RecyclerView") int position) { this.position = position;
        holder.bind(advertisements.get(position));
    }

    static class AdvertisementViewHolder extends RecyclerView.ViewHolder {

        TextView advertisementTitle, advertisementIntent, advertisementTag;
        ImageView advertisementImage, favoriteButton;
        boolean isFavorited;

        static LocalSave local_save = null;

        public AdvertisementViewHolder(@NonNull View itemView, AdvertisementInterface advertisementInterface, Advertisement advertisment) {
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
                            advertisementInterface.onItemClick(pos);
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

                    // adding to favorites of the user
                    try {
                        String password = local_save.getString(Constants.KEY_PASSWORD);
                        String user_id = local_save.getString(Constants.KEY_USER_ID);
                        User user = User.getUser(user_id);
                        user.addFavorite(password, advertisment.getAdvertisementID());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
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

