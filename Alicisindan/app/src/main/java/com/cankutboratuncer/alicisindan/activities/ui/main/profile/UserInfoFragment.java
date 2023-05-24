package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

public class UserInfoFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        LocalSave localSave = new LocalSave(getContext());
        TextView name = view.findViewById(R.id.usersName);
        name.setText(localSave.getString(Constants.KEY_USER_NAME) + " " + localSave.getString(Constants.KEY_USER_SURNAME));
        TextView username = view.findViewById(R.id.usersUsername);
        username.setText(localSave.getString(Constants.KEY_USER_USERNAME));
        TextView email = view.findViewById(R.id.usersEmail);
        email.setText(localSave.getString(Constants.KEY_USER_EMAIL));
        return view;
    }
}
