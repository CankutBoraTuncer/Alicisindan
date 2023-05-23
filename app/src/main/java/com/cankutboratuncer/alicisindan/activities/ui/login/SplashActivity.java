package com.cankutboratuncer.alicisindan.activities.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private LocalSave localSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localSave = new LocalSave(getApplicationContext());
        if (localSave.getBoolean(Constants.KEY_IS_SIGNED_IN) || localSave.getBoolean(Constants.KEY_IS_USER_SKIP)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else{
            binding = ActivitySplashBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            binding.button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignInActivity.class)));
            setListeners();
        }
    }

    private void setListeners() {
        binding.button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignInActivity.class)));
        binding.activitySplashImageViewCloseIcon.setOnClickListener(v -> {
            localSave.putBoolean(Constants.KEY_IS_USER_SKIP, true);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }
}