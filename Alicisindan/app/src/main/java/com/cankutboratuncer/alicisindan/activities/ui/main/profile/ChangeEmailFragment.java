package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
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

import Alicisindan.Password;
import Alicisindan.User;

public class ChangeEmailFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        LocalSave localSave = new LocalSave(getContext());
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeButton);
        button.setOnClickListener(v6 -> {
            EditText newEmail = view.findViewById(R.id.newEmail);
            String email = newEmail.getText().toString();
            if (email.equals(""))
            {
                showToast("Enter your new e-mail.");
            }
            else
            {
                if (email.contains("@"))
                {
                    localSave.putString(Constants.KEY_USER_EMAIL, null);
                    localSave.putString(Constants.KEY_USER_EMAIL, email);
                    showToast("Your e-mail has changed." );
                    try {
                        User user = User.getUser(localSave.getString(Constants.KEY_USER_ID));
                        user.setEmail(localSave.getString(Constants.KEY_PASSWORD), email);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
                else
                {
                    showToast("Your e-mail is not valid." );
                }
            }
        });
        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

