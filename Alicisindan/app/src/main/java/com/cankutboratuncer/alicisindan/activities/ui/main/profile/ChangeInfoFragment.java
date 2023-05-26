package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

public class ChangeInfoFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_info, container, false);
        LocalSave localSave = new LocalSave(getContext());
        CardView email = view.findViewById(R.id.change_cardView_email);
        CardView name = view.findViewById(R.id.change_cardView_name);
        CardView username = view.findViewById(R.id.change_cardView_username);
        CardView location = view.findViewById(R.id.change_cardView_location);
        email.setOnClickListener(v10 -> {
            Fragment fragment = new ChangeEmailFragment();
            loadFragment(fragment);
        });
        name.setOnClickListener(v11 -> {
            Fragment fragment = new ChangeNameFragment();
            loadFragment(fragment);
        });
        username.setOnClickListener(v12 -> {
            Fragment fragment = new ChangeUsernameFragment();
            loadFragment(fragment);
        });
        location.setOnClickListener(v13 -> {
            Fragment fragment = new ChangeLocationFragment();
            loadFragment(fragment);
        });
        return view;
    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}
