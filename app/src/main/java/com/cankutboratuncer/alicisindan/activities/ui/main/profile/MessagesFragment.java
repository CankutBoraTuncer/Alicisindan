package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.activities.ChatActivity;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.adapters.RecentConversationAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.listeners.ConversionListener;
import com.cankutboratuncer.alicisindan.activities.utilities.ChatMessage;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessagesFragment extends Fragment implements ConversionListener {

    private LocalSave localSave;
    private List<ChatMessage> conversations;
    private RecyclerView recyclerViewForMessages;
    private RecentConversationAdapter conversationsAdapter;
    private FirebaseFirestore database;
    private View view;

    public MessagesFragment() {
        // Required empty public constructor
    }


    public static MessagesFragment newInstance() {
        MessagesFragment fragment = new MessagesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        localSave = new LocalSave(getContext());
        init();
        getToken();
        listenConversations();
        return view;
    }

    private void init() {
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationAdapter(conversations, this);
        recyclerViewForMessages = view.findViewById(R.id.recyclerViewMessages);
        recyclerViewForMessages.setAdapter(conversationsAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void listenConversations() {
        database.collection(Constants.KEY_COLLECTION_ADVERTISEMENTS)
                .whereEqualTo(Constants.KEY_SENDER_ID, localSave.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_ADVERTISEMENTS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, localSave.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if (localSave.getString(Constants.KEY_USER_ID).equals(senderId)) {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    } else {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);

                    chatMessage.productTitle  = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_TITLE);
                    chatMessage.productDescription = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_DESCRIPTION);
                    chatMessage.userId = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_USERID);
                    chatMessage.userName = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_USERNAME);
                    chatMessage.productId = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_ID);
                    chatMessage.location = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_LOCATION);
                    chatMessage.price = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_PRICE);
                    chatMessage.image = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_IMAGE);
                    chatMessage.brand = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_BRAND);
                    chatMessage.type = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_TYPE);
                    conversations.add(chatMessage);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < conversations.size(); i++) {
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        String productId = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_ID);
                        if (conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId) && conversations.get(i).productId.equals(productId)) {
                            conversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            recyclerViewForMessages.smoothScrollToPosition(0);
            recyclerViewForMessages.setVisibility(View.VISIBLE);
//            binding.progressBar.setVisibility(View.GONE);
        }
    };

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        localSave.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        localSave.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> Log.d("Test","Unable to update token"));
    }

    @Override
    public void onConversionClicked(ChatMessage chatMessage) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_ADVERTISEMENT_TITLE, chatMessage.getProductTitle());
        args.putString(Constants.KEY_ADVERTISEMENT_USERID, chatMessage.getUserId());
        args.putString(Constants.KEY_ADVERTISEMENT_USERNAME, chatMessage.getUserName());
        args.putString(Constants.KEY_ADVERTISEMENT_ID, chatMessage.getProductId());
        args.putString(Constants.KEY_ADVERTISEMENT_LOCATION, chatMessage.getLocation());
        args.putString(Constants.KEY_ADVERTISEMENT_PRICE, chatMessage.getPrice());
        args.putString(Constants.KEY_ADVERTISEMENT_IMAGE, chatMessage.getImage());
        args.putString(Constants.KEY_ADVERTISEMENT_IMAGE, chatMessage.getImage());
        args.putString(Constants.KEY_ADVERTISEMENT_TYPE, chatMessage.getType());
        args.putString(Constants.KEY_RECEIVER_ID, chatMessage.conversionId);
        args.putString(Constants.KEY_SENDER_ID, localSave.getString(Constants.KEY_USER_ID));
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }
}