package com.cankutboratuncer.alicisindan.activities.ui.login;

import static com.cankutboratuncer.alicisindan.activities.utilities.Util.showToast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.activities.utilities.Util;
import com.cankutboratuncer.alicisindan.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Alicisindan.User;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    LocalSave localSave;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        localSave = new LocalSave(getApplicationContext());
        emailEditText = findViewById(R.id.signUpActivity_editText_email);
        setListeners();
    }

    private void setListeners() {
        binding.signUpActivityButtonSignIn.setOnClickListener(v -> onBackPressed());
        binding.signUpActivityButtonSignUp.setOnClickListener(v -> {
            if (isValidSignUpDetails()) {
                try {
                    signUp();
                } catch (Exception e) {
                    showToast("Registration failed.", getApplicationContext());
                }
                loading(false);
            }
        });
        binding.activitySignUpImageViewCloseIcon.setOnClickListener(v -> {
                localSave.putBoolean(Constants.KEY_IS_USER_SKIP, true);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        );
        binding.signUpActivityButtonSeeTOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
            }
        });
    }


    private void signUp() {
        loading(true);
        try {

            String username = binding.signUpActivityEditTextUserName.getText().toString();
            String email = emailEditText.getText().toString();
            String password = get_SHA_256_SecurePassword(binding.signUpActivityEditTextPassword.getText().toString(), "salt");
            String name = binding.signUpActivityEditTextName.getText().toString();
            String surname = binding.signUpActivityEditTextSurname.getText().toString();
            String phone = "";
            String address = binding.signUpActivityEditTextCountry.getText().toString() + "/" + binding.signUpActivityEditTextCity.getText().toString();
            String birthday = "";

            if (User.emailExists(email)) {
                showToast("There is already an user with this email.", getApplicationContext());
            } else if (User.usernameExists(username)) {
                showToast("There is already an user with this username.", getApplicationContext());
            } else {
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("Tokenoo", "Failed");
                            return;
                        }
                        String token = task.getResult();
                        Log.d("Tokenoo", token);
                        User user = new User(username, name, surname, birthday, address, email, phone);
                        try {
                            if (!User.emailExists(email)) {
                                user.registerUser(password);
                                User.setUserToken(user.getID(), password, token);
                                String image = Util.drawableToString(getResources(), R.drawable.default_user_img);
                                user.setImage(password, image);
                                localSave.saveUser(user.getID(), email, phone, username, password, name, surname, address, token);
                                loading(false);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                loading(false);
                                showToast("The user already exists", getApplicationContext());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            showToast(e.getMessage(), getApplicationContext());
            showToast("An error occurred. Please try again.", getApplicationContext());
        }
    }

    private Boolean isValidSignUpDetails() {
        if (binding.signUpActivityEditTextName.getText().toString().trim().isEmpty()) {
            showToast("Enter Name", getApplicationContext());
            return false;
        } else if (binding.signUpActivityEditTextSurname.getText().toString().trim().isEmpty()) {
            showToast("Enter Surname", getApplicationContext());
            return false;
        } else if (binding.signUpActivityEditTextUserName.getText().toString().trim().isEmpty()) {
            showToast("Enter Username", getApplicationContext());
            return false;
        } else if (emailEditText.getText().toString().trim().isEmpty()) {
            showToast("Enter Email", getApplicationContext());
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            showToast("Error invalid email", getApplicationContext());
            return false;
        } else if (binding.signUpActivityEditTextPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter password", getApplicationContext());
            return false;
        } else if (binding.signUpActivityEditTextConfirmPassword.getText().toString().trim().isEmpty()) {
            showToast("Confirm your password", getApplicationContext());
            return false;
        } else if (!binding.signUpActivityEditTextPassword.getText().toString().equals(binding.signUpActivityEditTextConfirmPassword.getText().toString())) {
            showToast("Password & confirm password are not matching", getApplicationContext());
            return false;
        } else if (!binding.signUpActivityCheckBoxTermsAndServices.isChecked()) {
            showToast("Please check the Terms & Services", getApplicationContext());
            return false;
        } else {
            return true;
        }
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