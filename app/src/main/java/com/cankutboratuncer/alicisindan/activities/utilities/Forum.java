package com.cankutboratuncer.alicisindan.activities.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.Date;
import java.util.UUID;

public class Forum {

    String forumID, forumOwnerID, forumOwnerName, forumTitle,forumDescription , forumImage;

    public Date timeStamp;
    public Forum() {

    }
    public Forum(String forumID, String forumOwnerID, String forumOwnerName, String forumTitle, String forumDescription, String forumImage, Date timeStamp) {
        this.forumID = forumID;
        this.forumOwnerID = forumOwnerID;
        this.forumOwnerName = forumOwnerName;
        this.forumTitle = forumTitle;
        this.forumDescription = forumDescription;
        this.forumImage = forumImage;
        this.timeStamp = timeStamp;
    }

    public String getForumID() {
        return forumID;
    }

    public void setForumID(String forumID) {
        this.forumID = forumID;
    }

    public String getForumOwnerID() {
        return forumOwnerID;
    }

    public void setForumOwnerID(String forumOwnerID) {
        this.forumOwnerID = forumOwnerID;
    }

    public String getForumOwnerName() {
        return forumOwnerName;
    }

    public void setForumOwnerName(String forumOwnerName) {
        this.forumOwnerName = forumOwnerName;
    }

    public String getForumTitle() {
        return forumTitle;
    }

    public void setForumTitle(String forumTitle) {
        this.forumTitle = forumTitle;
    }

    public String getForumDescription() {
        return forumDescription;
    }

    public void setForumDescription(String forumDescription) {
        this.forumDescription = forumDescription;
    }

    public String getForumImage() {
        return forumImage;
    }

    public void setForumImage(String forumImage) {
        this.forumImage = forumImage;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
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
