package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class ChangeLocationFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_location, container, false);
        LocalSave localSave = new LocalSave(getContext());
        androidx.appcompat.widget.AppCompatButton button = view.findViewById(R.id.changeLocationButton);
        button.setOnClickListener(v14 -> {
            EditText newCountry = view.findViewById(R.id.newCountry);
            String country = newCountry.getText().toString();
            EditText newCity = view.findViewById(R.id.newCity);
            String city = newCity.getText().toString();
            if (country.equals("")||city.equals(""))
            {
                showToast("Enter your country & city.");
            }
            else
            {
                String address = country +"/" + city;
                localSave.putString(Constants.KEY_USER_ADDRESS, null);
                localSave.putString(Constants.KEY_USER_ADDRESS, address);
                try {
                    User user = User.getUser(localSave.getString(Constants.KEY_USER_ID));
                    user.setAddress(localSave.getString(Constants.KEY_PASSWORD), address);
                    showToast("Your address has changed." );
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
