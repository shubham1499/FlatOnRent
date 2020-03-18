package com.example.flatonrent.ownerclasses;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.dashboard.ownerdashboardfragment;
import com.example.flatonrent.ownerclasses.notification.ownernotification_fragment;
import com.example.flatonrent.ownerclasses.profile.ownerprofile_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;


public class main_home_owner extends AppCompatActivity implements OnNavigationItemSelectedListener {
    String loggedas;
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_owner);

        loadFragment(new ownerdashboardfragment());


        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new ownerdashboardfragment();
                break;

            case R.id.navigation_notifications:
                fragment = new ownernotification_fragment();
                break;

            case R.id.navigation_profile:
                fragment = new ownerprofile_fragment();
                break;
        }

        return loadFragment(fragment);
    }

    public String getLoggedas(){
        return this.loggedas;
    }

}
