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
    private Bitmap bitmap;

    public static LocalSave local_save;

    public _MyPosts(String order, String name, Bitmap bitmap) {
        this.order = order;
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getOrder() {return this.order;}
    public String getName() {return this.name;}
    public Bitmap getBitmap() {return this.bitmap;}

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
        Listing[] listings = null;

        String id = local_save.getString(Constants.KEY_USER_ID);
        User user = null;

        try {
            user = User.getUser(id);

            listings = Listing.findListings(user.getID(), null, null, null, null, null, null, null, null, null, null, null);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < listings.length; i++) {
            try {
                Bitmap bitmap = getBitmapFromEncodedString(listings[i].getListingsFirstImage());
                user_posts.add(new _MyPosts(listings[i].getType(), listings[i].getTitle(), bitmap));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        return user_posts;
    }
}
