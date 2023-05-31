package com.cankutboratuncer.alicisindan.activities.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityPrivacyPolicyBinding;
import com.cankutboratuncer.alicisindan.databinding.ActivitySignUpBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;
    LocalSave localSave;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        localSave = new LocalSave(getApplicationContext());
        setListeners();
    }

    public void setListeners()
    {
        AppCompatButton acceptButton = binding.signUpActivityButtonAccept;
        acceptButton.setOnClickListener(v -> finish());
    }
}