package com.cankutboratuncer.alicisindan.activities.ui.main.forum.forum;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.activities.utilities.ChatMessage;
import com.cankutboratuncer.alicisindan.databinding.ItemContainerForumCommentBinding;
import com.cankutboratuncer.alicisindan.databinding.ItemContainerForumCommentSelfBinding;

import java.util.List;

public class ForumChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatMessages;
    private Bitmap receiverProfileImage;
    private final String senderId;
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public void setReceiverProfileImage(Bitmap bitmap) {
        receiverProfileImage = bitmap;
    }

    public ForumChatAdapter(List<ChatMessage> chatMessages, String receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        //this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ReceivedMessageViewHolder(ItemContainerForumCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ReceivedMessageViewHolder) holder).setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

//    public int getItemViewType(int position) {
//        Log.d("testos", (chatMessages == null) + " " + (position) + " " + senderId);
//        if (chatMessages.get(position).senderId.equals(senderId)) {
//            return VIEW_TYPE_SENT;
//        } else {
//            return VIEW_TYPE_RECEIVED;
//        }
//    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerForumCommentBinding binding;
        ReceivedMessageViewHolder(ItemContainerForumCommentBinding itemContainerForumCommentBinding) {
            super(itemContainerForumCommentBinding.getRoot());
            binding = itemContainerForumCommentBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            binding.username.setText(chatMessage.senderName);
        }
    }
}
