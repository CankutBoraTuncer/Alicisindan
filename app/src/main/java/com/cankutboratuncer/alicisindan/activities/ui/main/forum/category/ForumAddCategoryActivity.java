package com.cankutboratuncer.alicisindan.activities.ui.main.forum.category;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.cankutboratuncer.alicisindan.activities.data.database.CategoryTest;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.CategoryListener;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddSubCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostCategoryAdapter;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.databinding.ActivityForumAddCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class ForumAddCategoryActivity extends AppCompatActivity implements CategoryListener {

    ActivityForumAddCategoryBinding binding;
    private List<AllCategories> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categories = CategoryTest.categories;
        loadCategories();
    }

    private void loadCategories() {
        PostCategoryAdapter usersAdapter = new PostCategoryAdapter(categories, this);
        binding.categoriesRecyclerView.setAdapter(usersAdapter);
        binding.categoriesRecyclerView.setVisibility(View.VISIBLE);
        binding.categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onUserClicked(String category) {
        Intent intent = new Intent(getApplicationContext(), ForumAddSubCategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
        finish();
    }
}