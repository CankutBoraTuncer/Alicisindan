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
//import com.cankutboratuncer.alicisindan.databinding.FragmentChangePasswordBinding;
//import com.cankutboratuncer.alicisindan.databinding.FragmentProfileBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Alicisindan.Password;

public class ChangePasswordFragment extends Fragment {
    private LocalSave localSave;
    private String oldPassword;
    private String newPassword;
    //private FragmentChangePasswordBinding binding;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localSave = new LocalSave(getContext());
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        //binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeButton);
        button.setOnClickListener(v2 -> {
            EditText oldPass = view.findViewById(R.id.oldPassword);
            oldPassword = oldPass.getText().toString();
            EditText newPass = view.findViewById(R.id.newPassword);
            newPassword = newPass.getText().toString();
            if (oldPassword.equals("") || newPassword.equals(""))
            {
                showToast("Enter your old and new password.");
            }
            else
            {
                if ((get_SHA_256_SecurePassword(oldPassword, "salt")).equals(localSave.getString(Constants.KEY_PASSWORD)))
                {
                    localSave.putString(Constants.KEY_PASSWORD, null);
                    localSave.putString(Constants.KEY_PASSWORD, get_SHA_256_SecurePassword(newPassword, "salt"));
                    showToast("Your password has changed.");
                    try {
                        Password.setPassword(localSave.getString(Constants.KEY_USER_ID), get_SHA_256_SecurePassword(oldPassword, "salt"), get_SHA_256_SecurePassword(newPassword, "salt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else
                {

                    showToast("Your old password is not correct." );
                }
            }
        });
        return view;
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
