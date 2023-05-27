package com.cankutboratuncer.alicisindan.activities.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cankutboratuncer.alicisindan.activities.ui.main.MainActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

// Password Hashing
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Alicisindan.Password;
import Alicisindan.User;


public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    private LocalSave localSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localSave = new LocalSave(getApplicationContext());
        if (localSave.getBoolean(Constants.KEY_IS_SIGNED_IN) || localSave.getBoolean(Constants.KEY_IS_USER_SKIP)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void signIn() throws Exception {
        loading(true);
        String userID = Password.emailToID(binding.signInActivityEditTextEmailOrPhoneNumber.getText().toString());
        String userPassword = get_SHA_256_SecurePassword(binding.signInActivityEditTextPassword.getText().toString(), "salt");
        Log.d("PasswordCheck", "UserId: " + userID + "\nPassword: "+ userPassword);
        if (Password.isCorrectPassword(userID, userPassword)) {
            String token = FirebaseMessaging.getInstance().getToken().toString();
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.d("Tokenoo", "Failed");
                        return;
                    }
                    String token = task.getResult();
                    Log.d("Tokenoo", token);
                    try {
                        User.setUserToken(userID, userPassword, token);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            registerUser(userID, userPassword, token);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            loading(false);
            showToast("Incorrect username or password!");
        }
    }

    private void registerUser(String userID, String password, String token) {
        try {
            User user = User.getUser(userID);
            localSave.saveUser(user.getID(), user.getEmail(), user.getPhone(), user.getUsername(), password, user.getName(), user.getSurname(), user.getAddress(), token);
        } catch (Exception e) {
            localSave.clear();
            showToast("An error occurred while trying to save user information.");
        }
    }

    private void setListeners() {
        binding.signInActivityButtonSignUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.signInActivityButtonSignIn.setOnClickListener(v -> {
            if (isValidSignInDetails()) {
                try {
                    signIn();
                } catch (Exception e) {
                    showToast("User not found.");
                }
            }
        });
        binding.activitySignInImageViewCloseIcon.setOnClickListener(v -> {
            localSave.putBoolean(Constants.KEY_IS_USER_SKIP, true);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }

    private Boolean isValidSignInDetails() {
        if (binding.signInActivityEditTextEmailOrPhoneNumber.getText().toString().trim().isEmpty()) {
            showToast("Enter your email.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.signInActivityEditTextEmailOrPhoneNumber.getText().toString()).matches()) {
            showToast("Invalid email.");
            return false;
        } else if (binding.signInActivityEditTextPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter your password.");;
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
            binding.signInActivityButtonSignIn.setVisibility(View.INVISIBLE);
            binding.signInActivityProgressBar.setVisibility(View.VISIBLE);
        } else {
            binding.signInActivityButtonSignIn.setVisibility(View.VISIBLE);
            binding.signInActivityProgressBar.setVisibility(View.INVISIBLE);
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