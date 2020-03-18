package com.example.flatonrent.tenantclasses;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.dashboard.ownerdashboardfragment;
import com.example.flatonrent.ownerclasses.notification.ownernotification_fragment;
import com.example.flatonrent.ownerclasses.profile.ownerprofile_fragment;
import com.example.flatonrent.tenantclasses.tdashboard1.tenant_dashboard;
import com.example.flatonrent.tenantclasses.thome1.thome_1;
import com.example.flatonrent.tenantclasses.tnotifications.tenant_notification;
import com.example.flatonrent.tenantclasses.tprofile1.tenant_profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;


public class main_home_tenant extends AppCompatActivity implements OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_main_home_tenant);

        //getSupportActionBar().hide();

        loadFragment(new thome_1());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        Log.i("loggedas",getIntent().getExtras().getString("loggedas"));
        Log.i("loggedas1", FirebaseAuth.getInstance().getUid());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new thome_1();
                break;

            case R.id.navigation_notifications:
                fragment = new tenant_notification();
                break;

            case R.id.navigation_profile:
                fragment = new tenant_profile();
                break;
        }
        return loadFragment(fragment);
    }

    public String getLoggedas(){
        return this.loggedas;
    }

}
