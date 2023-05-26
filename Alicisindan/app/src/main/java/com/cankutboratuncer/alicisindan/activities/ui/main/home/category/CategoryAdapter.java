package com.cankutboratuncer.alicisindan.activities.ui.main.home.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryInterface;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final CategoryInterface categoryInterface;
    private ArrayList<AllCategories> categories;

    public CategoryAdapter(ArrayList<AllCategories> categories, CategoryInterface categoryInterface) {
        this.categories = categories;
        this.categoryInterface = categoryInterface;
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view, categoryInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind((Category) categories.get(position));
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        private ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView, CategoryInterface categoryInterface) {
            super(itemView);
            this.categoryName = itemView.findViewById(R.id.itemCategory_textView_categoryName);
            this.categoryImage = itemView.findViewById(R.id.itemCategory_imageView_categoryImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (categoryInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            categoryInterface.onCategoryClick(pos);
                        }
                    }
                }
            });
        }

        public void bind(Category category) {
            this.categoryImage.setImageResource(category.getImage());
        }
    }
}