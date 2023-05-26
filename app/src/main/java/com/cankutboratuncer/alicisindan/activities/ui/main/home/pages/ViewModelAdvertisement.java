package com.cankutboratuncer.alicisindan.activities.ui.main.home.pages;

import androidx.lifecycle.ViewModel;

import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;

import java.util.ArrayList;

public class ViewModelAdvertisement extends ViewModel {

    private ArrayList<Advertisement> advertisements_home;
    private ArrayList<Advertisement> advertisements_buy;
    private ArrayList<Advertisement> advertisements_sell;

    public ArrayList<Advertisement> getAdvertisements_home() {
        return advertisements_home;
    }

    public void setAdvertisements_home(ArrayList<Advertisement> advertisements_home) {
        this.advertisements_home = advertisements_home;
    }

    public ArrayList<Advertisement> getAdvertisements_buy() {
        return advertisements_buy;
    }

    public void setAdvertisements_buy(ArrayList<Advertisement> advertisements_buy) {
        this.advertisements_buy = advertisements_buy;
    }

    public ArrayList<Advertisement> getAdvertisements_sell() {
        return advertisements_sell;
    }

    public void setAdvertisements_sell(ArrayList<Advertisement> advertisements_sell) {
        this.advertisements_sell = advertisements_sell;
    }
}
