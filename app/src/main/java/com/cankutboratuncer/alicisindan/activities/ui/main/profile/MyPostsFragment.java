package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts.MyPosts_Adapter;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts._MyPosts;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;


public class MyPostsFragment extends Fragment {



    public MyPostsFragment() {
        // Required empty public constructor
    }


    public static MyPostsFragment newInstance() {
        MyPostsFragment fragment = new MyPostsFragment();
        return fragment;
    }

    private RecyclerView recyclerView;
    private MyPosts_Adapter myposts_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_posts, container, false);

        // get local save
        _MyPosts.local_save = new LocalSave(this.getActivity());

        View view = inflater.inflate(R.layout.fragment_my_posts, container, false);

        recyclerView = view.findViewById(R.id.mypostsFragment_recycleview);
        myposts_adapter = new MyPosts_Adapter(_MyPosts.manageData(), this.getActivity());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myposts_adapter);

        return view;
    }
}