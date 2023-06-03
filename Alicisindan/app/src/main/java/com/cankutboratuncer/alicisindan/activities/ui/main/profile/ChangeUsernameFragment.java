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

import Alicisindan.User;

public class ChangeUsernameFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_username, container, false);
        LocalSave localSave = new LocalSave(getContext());
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeUsernameButton);
        button.setOnClickListener(v12 -> {
            EditText newUsername = view.findViewById(R.id.newUsername);
            String username = newUsername.getText().toString();
            if (username.equals("")) {
                showToast("Enter your new username.");
            } else {
                localSave.putString(Constants.KEY_USER_USERNAME, username);
                try {
                    User user = User.getUser(localSave.getString(Constants.KEY_USER_ID));
                    user.setUsername(localSave.getString(Constants.KEY_PASSWORD), username);
                    showToast("Your username has changed.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
