package com.cankutboratuncer.alicisindan.activities.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity {
    ActivityLoadingBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Handler handler = new Handler();
        handler.postDelayed(() -> checkIfSignedIn(), 1000);
    }

    public void checkIfSignedIn() {
        LocalSave localSave = new LocalSave(getApplicationContext());
        if (localSave.getBoolean(Constants.KEY_IS_SIGNED_IN) || localSave.getBoolean(Constants.KEY_IS_USER_SKIP)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }
}


