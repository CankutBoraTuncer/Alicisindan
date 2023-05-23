package com.cankutboratuncer.alicisindan.activities.ui.main.forum.forum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Forum;

import java.util.ArrayList;

public class ForumAdapter {

//    private final ForumInterface forumInterface;
//    ArrayList<Forum> forums;
//
//    public ForumAdapter(ArrayList<Forum> forums, ForumInterface forumInterface) {
//        this.forums = forums;
//        this.forumInterface = forumInterface;
//    }
//
//    @Override
//    public int getItemCount() {
//        return this.forums.size();
//    }
//
//    @NonNull
//    @Override
//    public ForumAdapter.ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
//        return new ForumAdapter.ForumViewHolder(view, forumInterface);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ForumAdapter.ForumViewHolder holder, int position) {
//        holder.bind(forums.get(position));
//    }
//
//    static class ForumViewHolder extends RecyclerView.ViewHolder {
//
//        TextView forumTitle;
//        ImageView forumImage;
//        TextView forumDescription;
//
//        public ForumViewHolder(@NonNull View itemView, ForumInterface advertisementInterface) {
//            super(itemView);
//            this.forumTitle = itemView.findViewById(R.id.forumTitle);
//            this.forumImage = itemView.findViewById(R.id.forumImage);
//            this.forumDescription = itemView.findViewById(R.id.forumDescription);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (advertisementInterface != null) {
//                        int pos = getAdapterPosition();
//
//                        if (pos != RecyclerView.NO_POSITION) {
//                            advertisementInterface.onItemClick(pos);
//                        }
//                    }
//                }
//            });
//        }
//
//        public void bind(Forum forum) {
//            this.forumTitle.setText(forum.getTitle());
//            this.forumImage.setImageResource(forum.getImage());
//            this.forumDescription.setText(forum.getDescription());
//        }
//    }
}
