package com.cankutboratuncer.alicisindan.activities.ui.main;


import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.BaseActivity;
import com.cankutboratuncer.alicisindan.activities.ui.main.forum.forum.ForumFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.BuyFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.HomeFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.home.pages.SellFragment;
import com.cankutboratuncer.alicisindan.activities.ui.main.profile.ProfileFragment;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.LocalSave;
import com.cankutboratuncer.alicisindan.activities.utilities.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    public static String type;
    public static String category;
    public static String condition;
    public static String location;
    public static String price;
    public static boolean comingFromPostAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.mainActivity_bottomNavigationBar_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Fragment fragment;
        if (comingFromPostAdd) {
            fragment = HomeFragment.newInstance(type, category, condition, location, price);
        } else {
            fragment = new HomeFragment();
        }
        requestNotificationPermission();
        loadFragment(fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.menu_buy:
                fragment = new BuyFragment();
                break;
            case R.id.menu_sell:
                fragment = new SellFragment();
                break;
            case R.id.menu_forum:
                fragment = new ForumFragment();
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }

    void loadFragment(Fragment fragment) {
        MainActivity.type = null;
        MainActivity.category = null;
        MainActivity.condition = null;
        MainActivity.location = null;
        MainActivity.price = null;
        MainActivity.comingFromPostAdd = false;
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_frameLayout_main, fragment).addToBackStack(null).commit();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            LocalSave localSave = new LocalSave(getApplicationContext());
            // Check if the notification permission is already granted
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            boolean isPermissionGranted = notificationManager.areNotificationsEnabled();

            if (!isPermissionGranted && (localSave.getBoolean(Constants.KEY_IS_NOTIFICATION_NOTIFIED) == null || !localSave.getBoolean(Constants.KEY_IS_NOTIFICATION_NOTIFIED))) {
                Util.showToast("Please enable notifications", getApplicationContext());
                localSave.putBoolean(Constants.KEY_IS_NOTIFICATION_NOTIFIED, true);
                // If permission is not granted, request it from the user
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
            }
        }
    }

}
