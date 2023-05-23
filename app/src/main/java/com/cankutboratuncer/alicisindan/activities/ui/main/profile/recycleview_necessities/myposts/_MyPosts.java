package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts;

import android.graphics.drawable.Drawable;

import com.cankutboratuncer.alicisindan.R;

import java.util.ArrayList;

public class _MyPosts {
    private String order;
    private String name;
    private int image;

    public _MyPosts(String order, String name, int image) {
        this.order = order;
        this.name = name;
        this.image = image;
    }

    public String getOrder() {return this.order;}
    public String getName() {return this.name;}
    public int getImage() {return this.image;}

    public void setOrder(String order) {this.order = order;}
    public void setName(String name) {this.name = name;}
    public void setImage(int image) {this.image = image;}


    /**
     * This method collects the user's data from the database and accordingly returns a self-posts ArrayList.
     * It is written for the purpose of being used inside the recycleView of MyPostsFragment.
     * @return user's posts as an ArrayList
     */
    static public ArrayList<_MyPosts> manageData() {
        ArrayList<_MyPosts> user_posts = new ArrayList<>();

        // hard-coded segments will be changed later on with functionality
        user_posts.add(new _MyPosts("Sell", "Saodimallsu Womens Turtleneck", R.drawable.ic_launcher_background));
        user_posts.add(new _MyPosts("Sell", "Angashion Women's Sweaters Casual", R.drawable.ic_launcher_background));
        user_posts.add(new _MyPosts("Buy", "ECOWISH Womens Color Block\nStriped Draped K...", R.drawable.ic_launcher_background));
        user_posts.add(new _MyPosts("Buy", "Simplee Women's Oversized Lantern\nSlee...l", R.drawable.ic_launcher_background));
        user_posts.add(new _MyPosts("Sell", "Bertan Turgut", R.drawable.ic_launcher_background));
        user_posts.add(new _MyPosts("Buy", "Bertan Turgut", R.drawable.ic_launcher_background));

        return user_posts;
    }
}
