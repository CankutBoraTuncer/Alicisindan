package com.cankutboratuncer.alicisindan.activities.utilities;

public class Advertisement {

    // User advertisementOwner;
    // Category advertisementCategory;
    String title, description, price, image, advertisementID;

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

    public Advertisement(String title, String description, String image, String price, String advertisementID) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.advertisementID = advertisementID;
    }

    public String getTitle() {
        return title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdvertisementID() {
        return advertisementID;
    }

    public void setAdvertisementID(String advertisementID) {
        this.advertisementID = advertisementID;
    }
}