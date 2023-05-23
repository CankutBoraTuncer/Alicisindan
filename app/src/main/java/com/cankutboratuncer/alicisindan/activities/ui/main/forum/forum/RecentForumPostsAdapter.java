package com.cankutboratuncer.alicisindan.activities.ui.main.forum.forum;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.activities.utilities.ChatMessage;
import com.cankutboratuncer.alicisindan.activities.utilities.Forum;
import com.cankutboratuncer.alicisindan.databinding.ItemContainerRecentConversationsBinding;
import com.cankutboratuncer.alicisindan.databinding.ItemForumBinding;

import java.util.List;

public class RecentForumPostsAdapter extends RecyclerView.Adapter<RecentForumPostsAdapter.ForumPostViewHolder> {
    private final List<Forum> forumPosts;
    private final ForumListener forumListener;

    public RecentForumPostsAdapter(List<Forum> forumPosts, ForumListener forumListener) {
        this.forumPosts = forumPosts;
        this.forumListener = forumListener;
    }

    @NonNull
    @Override
    public ForumPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentForumPostsAdapter.ForumPostViewHolder(ItemForumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForumPostViewHolder holder, int position) {
        holder.setData(forumPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return forumPosts.size();
    }

    class ForumPostViewHolder extends RecyclerView.ViewHolder {

        ItemForumBinding binding;

        ForumPostViewHolder(ItemForumBinding itemForumBinding) {
            super(itemForumBinding.getRoot());
            binding = itemForumBinding;
        }

        void setData(Forum forum) {
            binding.image.setImageBitmap(Forum.decodeImage(forum.getForumImage()));
            binding.description.setText(forum.getForumDescription());
            binding.title.setText(forum.getForumTitle());
            binding.username.setText(forum.getForumOwnerName());
            binding.getRoot().setOnClickListener(v -> {
                forumListener.onForumPostClicked(forum);
            });
        }
    }
}

