package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.activities.utilities.User;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }



    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocalSave localSave = new LocalSave(getContext());
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        if (localSave.getString(Constants.KEY_USER_ID) == null) {
            showToast("You have to log in first.");
            return view;
        }

        CardView changePassword = view.findViewById(R.id.accountFragment_cardView_changePassword);
        CardView userInfo = view.findViewById(R.id.accountFragment_cardView_userInfo);
        CardView delete = view.findViewById(R.id.accountFragment_cardView_deleteAccount);
        changePassword.setOnClickListener(v -> {
            Fragment fragment = new ChangePasswordFragment();
            loadFragment(fragment);
        });
        userInfo.setOnClickListener(v3 -> {
            Fragment fragment = new UserInfoFragment();
            loadFragment(fragment);
        });
        delete.setOnClickListener(v6 -> {
            Fragment fragment = new DeleteAccountFragment();
            loadFragment(fragment);
        });
        return view;

    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}