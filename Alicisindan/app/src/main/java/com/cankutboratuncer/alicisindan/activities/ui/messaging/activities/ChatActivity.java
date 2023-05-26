package com.cankutboratuncer.alicisindan.activities.ui.messaging.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.BaseActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.OtherProfileFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.OtherProfileFragmentActivity;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.adapters.ChatAdapter;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.network.ApiClient;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.network.ApiService;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.ChatMessage;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import Alicisindan.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
    private ChatMessage chatMessage;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private LocalSave localSave;
    private FirebaseFirestore database;
    private Advertisement advertisement;
    private String conversionId = null;

    private Boolean isReceiverAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        init();
        loadReceiverDetails();
        listenMessages();
    }

    private void init() {
        localSave = new LocalSave(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, "getBitmapFromEncodedString(advertisement.image)", localSave.getString(Constants.KEY_USER_ID));
        binding.chatRecyclerView.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void sendMessage() {
        if (binding.messageInputField.getText() != null && binding.messageInputField.getText().toString().length() != 0) {
            String userMessage = binding.messageInputField.getText().toString().trim();
            HashMap<String, Object> message = new HashMap<>();
            message.put(Constants.KEY_SENDER_ID, localSave.getString(Constants.KEY_USER_ID));
            message.put(Constants.KEY_RECEIVER_ID, chatMessage.receiverId);
            message.put(Constants.KEY_MESSAGE, userMessage);
            message.put(Constants.KEY_ADVERTISEMENT_ID, chatMessage.getProductId());
            message.put(Constants.KEY_TIMESTAMP, new Date());
            database.collection(Constants.KEY_COLLECTION_ADVERTISEMENT_CHAT).add(message);
            if (conversionId != null) {
                updateConversion(userMessage);
            } else {
                HashMap<String, Object> conversion = new HashMap<>();
                conversion.put(Constants.KEY_SENDER_ID, localSave.getString(Constants.KEY_USER_ID));
                conversion.put(Constants.KEY_SENDER_NAME, localSave.getString(Constants.KEY_USER_NAME));
                conversion.put(Constants.KEY_RECEIVER_ID, chatMessage.receiverId);
                conversion.put(Constants.KEY_RECEIVER_NAME, chatMessage.getUserName());

                conversion.put(Constants.KEY_ADVERTISEMENT_TITLE, chatMessage.getProductTitle());
                conversion.put(Constants.KEY_ADVERTISEMENT_DESCRIPTION, chatMessage.getProductDescription());
                conversion.put(Constants.KEY_ADVERTISEMENT_ID, chatMessage.getProductId());
                conversion.put(Constants.KEY_ADVERTISEMENT_LOCATION, chatMessage.getLocation());
                conversion.put(Constants.KEY_ADVERTISEMENT_USERNAME, chatMessage.getUserName());
                conversion.put(Constants.KEY_ADVERTISEMENT_USERID, chatMessage.getUserId());
                conversion.put(Constants.KEY_ADVERTISEMENT_PRICE, chatMessage.getPrice());
                conversion.put(Constants.KEY_ADVERTISEMENT_IMAGE, chatMessage.getImage());
                conversion.put(Constants.KEY_ADVERTISEMENT_BRAND, chatMessage.getBrand());
                conversion.put(Constants.KEY_ADVERTISEMENT_TYPE, chatMessage.getType());
                conversion.put(Constants.KEY_ADVERTISEMENT_CATEGORY, chatMessage.getCategory());
                conversion.put(Constants.KEY_ADVERTISEMENT_CONDITION, chatMessage.getCondition());

//            conversion.put(Constants.KEY_SENDER_IMAGE, localSave.getString(Constants.KEY_IMAGE));
//            conversion.put(Constants.KEY_RECEIVER_IMAGE, advertisement.image);
                conversion.put(Constants.KEY_LAST_MESSAGE, userMessage);
                conversion.put(Constants.KEY_TIMESTAMP, new Date());
                addConversion(conversion);
            }
            sendNotification(userMessage);
            binding.messageInputField.setText(null);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void sendNotification(String userMessage) {
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(User.getUserToken(chatMessage.getReceiverId()));

            JSONObject data = new JSONObject();
            data.put(Constants.KEY_USER_ID, localSave.getString(Constants.KEY_USER_ID));
            data.put(Constants.KEY_USER_NAME, localSave.getString(Constants.KEY_USER_NAME));
            data.put(Constants.KEY_FCM_TOKEN, localSave.getString(Constants.KEY_FCM_TOKEN));
            data.put(Constants.KEY_MESSAGE, userMessage);

            JSONObject body = new JSONObject();
            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);
            String messageBody = body.toString();

            ApiClient.getClient().create(ApiService.class).sendMessage(Constants.getRemoteMsgHeaders(), messageBody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            if (response.body() != null) {
                                JSONObject responseJson = new JSONObject(response.body());
                                JSONArray results = responseJson.getJSONArray("results");
                                if (responseJson.getInt("failure") == 1) {
                                    JSONObject error = (JSONObject) results.get(0);
                                    showToast(error.getString("error"));
                                    Log.d("Errrr", responseJson.toString());
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToast("Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    showToast(t.getMessage());
                }
            });
        } catch (Exception exception) {
            showToast(exception.getMessage());
        }
    }


//    private void listenAvailabilityOfReceiver() {
//        database.collection(Constants.KEY_COLLECTION_USERS).document(receiverUser.id).addSnapshotListener(ChatActivity.this, (value, error) -> {
//            if (error != null) {
//                return;
//            }
//            if (value != null) {
//                if (value.getLong(Constants.KEY_AVAILABILITY) != null) {
//                    int availability = Objects.requireNonNull(value.getLong(Constants.KEY_AVAILABILITY)).intValue();
//                    isReceiverAvailable = availability == 1;
//                }
//                receiverUser.token = value.getString(Constants.KEY_FCM_TOKEN);
//                if (receiverUser.image == null) {
//                    receiverUser.image = value.getString(Constants.KEY_IMAGE);
//                    chatAdapter.setReceiverProfileImage(getBitmapFromEncodedString(receiverUser.image));
//                    chatAdapter.notifyItemRangeChanged(0, chatMessages.size());
//                }
//            }
//        });
//    }

    private void listenMessages() {
        database.collection(Constants.KEY_COLLECTION_ADVERTISEMENT_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, localSave.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, chatMessage.receiverId)
                .whereEqualTo(Constants.KEY_ADVERTISEMENT_ID, chatMessage.getProductId())
                .addSnapshotListener(eventListener);

        database.collection(Constants.KEY_COLLECTION_ADVERTISEMENT_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, chatMessage.receiverId)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, localSave.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_ADVERTISEMENT_ID, chatMessage.getProductId())
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessage.productId = documentChange.getDocument().getString(Constants.KEY_ADVERTISEMENT_ID);
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }
            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
        if (conversionId == null) {
            checkForConversion();
        }
    };

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }

    private void loadReceiverDetails() {

        loadAdvertisementData();
        binding.productCardTitle.setText(chatMessage.getProductTitle());
        binding.productCardPrice.setText(chatMessage.getPrice());
        binding.productCardLocation.setText(chatMessage.getLocation());
        binding.productCardName.setText(chatMessage.getUserName());
        binding.productCardImage.setImageBitmap(Advertisement.decodeImage(chatMessage.getImage()));
    }

    private void loadAdvertisementData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString(Constants.KEY_ADVERTISEMENT_TITLE);
            String description = bundle.getString(Constants.KEY_ADVERTISEMENT_TITLE);
            String userId = bundle.getString(Constants.KEY_ADVERTISEMENT_USERID);
            String userName = bundle.getString(Constants.KEY_ADVERTISEMENT_USERNAME);
            String id = bundle.getString(Constants.KEY_ADVERTISEMENT_ID);
            String location = bundle.getString(Constants.KEY_ADVERTISEMENT_LOCATION);
            String price = bundle.getString(Constants.KEY_ADVERTISEMENT_PRICE);
            String[] image = new String[]{bundle.getString(Constants.KEY_ADVERTISEMENT_IMAGE)};
            String brand = bundle.getString(Constants.KEY_ADVERTISEMENT_LOCATION);
            String type = bundle.getString(Constants.KEY_ADVERTISEMENT_TYPE);
            String category = bundle.getString(Constants.KEY_ADVERTISEMENT_CATEGORY);
            String condition = bundle.getString(Constants.KEY_ADVERTISEMENT_CONDITION);
            String senderId = bundle.getString(Constants.KEY_SENDER_ID);
            String receiverId = bundle.getString(Constants.KEY_RECEIVER_ID);

            advertisement = new Advertisement(title, description, image, price, id, location, userId, userName, brand, type, category, condition);
            chatMessage = new ChatMessage();
            chatMessage.senderId = senderId;
            chatMessage.receiverId = receiverId;
            chatMessage.loadAdvertisement(advertisement);
        }
    }

    private void setListeners() {
        //binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.messageSendButton.setOnClickListener(v -> sendMessage());
        binding.userCardName.setOnClickListener(v2 -> {
            Bundle b = new Bundle();
            b.putString("userId", chatMessage.getUserId());
            Intent i = new Intent(ChatActivity.this, OtherProfileFragmentActivity.class);
            i.putExtras(b);
            startActivity(i);
        });
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh: mm a", Locale.getDefault()).format(date);
    }

    private void addConversion(HashMap<String, Object> conversion) {
        database.collection(Constants.KEY_COLLECTION_ADVERTISEMENTS).add(conversion).
                addOnSuccessListener(documentReference -> conversionId = documentReference.getId());
    }

    private void updateConversion(String message) {
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_ADVERTISEMENTS).document(conversionId);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message, Constants.KEY_TIMESTAMP, new Date());
    }

    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(localSave.getString(Constants.KEY_USER_ID), chatMessage.receiverId, chatMessage.getProductId());
            checkForConversionRemotely(chatMessage.receiverId, localSave.getString(Constants.KEY_USER_ID), chatMessage.getProductId());
        }
    }

    private void checkForConversionRemotely(String senderId, String receiverId, String productId) {
        database.collection(Constants.KEY_COLLECTION_ADVERTISEMENTS).
                whereEqualTo(Constants.KEY_SENDER_ID, senderId).
                whereEqualTo(Constants.KEY_RECEIVER_ID, receiverId).
                whereEqualTo(Constants.KEY_ADVERTISEMENT_ID, productId).
                get().addOnCompleteListener(conversionOnCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionOnCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };


}