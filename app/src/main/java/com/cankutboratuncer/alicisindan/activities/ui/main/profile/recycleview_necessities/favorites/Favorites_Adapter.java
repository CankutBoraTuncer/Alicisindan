package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts.MyPosts_Adapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Favorites_Adapter extends RecyclerView.Adapter<Favorites_Adapter.Favorites_Holder>{
    private ArrayList<_Favorites> _favorites;
    private Context _context;

    public Favorites_Adapter(ArrayList<_Favorites> _favorites, Context _context) {
        this._favorites = _favorites;
        this._context = _context;
    }

    @NonNull
    @Override
    public Favorites_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.item_advertisement, parent, false);
        return new Favorites_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Favorites_Holder holder, int position) {
        _Favorites favorites = _favorites.get(position);
        holder.setData(favorites);
    }

    @Override
    public int getItemCount() {
        return _favorites.size();
    }

    class Favorites_Holder extends RecyclerView.ViewHolder {
        TextView order, name;
        ImageView image, star;

        public Favorites_Holder(@NonNull View itemView) {
            super(itemView);

            order = (TextView) itemView.findViewById(R.id.itemAdvertisement_textView_intent);
            name = (TextView) itemView.findViewById(R.id.itemAdvertisement_textView_title);
            image = (ImageView) itemView.findViewById(R.id.itemAdvertisement_imageView_image);
            star = (ImageView) itemView.findViewById(R.id.itemAdvertisement_imageView_favorite);
        }

        public void setData(_Favorites _favorites) {
            order.setText(_favorites.getOrder());
            name.setText(_favorites.getFavorite());
            image.setImageResource(_favorites.getImage());
            star.setImageResource(_favorites.getFavorite());
        }
    }
}
