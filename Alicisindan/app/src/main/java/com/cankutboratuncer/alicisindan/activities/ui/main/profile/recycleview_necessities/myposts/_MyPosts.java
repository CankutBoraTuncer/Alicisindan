package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Base64;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.ArrayList;

import Alicisindan.Listing;
import Alicisindan.User;

public class _MyPosts {
    private String order;
    private String name;
    private String intent;
    private String price;
    private Bitmap bitmap;
    private String post_id;

    public static LocalSave local_save;

    public _MyPosts(String order, String name, Bitmap bitmap, String id, String intent, String price) {
        this.order = order;
        this.name = name;
        this.intent = intent;
        this.price = price;
        this.bitmap = bitmap;
        this.post_id = id;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getOrder() {return this.order;}
    public String getName() {return this.name;}
    public Bitmap getBitmap() {return this.bitmap;}
    public String getPostID() {return this.post_id;}

    public void setOrder(String order) {this.order = order;}
    public void setName(String name) {this.name = name;}
    public void setImage(Bitmap bitmap) {this.bitmap = bitmap;}

    private static Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }

    /**
     * This method collects the user's data from the database and accordingly returns a self-posts ArrayList.
     * It is written for the purpose of being used inside the recycleView of MyPostsFragment.
     * @return user's posts as an ArrayList
     */
    public static ArrayList<_MyPosts> manageData() {
        ArrayList<_MyPosts> user_posts = new ArrayList<>();
        //Listing[] listings = null;
        String[][] listings = null;
        String id = local_save.getString(Constants.KEY_USER_ID);

        try {
            //listings = Listing.findListings(id, null, null, null, null, null, null, null, null, null, null, null);
            listings = Listing.findListingShowcases(id, null, null, null, null, null, null, null, null, null, null, null);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < listings.length; i++) {
            try {
                //Bitmap bitmap = getBitmapFromEncodedString(listings[i].getListingsFirstImage());
                //user_posts.add(new _MyPosts(listings[i].getType(), listings[i].getTitle(), bitmap));
                user_posts.add(new _MyPosts(listings[i][4], listings[i][5], getBitmapFromEncodedString(listings[i][3]), listings[i][2], listings[i][4], listings[i][6]));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        return user_posts;
    }
}
