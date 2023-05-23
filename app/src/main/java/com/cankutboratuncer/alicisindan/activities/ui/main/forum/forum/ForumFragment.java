package com.cankutboratuncer.alicisindan.activities.ui.main.forum.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.data.database.ForumTest;
import com.cankutboratuncer.alicisindan.activities.ui.main.advertisement.category.PostAddCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.forum.category.ForumAddCategoryActivity;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.activities.ChatActivity;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.adapters.RecentConversationAdapter;
import com.cankutboratuncer.alicisindan.activities.utilities.ChatMessage;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.Forum;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ForumFragment extends Fragment implements ForumListener {

    private LocalSave localSave;
    private List<Forum> forumPosts;
    private RecyclerView recyclerViewForForums;
    private RecentForumPostsAdapter forumsAdapter;
    private FirebaseFirestore database;
    private View view;


    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance() {
        ForumFragment fragment = new ForumFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum, container, false);
        localSave = new LocalSave(getContext());
        init();
        initListeners();
        listenForumPosts();
        return view;
    }

    private void initListeners() {
        view.findViewById(R.id.buttonCreateForumPost).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ForumAddCategoryActivity.class));
        });
    }

    private void init() {
        forumPosts = new ArrayList<>();
        forumsAdapter = new RecentForumPostsAdapter(forumPosts, this);
        recyclerViewForForums = view.findViewById(R.id.recyclerViewForum);
        recyclerViewForForums.setAdapter(forumsAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void listenForumPosts() {
        database.collection(Constants.KEY_COLLECTION_FORUM_POSTS).addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    Forum forum = new Forum();
                    forum.setForumID(documentChange.getDocument().getString(Constants.KEY_FORUM_ID));
                    forum.setForumOwnerID(documentChange.getDocument().getString(Constants.KEY_FORUM_OWNER_ID));
                    forum.setForumTitle(documentChange.getDocument().getString(Constants.KEY_FORUM_TITLE));
                    forum.setForumOwnerName(documentChange.getDocument().getString(Constants.KEY_FORUM_OWNER_NAME));
                    forum.setForumImage(documentChange.getDocument().getString(Constants.KEY_FORUM_IMAGE));
                    forum.setForumDescription(documentChange.getDocument().getString(Constants.KEY_FORUM_DESCRIPTION));
                    forum.setTimeStamp(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    forumPosts.add(forum);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < forumPosts.size(); i++) {
                        String forumID = documentChange.getDocument().getString(Constants.KEY_FORUM_ID);
                        if (forumPosts.get(i).getForumID().equals(forumID)) {
                            forumPosts.get(i).setTimeStamp(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                            break;
                        }
                    }
                }
            }
            Collections.sort(forumPosts, (obj1, obj2) -> obj2.getTimeStamp().compareTo(obj1.getTimeStamp()));
            forumsAdapter.notifyDataSetChanged();
            recyclerViewForForums.smoothScrollToPosition(0);
            recyclerViewForForums.setVisibility(View.VISIBLE);
//            binding.progressBar.setVisibility(View.GONE);
        }
    };

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getParentFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onForumPostClicked(Forum forum) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_FORUM_ID, forum.getForumID());
        args.putString(Constants.KEY_FORUM_OWNER_ID, forum.getForumOwnerID());
        args.putString(Constants.KEY_FORUM_OWNER_NAME, forum.getForumOwnerName());
        args.putString(Constants.KEY_FORUM_TITLE, forum.getForumTitle());
        args.putString(Constants.KEY_FORUM_DESCRIPTION, forum.getForumDescription());
        args.putString(Constants.KEY_FORUM_IMAGE, forum.getForumImage());
        args.putString(Constants.KEY_TIMESTAMP, forum.getTimeStamp().toString());

        Intent intent = new Intent(getActivity(), ForumChatActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }
}