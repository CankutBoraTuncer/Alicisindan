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
import com.cankutboratuncer.alicisindan.databinding.FragmentChangePasswordBinding;
import com.cankutboratuncer.alicisindan.databinding.FragmentProfileBinding;

import Alicisindan.Password;

public class ChangePasswordFragment extends Fragment {
    private LocalSave localSave;
    private String oldPassword;
    private String newPassword;
    private FragmentChangePasswordBinding binding;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localSave = new LocalSave(getContext());
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeButton);
        button.setOnClickListener(v2 -> {
            EditText oldPass = view.findViewById(R.id.oldPassword);
            oldPassword = oldPass.getText().toString();
            EditText newPass = view.findViewById(R.id.oldPassword);
            newPassword = newPass.getText().toString();
            if (oldPassword.equals("") || newPassword.equals(""))
            {
                showToast("Enter your old and new password.");
            }
            else
            {
                if (oldPassword.equals(localSave.getString(Constants.KEY_PASSWORD)))
                {
                    localSave.putString(Constants.KEY_PASSWORD, newPassword);
                    showToast("Your password has changed.");
                    try {
                        Password.setPassword(Constants.KEY_USER_ID, oldPassword, newPassword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    showToast("Your old password is not correct.");
                }
            }
        });
        return view;
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
