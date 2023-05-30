package com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cankutboratuncer.alicisindan.databinding.ActivityPostTypeBinding;

public class PostTypeActivity extends AppCompatActivity {

    ActivityPostTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonBuy.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), PostAddCategoryActivity.class);
            intent.putExtra("type", "buy");
            startActivity(intent);
            finish();
        });
        binding.buttonSell.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), PostAddCategoryActivity.class);
            intent.putExtra("type", "sell");
            startActivity(intent);
            finish();
        });

    }
}