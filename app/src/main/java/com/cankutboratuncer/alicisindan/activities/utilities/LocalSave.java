package com.cankutboratuncer.alicisindan.activities.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSave {

    private final SharedPreferences sharedPreferences;

    public LocalSave(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveUser(String id, String email, String phone, String username, String password, String name, String surname, String address){
        this.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        this.putString(Constants.KEY_USER_ID, id);
        this.putString(Constants.KEY_USER_NAME, name);
        this.putString(Constants.KEY_USER_SURNAME, surname);
        this.putString(Constants.KEY_USER_ADDRESS, address);
        this.putString(Constants.KEY_USER_EMAIL, email);
        this.putString(Constants.KEY_USER_PHONE, phone);
        this.putString(Constants.KEY_USER_USERNAME, username);
        this.putString(Constants.KEY_PASSWORD, password);
    }
}
