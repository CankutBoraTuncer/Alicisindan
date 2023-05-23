package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.cankutboratuncer.alicisindan.activities.data.database.CategoryTest;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.PostEditActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.forum.category.ForumEditActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.Subcategory;
import com.cankutboratuncer.alicisindan.databinding.ActivityPostAddSubCategoryBinding;

import java.util.List;

public class PostAddSubCategoryActivity extends AppCompatActivity implements CategoryListener{

    private ActivityPostAddSubCategoryBinding binding;
    private List<AllCategories> subCategories;
    String category, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAddSubCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        type = intent.getStringExtra("type");
        binding.topPanel.setText("I want to " + type);
        subCategories = CategoryTest.createSubCategories(category);
        loadSubCategories();

    }

    private void loadSubCategories() {
        PostCategoryAdapter usersAdapter = new PostCategoryAdapter(subCategories, this);
        binding.categoriesRecyclerView.setAdapter(usersAdapter);
        binding.categoriesRecyclerView.setVisibility(View.VISIBLE);
        binding.categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onUserClicked(String subCategory) {
        Intent intent = new Intent(getApplicationContext(), PostEditActivity.class);
        intent.putExtra("category", category + "/" + subCategory);
        intent.putExtra("type", type);
        startActivity(intent);
        finish();
    }
}