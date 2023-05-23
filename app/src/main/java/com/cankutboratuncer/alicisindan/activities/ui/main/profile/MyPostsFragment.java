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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPostsFragment newInstance(String param1, String param2) {
        MyPostsFragment fragment = new MyPostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private MyPosts_Adapter myposts_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_posts, container, false);

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