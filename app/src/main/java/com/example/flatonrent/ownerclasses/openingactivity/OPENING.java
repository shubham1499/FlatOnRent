package com.example.flatonrent.ownerclasses.openingactivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.flatonrent.ownerclasses.signinsignup.loginPage;
import com.example.flatonrent.R;

public class OPENING extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Intent i = new Intent(getApplicationContext(), loginPage.class);
                startActivity(i);
                finish();
            }
        }, 1500);
    }
}