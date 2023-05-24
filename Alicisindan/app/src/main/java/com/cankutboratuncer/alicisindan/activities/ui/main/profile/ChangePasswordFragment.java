package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
<<<<<<< HEAD
import com.cankutboratuncer.alicisindan.databinding.FragmentChangePasswordBinding;
import com.cankutboratuncer.alicisindan.databinding.FragmentProfileBinding;
=======
//import com.cankutboratuncer.alicisindan.databinding.FragmentChangePasswordBinding;
//import com.cankutboratuncer.alicisindan.databinding.FragmentProfileBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
>>>>>>> main

import Alicisindan.Password;

public class ChangePasswordFragment extends Fragment {
    private LocalSave localSave;
    private String oldPassword;
    private String newPassword;
<<<<<<< HEAD
    private FragmentChangePasswordBinding binding;
=======
    //private FragmentChangePasswordBinding binding;
>>>>>>> main
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localSave = new LocalSave(getContext());
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
<<<<<<< HEAD
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
=======
        //binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
>>>>>>> main
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeButton);
        button.setOnClickListener(v2 -> {
            EditText oldPass = view.findViewById(R.id.oldPassword);
            oldPassword = oldPass.getText().toString();
<<<<<<< HEAD
            EditText newPass = view.findViewById(R.id.oldPassword);
=======
            EditText newPass = view.findViewById(R.id.newPassword);
>>>>>>> main
            newPassword = newPass.getText().toString();
            if (oldPassword.equals("") || newPassword.equals(""))
            {
                showToast("Enter your old and new password.");
            }
            else
            {
<<<<<<< HEAD
                if (oldPassword.equals(localSave.getString(Constants.KEY_PASSWORD)))
                {
                    localSave.putString(Constants.KEY_PASSWORD, newPassword);
                    showToast("Your password has changed.");
                    try {
                        Password.setPassword(Constants.KEY_USER_ID, oldPassword, newPassword);
=======
                if ((get_SHA_256_SecurePassword(oldPassword, "salt")).equals(localSave.getString(Constants.KEY_PASSWORD)))
                {
                    localSave.putString(Constants.KEY_PASSWORD, null);
                    localSave.putString(Constants.KEY_PASSWORD, get_SHA_256_SecurePassword(newPassword, "salt"));
                    showToast("Your password has changed.");
                    try {
                        Password.setPassword(localSave.getString(Constants.KEY_USER_ID), get_SHA_256_SecurePassword(oldPassword, "salt"), get_SHA_256_SecurePassword(newPassword, "salt"));
>>>>>>> main
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else
                {
<<<<<<< HEAD
                    showToast("Your old password is not correct.");
=======

                    showToast("Your old password is not correct." );
>>>>>>> main
                }
            }
        });
        return view;
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
<<<<<<< HEAD
}
=======

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
>>>>>>> main
