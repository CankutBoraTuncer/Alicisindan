package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.cankutboratuncer.alicisindan.activities.data.database.CategoryTest;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityPostAddCategoryBinding;

import java.util.List;

public class PostAddCategoryActivity extends AppCompatActivity implements CategoryListener {

    private ActivityPostAddCategoryBinding binding;
    private String type;
    private List<AllCategories> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            type = bundle.getString("type");
        }
        binding.topPanel.setText("I want to " + type);
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
       Intent intent = new Intent(getApplicationContext(), PostAddSubCategoryActivity.class);
       intent.putExtra("category", category);
       intent.putExtra("type", type);
       startActivity(intent);
       finish();
    }
}