package com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.favorites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.recycleview_necessities.myposts._MyPosts;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;

import java.util.ArrayList;

import Alicisindan.Listing;
import Alicisindan.User;

public class _Favorites {
    private String order;
    private String user;
    private String title;
    private String decsription;
    private Bitmap bitmap;
    private int favorite;
    private String adID;
    private String price;
    private static int item_count = 0;

    public static LocalSave local_save;

    public _Favorites(String order, String user, String description, Bitmap bitmap, int favorite, String adID, String price) {
        this.order = order;
        this.user = user;
        this.title = user + " wants to " + order.toLowerCase();
        this.decsription = description;
        this.bitmap = bitmap;
        this.favorite = favorite;
        this.adID = adID;
        this.price = price;
    }

    public String getOrder() {return this.order;}
    public String getUser() {return this.user;}
    public String getTitle() {return this.title;}
    public String getDescription() {return this.decsription;}
    public Bitmap getBitmap() {return this.bitmap;}
    public String getAdID(){return this.adID;}
    public int getFavorite() {return this.favorite;}
    public String getPrice() {return this.price;}
    public static int getItemCount() {return item_count;}

    public void setOrder(String order) {this.order = order;}
    public void setUser(String user) {this.user = user;}
    public void setName(String title) {this.title = title;}
    public void setDescription(String description) {this.decsription = description;}
    public void setBitmap(Bitmap bitmap) {this.bitmap = bitmap;}
    public void setFavorite(int favorite) {this.favorite = favorite;}

    private static Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }

    /**
     * This method collects the user's data from the database and accordingly returns a favorites ArrayList.
     * It is written for the purpose of being used inside the recycleView of FavoritesFragment.
     * @return user's favorite products as an ArrayList
     */
    static public ArrayList<_Favorites> manageData() {
        ArrayList<_Favorites> favorites = new ArrayList<>();
        String[] listingIDs = null;
        String[][] listingShowcases = null;
        //ArrayList<Listing> listings = new ArrayList<>();

        String id = local_save.getString(Constants.KEY_USER_ID);
        User user;
        //User owner = null;

        try {
            user = User.getUser(id);
            listingIDs = user.getFavorites();
            Log.d("ListingShowcases", "Started getting showcases");
            listingShowcases = Listing.getListingShowcases(listingIDs);
            Log.d("ListingShowcases", "Completed getting showcases");

            for (String[] listingShowcase : listingShowcases) {
                if (listingShowcase[2] == null) {
                    user.removeFavorite(local_save.getString(Constants.KEY_PASSWORD), listingShowcase[2]);
                }
                else {
                    favorites.add(new _Favorites(listingShowcase[4], listingShowcase[1], listingShowcase[5], getBitmapFromEncodedString(listingShowcase[3]), R.drawable.ic_star_full, listingShowcase[2], listingShowcase[6]));
                    Log.d(listingShowcase[5], "Listing added to " + user.getUsername() + "'s favorites fragment");
                }
            }

            /*
            for (int i = 0; i < listingIDs.length; i++) {
                //listings.add(Listing.getListing(listingIDs[i]));
                //owner = User.getUser(listings.get(i).getOwnerID());
                //Bitmap bitmap = getBitmapFromEncodedString(listings.get(i).getListingsFirstImage());

                //favorites.add(new _Favorites(listings.get(i).getType(), owner.getName(), listings.get(i).getDescription(), bitmap, R.drawable.ic_star_full));

                String[] listing = Listing.getListingShowcase(listingIDs[i]);
                favorites.add(new _Favorites(listing[4], listing[1], listing[5], getBitmapFromEncodedString(listing[3]), R.drawable.ic_star_full));
                Log.d(listing[5], "Listing added to " + user.getUsername() + "'s favorites fragment");
            } */

            item_count = favorites.size();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return  favorites;
    }
}
