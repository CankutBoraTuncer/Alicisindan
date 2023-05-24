package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
<<<<<<< HEAD
=======
import com.cankutboratuncer.alicisindan.activities.utilities.User;
>>>>>>> main

public class AccountFragment extends Fragment {

<<<<<<< HEAD
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LocalSave localSave;

=======
>>>>>>> main
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
<<<<<<< HEAD
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        localSave =  new LocalSave(getContext());
        CardView changePassword = view.findViewById(R.id.accountFragment_cardView_changePassword);
=======
        LocalSave localSave = new LocalSave(getContext());
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        CardView changePassword = view.findViewById(R.id.accountFragment_cardView_changePassword);
        CardView userInfo = view.findViewById(R.id.accountFragment_cardView_userInfo);
        CardView location = view.findViewById(R.id.accountFragment_cardView_addressInfo);
        CardView email = view.findViewById(R.id.accountFragment_cardView_changeMailOrPhone);
        CardView delete = view.findViewById(R.id.accountFragment_cardView_deleteAccount);
>>>>>>> main
        changePassword.setOnClickListener(v -> {
            Fragment fragment = new ChangePasswordFragment();
            loadFragment(fragment);
        });
<<<<<<< HEAD
=======
        userInfo.setOnClickListener(v3 -> {
            Fragment fragment = new UserInfoFragment();
            loadFragment(fragment);
        });
        location.setOnClickListener(v4 -> {
            Fragment fragment = new AddressFragment();
            loadFragment(fragment);
        });
        email.setOnClickListener(v5 -> {
            Fragment fragment = new ChangeEmailFragment();
            loadFragment(fragment);
        });
        delete.setOnClickListener(v6 -> {
            Fragment fragment = new DeleteAccountFragment();
            loadFragment(fragment);
        });
>>>>>>> main
        return view;

    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}