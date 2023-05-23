package com.cankutboratuncer.alicisindan.activities.ui.main.forum.category;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.databinding.ItemContainerCategoryBinding;

import java.util.List;

public class ForumCategoryAdapter extends RecyclerView.Adapter<ForumCategoryAdapter.CategoryViewHolder> {

    private final List<String> categories;
    private final ForumCategoryListener categoryListener;

    public ForumCategoryAdapter(List<String> categories, ForumCategoryListener categoryListener) {
        this.categories = categories;
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerCategoryBinding itemContainerCategoryBinding = ItemContainerCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(itemContainerCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.setCategoryTitle(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        ItemContainerCategoryBinding binding;

        CategoryViewHolder(ItemContainerCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            binding = itemCategoryBinding;
        }

        void setCategoryTitle(String title) {
            binding.categoryName.setText(title);
            binding.getRoot().setOnClickListener(v -> categoryListener.onUserClicked(title));
        }
    }
}
