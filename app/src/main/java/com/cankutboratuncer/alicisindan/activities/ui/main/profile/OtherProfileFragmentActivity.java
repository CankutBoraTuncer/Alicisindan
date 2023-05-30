package com.cankutboratuncer.alicisindan.activities.ui.main.profile;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

public class OtherProfileFragmentActivity extends FragmentActivity {
    private String id;

    public OtherProfileFragmentActivity()
    {
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if(b != null)
            id = b.getString("userId");
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new OtherProfileFragment(id)).commit();}
    }
}
