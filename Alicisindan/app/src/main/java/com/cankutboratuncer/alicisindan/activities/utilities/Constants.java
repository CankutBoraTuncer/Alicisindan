package com.cankutboratuncer.alicisindan.activities.utilities;
import android.util.Log;

import com.cankutboratuncer.alicisindan.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_COLLECTION_ADVERTISEMENTS = "advertisements";
    public static final String KEY_COLLECTION_ADVERTISEMENT_CHAT = "advertisement_chat";
    public static final String KEY_COLLECTION_FORUM_POSTS = "forumPosts";
    public static final String KEY_COLLECTION_FORUM_CHAT = "forum_chat";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_SURNAME = "userSurname";
    public static final String KEY_USER_USERNAME = "userUsername";
    public static final String KEY_USER_ADDRESS = "userAddress";
    public static final String KEY_USER_PHONE = "userPhone";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_IMAGE = "userImage";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_IS_USER_SKIP = "isSkip";
    public static final String KEY_IS_NOTIFICATION_NOTIFIED = "isNotified";
    public static final String KEY_ADVERTISEMENT = "advertisement";
    public static final String KEY_ADVERTISEMENT_TITLE = "advertisement_title";
    public static final String KEY_ADVERTISEMENT_USERID = "advertisement_userid";
    public static final String KEY_ADVERTISEMENT_USERNAME = "advertisement_username";
    public static final String KEY_ADVERTISEMENT_ID = "advertisement_id";
    public static final String KEY_ADVERTISEMENT_LOCATION = "advertisement_location";
    public static final String KEY_ADVERTISEMENT_PRICE = "advertisement_price";
    public static final String KEY_ADVERTISEMENT_PREVIEW_IMAGE = "advertisement_preview_image";
    public static final String KEY_ADVERTISEMENT_IMAGE = "advertisement_image";
    public static final String KEY_ADVERTISEMENT_DESCRIPTION = "advertisement_description";
    public static final String KEY_ADVERTISEMENT_BRAND = "advertisement_brand";
    public static final String KEY_ADVERTISEMENT_TYPE = "advertisement_type";
    public static final String KEY_ADVERTISEMENT_CATEGORY= "advertisement_category";
    public static final String KEY_ADVERTISEMENT_CONDITION = "advertisement_condition";

    public static final String KEY_ADVERTISEMENT_TOKEN = "advertisement_token";

    public static final String KEY_FORUM_ID = "forum_id";
    public static final String KEY_FORUM_OWNER_ID = "forum_owner_id";
    public static final String KEY_FORUM_OWNER_NAME = "forum_owner_name";
    public static final String KEY_FORUM_TITLE = "forum_title";
    public static final String KEY_FORUM_DESCRIPTION = "forum_description";
    public static final String KEY_FORUM_IMAGE = "forum_image";

    public static final ArrayList<String> SORTING_METHODS = new ArrayList<>(Arrays.asList("NewestFirst", "OldestFirst", "CheapFirst", "ExpensiveFirst"));
    public static final ArrayList<String> CONDITION_FILTER = new ArrayList<>(Arrays.asList("Any", "Unused", "2nd Hand", "Worn out"));
    public static final ArrayList<String> CONDITION_POST = new ArrayList<>(Arrays.asList("Unused", "2nd Hand", "Worn out"));


    public static final String KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";

    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_SENDER_USERNAME = "senderUserName";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";

    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final ArrayList<String> carCategory = new ArrayList<>(Arrays.asList("Cars","Car Spare Parts and Accessories", "Car Audio and Video Systems", "Wheel and Tire", "Rental Vehicles"));
    public static final ArrayList<String> telephoneCategory = new ArrayList<>(Arrays.asList("Smart Phone", "Phone Accessories and Parts", "Other"));
    public static final ArrayList<String> houseApplianceCategory = new ArrayList<>(Arrays.asList("Furniture", "Kitchenware and Tableware", "Decoration", "Garden", "Hand Tools", "Other"));
    public static final ArrayList<String> electronicsCategory = new ArrayList<>(Arrays.asList("Desktop Computer", "Laptop", "Cameras", "Tablets", "Headphones", "TVs", "Smart Watches", "Gaming"));
    public static final ArrayList<String> motorcycleCategory = new ArrayList<>(Arrays.asList("Motorcycle", "Motorcycle Parts", "Helmet", "Accessories"));
    public static final ArrayList<String> otherVehiclesCategory = new ArrayList<>(Arrays.asList("Truck", "Minivan and Panelvan", "Caravan", "Other Vehicle Parts", "Other"));
    public static final ArrayList<String> babyChildrenCategory = new ArrayList<>(Arrays.asList("Hygiene", "Clothes", "Baby & Children Furniture"));
    public static final ArrayList<String> sportsOutdoorCategory = new ArrayList<>(Arrays.asList("Bicycle", "Exercise Equipment", "Sports equipment","Other"));
    public static final ArrayList<String> hobbiesCategory = new ArrayList<>(Arrays.asList("Movie and Music", "Musical Instruments", "Board and Board Games","Pet Products","Other"));
    public static final ArrayList<String> clothesCategory = new ArrayList<>(Arrays.asList("Tops", "Pants", "Jeans", "Dresses", "Coats", "Skirts", "Shoes", "Accessories"));
    public static final ArrayList<String> stationaryCategory = new ArrayList<>(Arrays.asList("Pen and Pencil", "Notebooks", "Erasers & Lead", "Other"));
    public static final ArrayList<String> booksCategory = new ArrayList<>(Arrays.asList("University books", "Preschool books", "Other"));

    public static final ArrayList<String> categoriesNames = new ArrayList<>(Arrays.asList("Cars","Telephone", "House Appliance", "Electronics", "Motorcycle", "Other Vehicles", "Baby & Children Care", "Sports and Outdoors", "Hobbies and Entertainment", "Clothes and Accessories", "Stationery", "Books and Literature"));
    public static final ArrayList<Integer> categoryImages = new ArrayList<>(Arrays.asList(R.drawable.img_car, R.drawable.img_phone, R.drawable.img_sofa, R.drawable.img_pc, R.drawable.img_motorcycle, R.drawable.img_truck, R.drawable.img_baby, R.drawable.img_sport, R.drawable.img_music, R.drawable.img_shoe, R.drawable.img_pen, R.drawable.img_book));
    public static final String KEY_IS_USER_READ_TOS = "read_tos?";

    public static ArrayList<AllCategories> categories = createCategories();
    // subcategories arraylist stores the subcategories for each category index
    public static ArrayList<AllCategories> createSubCategories(String category) {
        ArrayList<AllCategories> subcategoryArrayList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String categoryName = Constants.categories.get(i).getName();
            ArrayList<String> subcategories = Constants.findSubCategory(category);
            if (categoryName.equals(category)) {
                Log.d("catname", category + " " + categoryName + " " + i);
                for (int j = 0; j < Objects.requireNonNull(subcategories).size(); j++) {
                    subcategoryArrayList.add(new Subcategory(subcategories.get(j), (Category) categories.get(i), Constants.categoryImages.get(i)));
                }
                break;
            }
        }
        return subcategoryArrayList;
    }

    public static ArrayList<AllCategories> createCategories() {
        ArrayList<AllCategories> categoryArrayList = new ArrayList<>();
        for (int i = 0; i < categoriesNames.size(); i++) {
            Category category = new Category(categoriesNames.get(i), categoryImages.get(i));
            categoryArrayList.add(category);
        }
        return categoryArrayList;
    }
    public static ArrayList<String> findSubCategory(String category){
        if(category.equals(categoriesNames.get(0))){
            return carCategory;
        }
        else if (category.equals(categoriesNames.get(1))){
            return telephoneCategory;
        }
        else if (category.equals(categoriesNames.get(2))){
            return houseApplianceCategory;
        }
        else if (category.equals(categoriesNames.get(3))){
            return electronicsCategory;
        }
        else if (category.equals(categoriesNames.get(4))){
            return motorcycleCategory;
        }
        else if (category.equals(categoriesNames.get(5)))
        {
            return otherVehiclesCategory;
        }
        else if (category.equals(categoriesNames.get(6)))
        {
            return babyChildrenCategory;
        }
        else if (category.equals(categoriesNames.get(7)))
        {
            return sportsOutdoorCategory;
        }
        else if (category.equals(categoriesNames.get(8)))
        {
            return hobbiesCategory;
        }
        else if (category.equals(categoriesNames.get(9)))
        {
            return clothesCategory;
        }
        else if (category.equals(categoriesNames.get(10)))
        {
            return stationaryCategory;
        }
        else if (category.equals(categoriesNames.get(11)))
        {
            return booksCategory;
        }
        else {
            return null;
        }
    }

    public static HashMap<String, String> remoteMsgHeaders = null;

    public static HashMap<String, String> getRemoteMsgHeaders() {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAA-HtNnc8:APA91bHu6WOILREkyi9nv1ocQXuaYuTq1WN6rvM9EgyBuU2kX7d5Pk_1oy3bVxe8V9YtFsJvx78AesnipkPvB2lEMWOtPAcPTQ4mtTXwjrXu7OKYjcSf_ARZBF_d-H7eGehOnL2BUvgV"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }


}