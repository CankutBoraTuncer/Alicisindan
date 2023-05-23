package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        CardView cardView_myPosts = view.findViewById(R.id.profileFragment_cardView_myPosts);
        CardView cardView_messages = view.findViewById(R.id.profileFragment_cardView_messages);
        CardView cardView_account = view.findViewById(R.id.profileFragment_cardView_account);
        CardView cardView_favorite = view.findViewById(R.id.profileFragment_cardView_favorite);
        CardView cardView_help = view.findViewById(R.id.profileFragment_cardView_help);
        CardView cardView_logOut = view.findViewById(R.id.profileFragment_cardView_logOut);

        cardView_myPosts.setOnClickListener(view11 -> {
            Fragment fragment = new MyPostsFragment();
            loadFragment(fragment);
        });
        cardView_messages.setOnClickListener(view12 -> {
            Fragment fragment = new MessagesFragment();
            loadFragment(fragment);
        });
        cardView_account.setOnClickListener(view13 -> {
            Fragment fragment = new AccountFragment();
            loadFragment(fragment);
        });
        cardView_favorite.setOnClickListener(view14 -> {
            Fragment fragment = new FavoritesFragment();
            loadFragment(fragment);
        });
        cardView_help.setOnClickListener(view15 -> {
            Fragment fragment = new HelpFragment();
            loadFragment(fragment);
        });
        cardView_logOut.setOnClickListener(view16 -> {
            Fragment fragment = new LogOutFragment();
            loadFragment(fragment);
        });

        return view;
    }

    public void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }
}