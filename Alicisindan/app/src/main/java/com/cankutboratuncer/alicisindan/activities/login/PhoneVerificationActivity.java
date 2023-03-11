package com.cankutboratuncer.alicisindan.activities.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cankutboratuncer.alicisindan.databinding.ActivityPhoneVerificationBinding;

public class PhoneVerificationActivity extends AppCompatActivity {

    ActivityPhoneVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonVerify.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonChangePhone.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PhoneActivity.class)));
    }
}