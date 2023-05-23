package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.databinding.ItemContainerCategoryBinding;

import java.util.List;

public class PostCategoryAdapter extends RecyclerView.Adapter<PostCategoryAdapter.CategoryViewHolder> {

    private final List<AllCategories> categories;
    private final CategoryListener categoryListener;

    public PostCategoryAdapter(List<AllCategories> categories, CategoryListener categoryListener) {
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
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        ItemContainerCategoryBinding binding;
        private ImageView categoryImage;

        CategoryViewHolder(ItemContainerCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            binding = itemCategoryBinding;
            categoryImage = binding.categoryImage;
        }

        void bind(AllCategories category) {
            this.categoryImage.setImageResource(category.getImage());
            binding.categoryName.setText(category.getName());
            binding.getRoot().setOnClickListener(v -> categoryListener.onUserClicked(category.getName()));
        }
    }
}
