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

import Alicisindan.AlicisindanException;
import Alicisindan.User;

public class ChangeNameFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_name, container, false);
        LocalSave localSave = new LocalSave(getContext());
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeNameButton);
        button.setOnClickListener(v13 -> {
            EditText newName = view.findViewById(R.id.newName);
            EditText newSurname = view.findViewById(R.id.newSurname);
            String name = newName.getText().toString();
            String surname = newSurname.getText().toString();
            if (name.equals("")||surname.equals(""))
            {
                showToast("Enter your name & surname.");
            }
            else
            {
                localSave.putString(Constants.KEY_USER_NAME, name);
                localSave.putString(Constants.KEY_USER_SURNAME, surname);
                try {
                    User user = User.getUser(localSave.getString(Constants.KEY_USER_ID));
                    user.setName(localSave.getString(Constants.KEY_PASSWORD), name);
                    user.setSurname(localSave.getString(Constants.KEY_PASSWORD), surname);
                    showToast("Your name & surname have changed." );
                } catch (AlicisindanException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
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
