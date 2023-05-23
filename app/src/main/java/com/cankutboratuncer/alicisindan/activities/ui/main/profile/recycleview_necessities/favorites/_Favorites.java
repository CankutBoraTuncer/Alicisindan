package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;

import java.util.ArrayList;

public class _Favorites {
    private String order;
    private String name;
    private int image;
    private int favorite;
    private static int item_count = 0;

    public _Favorites(String order, String name, int image, int favorite) {
        this.order = order;
        this.name = name;
        this.image = image;
        this.favorite = favorite;

        item_count++;
    }

    public String getOrder() {return this.name;}
    public String getName() {return this.name;}
    public int getImage() {return this.image;}
    public int getFavorite() {return this.favorite;}
    public static int getItemCount() {return item_count;}

    public void setOrder(String order) {this.order = order;}
    public void setName(String name) {this.name = name;}
    public void setImage(int image) {this.image = image;}
    public void setFavorite(int favorite) {this.favorite = favorite;}

    /**
     * This method collects the user's data from the database and accordingly returns a favorites ArrayList.
     * It is written for the purpose of being used inside the recycleView of FavoritesFragment.
     * @return user's favorite products as an ArrayList
     */
    static public ArrayList<_Favorites> manageData() {
        ArrayList<_Favorites> favorites = new ArrayList<>();

        // hard-coded segments will be changed later on with functionality
        favorites.add(new _Favorites("Buy", "Bertan", R.drawable.ic_launcher_background, R.drawable.ic_star_full));
        favorites.add(new _Favorites("Sell", "Bertan", R.drawable.ic_launcher_background, R.drawable.ic_star_full));
        favorites.add(new _Favorites("Buy", "Turgut", R.drawable.ic_launcher_background, R.drawable.ic_star_full));
        favorites.add(new _Favorites("Sell", "Turgut", R.drawable.ic_launcher_background, R.drawable.ic_star_full));
        favorites.add(new _Favorites("Sell", "Bertan Turgut", R.drawable.ic_launcher_background, R.drawable.ic_star_full));

        return  favorites;
    }
}
