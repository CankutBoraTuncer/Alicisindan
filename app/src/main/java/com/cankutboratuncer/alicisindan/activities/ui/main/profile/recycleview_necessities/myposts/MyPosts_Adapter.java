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
            this.order.setText(myposts.getOrder());
            this.name.setText(myposts.getName());
            this.image.setImageBitmap(myposts.getBitmap());
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