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

public class CategoryExpandedAdapter extends RecyclerView.Adapter<CategoryExpandedAdapter.CategoryExpandedViewHolder> {

    private final CategoryInterface categoryInterface;
    private ArrayList<AllCategories> categories;

    public CategoryExpandedAdapter(ArrayList<AllCategories> categories, CategoryInterface categoryInterface) {
        this.categories = categories;
        this.categoryInterface = categoryInterface;
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    @NonNull
    @Override
    public CategoryExpandedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_expanded, parent, false);
        return new CategoryExpandedViewHolder(view, categoryInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryExpandedViewHolder holder, int position) {
        holder.bind((Category) categories.get(position));
    }

    static class CategoryExpandedViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        private ImageView categoryImage;

        public CategoryExpandedViewHolder(@NonNull View itemView, CategoryInterface categoryInterface) {
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
            this.categoryName.setText(category.getName());
            this.categoryImage.setImageResource(category.getImage());
        }
    }
}