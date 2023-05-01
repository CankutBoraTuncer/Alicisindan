package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityPostAddSubCategoryBinding;

import java.util.List;

public class PostAddSubCategoryActivity extends AppCompatActivity implements CategoryListener{

    private ActivityPostAddSubCategoryBinding binding;
    private LocalSave localSave;
    private List<String> subCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAddSubCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        subCategories = Constants.findSubCategory(category);
        loadSubCategories();
    }

    private void loadSubCategories() {
        CategoryAdapter usersAdapter = new CategoryAdapter(subCategories, this);
        binding.categoriesRecyclerView.setAdapter(usersAdapter);
        binding.categoriesRecyclerView.setVisibility(View.VISIBLE);
        binding.categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onUserClicked(String category) {
        Intent intent = new Intent(getApplicationContext(), PostEditActivity.class);
        startActivity(intent);
        finish();
    }
}