package com.cankutboratuncer.alicisindan.activities.ui.messaging.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.activities.ui.messaging.listeners.ConversionListener;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.ChatMessage;

import com.cankutboratuncer.alicisindan.databinding.ItemContainerRecentConversationsBinding;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class RecentConversationAdapter extends RecyclerView.Adapter<RecentConversationAdapter.ConversionViewHolder> {


    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversationAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(ItemContainerRecentConversationsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {

        ItemContainerRecentConversationsBinding binding;

        ConversionViewHolder(ItemContainerRecentConversationsBinding itemContainerRecentConversationsBinding) {
            super(itemContainerRecentConversationsBinding.getRoot());
            binding = itemContainerRecentConversationsBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.image.setImageBitmap(ChatMessage.decodeImage(chatMessage.getImage()));
            binding.description.setText(chatMessage.productDescription);
            binding.username.setText(chatMessage.conversionName);
            binding.chat.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                conversionListener.onConversionClicked(chatMessage);
            });
        }
    }


    private Bitmap getConversionImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
