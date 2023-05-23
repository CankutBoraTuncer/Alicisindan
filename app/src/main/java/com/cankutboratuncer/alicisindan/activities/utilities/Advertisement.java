package com.cankutboratuncer.alicisindan.activities.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Advertisement {

    // User advertisementOwner;
    // Category advertisementCategory;
    String title, description, price, image, advertisementID, location, userID, username, brand, type;

    /*  after completing other classes, use this constructor instead.
        also change advertisementTest class members according to the new design

    public Advertisement(User advertisementOwner, Category advertisementCategory, String title, String description, String price, int image, int advertisementID) {
        this.advertisementOwner = advertisementOwner;
        this.advertisementCategory = advertisementCategory;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.advertisementID = advertisementID;
    }
    */

    public Advertisement(String title, String description, String image, String price, String advertisementID, String location, String userID, String username, String brand, String type) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.advertisementID = advertisementID;
        this.location = location;
        this.userID = userID;
        this.username = username;
        this.brand = brand;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }
    public String getBrand(){ return brand;}
    public String getUsername() {
        return username;
    }
    public String getUserID(){ return userID;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }
    public String getLocation() { return location; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdvertisementID() {
        return advertisementID;
    }

    public void setAdvertisementID(String advertisementID) {
        this.advertisementID = advertisementID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Bitmap decodeImage(String encodedImage) {
        try {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}