package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Alicisindan.AlicisindanException;
import Alicisindan.Password;
import Alicisindan.User;

public class DeleteAccountFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);
        LocalSave localSave = new LocalSave(getContext());
        //binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
        androidx.appcompat.widget.AppCompatButton deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v8 -> {
            EditText passwordEdit = view.findViewById(R.id.password);
            String enteredPassword = passwordEdit.getText().toString();
            if (enteredPassword.equals(""))
            {
                showToast("Enter your password.");
            }
            else
            {
                if ((get_SHA_256_SecurePassword(enteredPassword, "salt")).equals(localSave.getString(Constants.KEY_PASSWORD)))
                {
                    try {
                        System.out.println(localSave.getString(Constants.KEY_PASSWORD));
                        User deletedUser = User.getUser(localSave.getString(Constants.KEY_USER_ID));
                        System.out.println(deletedUser.getEmail());
                        deletedUser.deleteUser(localSave.getString(Constants.KEY_PASSWORD));
                        showToast("Your account is deleted." );
                        Fragment fragment = new LogOutFragment();
                        loadFragment(fragment);
                    } catch (AlicisindanException e) {
                        showToast("Your account cannot be deleted." );
                        System.out.println(e.getType().toString());
                        e.printStackTrace();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //User.deleteUser();
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


    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}