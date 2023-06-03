package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.advertisement.AdvertisementFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts.MyPosts_Adapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts._MyPosts;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.ArrayList;

import Alicisindan.Listing;

public class OtherProfilePostsFragment extends Fragment {
    private static String ARG_USER_ID = "userId";
    private static String ARG_USERNAME = "username";
    private String userID;
    private String username;
    public OtherProfilePostsFragment()
    {

    }

    public static OtherProfilePostsFragment newInstance(String userID, String username) {
        OtherProfilePostsFragment fragment = new OtherProfilePostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userID);
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private MyPosts_Adapter myposts_adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getString(ARG_USER_ID);
            username = getArguments().getString(ARG_USERNAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile_posts, container, false);
        _MyPosts.local_save = new LocalSave(this.getActivity());

        recyclerView = view.findViewById(R.id.otherProfilePostsFragment_recycleview);
        ArrayList<_MyPosts> user_posts = new ArrayList<>();
        String[][] listings;
        try {
            listings = Listing.findListingShowcases(userID, null, null, null, null, null, null, null, null, null, null, "25");
            for (int i = 0; i < listings.length; i++) {
                try {
                    user_posts.add(new _MyPosts(listings[i][4], listings[i][5], getBitmapFromEncodedString(listings[i][3]), listings[i][2], listings[i][4], listings[i][6]));
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        myposts_adapter = new MyPosts_Adapter(user_posts, this.getActivity(), false);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myposts_adapter);
        TextView topText = view.findViewById(R.id.otherProfilePostsFragment_topText);
        topText.setText(username + "\'s Posts");

        myposts_adapter.setOnClickListener(new MyPosts_Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(_MyPosts _myposts, int position) {
                Fragment fragment = AdvertisementFragment.newInstance(user_posts.get(position).getPostID());

                Bundle args = new Bundle();
                args.putString(Constants.KEY_ADVERTISEMENT_ID, user_posts.get(position).getPostID());
                args.putString(Constants.KEY_ADVERTISEMENT_USERNAME, username);
                fragment.setArguments(args);
                loadFragment(fragment);

                Log.d(Integer.toString(position), "MyPost clicked");
            }
        });

        return view;
    }

    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private static Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }
}
