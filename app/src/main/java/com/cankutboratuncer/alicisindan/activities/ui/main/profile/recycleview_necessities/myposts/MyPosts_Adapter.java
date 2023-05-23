package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class MyPosts_Adapter extends  RecyclerView.Adapter<MyPosts_Adapter.MyPosts_Holder> {
    private ArrayList<_MyPosts> _myposts;
    private Context _context;

    public MyPosts_Adapter(ArrayList<_MyPosts> _myposts, Context _context) {
        this._myposts = _myposts;
        this._context = _context;
    }

    @NonNull
    @Override
    public MyPosts_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.item_myposts, parent, false);
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

        TextView order, name;
        ImageView image;

        public MyPosts_Holder(@NonNull View itemView) {
            super(itemView);

            order = (TextView) itemView.findViewById(R.id.myposts_item_textview_order);
            name = (TextView) itemView.findViewById(R.id.myposts_item_textview_name);
            image = (ImageView) itemView.findViewById(R.id.myposts_item_imageview_image);
        }

        public void setData(_MyPosts myposts) {
            this.order.setText(myposts.getOrder());
            this.name.setText(myposts.getName());
            this.image.setImageResource(myposts.getImage());
        }
    }
}
