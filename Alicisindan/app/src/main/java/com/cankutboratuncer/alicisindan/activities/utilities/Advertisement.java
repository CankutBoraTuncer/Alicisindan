package com.cankutboratuncer.alicisindan.activities.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Advertisement {
    String title, description, price, previewImage, advertisementID, location, userID, username, brand, type, category, condition;
    String[] images;

    public Advertisement(String title, String description, String[] images, String price, String advertisementID, String location, String userID, String username, String brand, String type, String category, String condition) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = images;
        if (images != null) {
            this.previewImage = images[0];
        } else {
            this.previewImage = null;
        }
        this.advertisementID = advertisementID;
        this.location = location;
        this.userID = userID;
        this.username = username;
        this.brand = brand;
        this.type = type;
        this.category = category;
        this.condition = condition;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTitle() {
        return title;
    }

    public String getBrand() {
        return brand;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

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

    public String[] getImages() {
        return images;
    }

    public String getLocation() {
        return location;
    }

    public void setImages(String[] images) {
        this.images = images;
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