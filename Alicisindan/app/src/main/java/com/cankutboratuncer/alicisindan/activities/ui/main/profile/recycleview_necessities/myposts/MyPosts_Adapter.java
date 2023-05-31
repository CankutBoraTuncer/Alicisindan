package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementInterface;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class MyPosts_Adapter extends  RecyclerView.Adapter<MyPosts_Adapter.MyPosts_Holder> {
    private ArrayList<_MyPosts> _myposts;
    private Context _context;
    private OnItemClickListener listener;
    private boolean self;


    public MyPosts_Adapter(ArrayList<_MyPosts> _myposts, Context _context, boolean self) {
        this._myposts = _myposts;
        this._context = _context;
        this.self = self;
    }

    @NonNull
    @Override
    public MyPosts_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.item_advertisement, parent, false);
        return new MyPosts_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPosts_Holder holder, int position) {
        _MyPosts myposts = _myposts.get(position);
        holder.setData(myposts);
    }

    @Override
    public int getItemCount() {
        return _myposts.size();
    }

    class MyPosts_Holder extends RecyclerView.ViewHolder {

        TextView order, name, intent, price;
        ImageView image;

        public MyPosts_Holder(@NonNull View itemView) {
            super(itemView);
            order = (TextView) itemView.findViewById(R.id.itemAdvertisement_textView_tag);
            name = (TextView) itemView.findViewById(R.id.itemAdvertisement_textView_title);
            image = (ImageView) itemView.findViewById(R.id.itemAdvertisement_imageView_image);
            intent = (TextView) itemView.findViewById(R.id.itemAdvertisement_textView_intent);
            price = (TextView) itemView.findViewById(R.id.itemAdvertisement_textView_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(_myposts.get(position), position);
                    }
                }
            });
        }

        public void setData(_MyPosts myposts) {
            if (myposts.getOrder().equals("sell")) {
                this.order.setText("Sell");
                order.setBackgroundResource(R.drawable.background_sell_tag);
                if (self) {
                    this.intent.setText("You want to sell");
                } else {
                    this.intent.setText("They want to sell");
                }

            } else {
                this.order.setText("Buy");
                order.setBackgroundResource(R.drawable.background_buy_tag);
                if (self) {
                    this.intent.setText("You want to buy");
                } else {
                    this.intent.setText("They want to buy");
                }
            }

            this.name.setText(myposts.getName());
            this.image.setImageBitmap(myposts.getBitmap());
            this.price.setText("$" + myposts.getPrice());
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(_MyPosts _myposts, int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}