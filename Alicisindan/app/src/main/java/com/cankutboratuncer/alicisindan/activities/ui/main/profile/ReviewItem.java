package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import Alicisindan.AlicisindanException;

public class ReviewItem {
    private int ratingInt;
    private String text;
    private String username;

    public ReviewItem(int ratingInt, String text, String userID)
    {
        this.ratingInt = ratingInt;
        try {
            this.username = Alicisindan.User.getUser(userID).getUsername();
        } catch (AlicisindanException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.text = text;
    }

    public String getUsername()
    {
        return username;
    }

    public int getRating()
    {
        return this.ratingInt;
    }

    public String getText()
    {
        return text;
    }

}