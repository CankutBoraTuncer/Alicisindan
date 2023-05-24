package com.cankutboratuncer.alicisindan.activities.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivitySignUpBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Alicisindan.User;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    LocalSave localSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signUpActivityEditTextCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Constants.COUNTRIES));
        localSave = new LocalSave(getApplicationContext());
        setListeners();
    }

    private void setListeners() {
        binding.signUpActivityButtonSignIn.setOnClickListener(v -> onBackPressed());
        binding.signUpActivityButtonSignUp.setOnClickListener(v -> {
            if (isValidSignUpDetails()) {
                try {
                    signUp();
                } catch (Exception e) {
                    showToast("The register failed.");
                    throw new RuntimeException(e);
                }
            }
        });
        binding.activitySignUpImageViewCloseIcon.setOnClickListener(v -> {
                    localSave.putBoolean(Constants.KEY_IS_USER_SKIP, true);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
        );

        binding.signUpActivityEditTextCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String country = binding.signUpActivityEditTextCountry.getText().toString();
                if (Constants.COUNTRIES.contains(country)) {
                    binding.signUpActivityEditTextCity.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, Constants.CITIES.get(0)));
                }
            }
        });
    }

    private void signUp() {
        loading(true);
        try {
            if (!User.emailExists(binding.signUpActivityEditTextEmailOrPhoneNumber.getText().toString())) {
                String username = binding.signUpActivityEditTextUserName.getText().toString();
                String email = binding.signUpActivityEditTextEmailOrPhoneNumber.getText().toString();
                String password = get_SHA_256_SecurePassword(binding.signUpActivityEditTextPassword.getText().toString(), "salt");
                String name = binding.signUpActivityEditTextName.getText().toString();
                String surname = binding.signUpActivityEditTextSurname.getText().toString();
                String phone = binding.signUpActivityEditTextEmailOrPhoneNumber.getText().toString();
                String address = binding.signUpActivityEditTextCountry.getText().toString() + "/" + binding.signUpActivityEditTextCity.getText().toString();
                String birthday = "";

                User user = new User(username, name, surname, birthday, address, email, phone);
                user.registerUser(password);
                if (User.emailExists(email)) {
                    localSave.saveUser(user.getID(), email, phone, username, password, name, surname, address);
                    loading(false);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    loading(false);
                    showToast("The user already exists");
                }
            } else {
                showToast("There is already a user with this email.");
            }
        } catch (Exception e) {
            showToast(e.getMessage());
        }
    }

    private Boolean isValidSignUpDetails() {
        if (binding.signUpActivityEditTextName.getText().toString().trim().isEmpty()) {
            showToast("Enter Name");
            return false;
        } else if (binding.signUpActivityEditTextSurname.getText().toString().trim().isEmpty()) {
            showToast("Enter Surname");
            return false;
        } else if (binding.signUpActivityEditTextUserName.getText().toString().trim().isEmpty()) {
            showToast("Enter Username");
            return false;
        } else if (binding.signUpActivityEditTextEmailOrPhoneNumber.getText().toString().trim().isEmpty()) {
            showToast("Enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.signUpActivityEditTextEmailOrPhoneNumber.getText().toString()).matches()) {
            showToast("Error invalid email");
            return false;
        } else if (binding.signUpActivityEditTextPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else if (binding.signUpActivityEditTextConfirmPassword.getText().toString().trim().isEmpty()) {
            showToast("Confirm your password");
            return false;
        } else if (!binding.signUpActivityEditTextPassword.getText().toString().equals(binding.signUpActivityEditTextConfirmPassword.getText().toString())) {
            showToast("Password & confirm password are not matching");
            return false;
        } else if (!binding.signUpActivityCheckBoxTermsAndServices.isChecked()) {
            showToast("Please check the Terms & Services");
            return false;
        } else {
            return true;
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            binding.signUpActivityButtonSignUp.setVisibility(View.INVISIBLE);
            binding.signUpActivityProgressBar.setVisibility(View.VISIBLE);
        } else {
            binding.signUpActivityButtonSignUp.setVisibility(View.VISIBLE);
            binding.signUpActivityProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private static String get_SHA_256_SecurePassword(String passwordToHash,
                                                     String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}