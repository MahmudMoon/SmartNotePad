package com.example.moon.smartnotepad;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500*2);
                Intent intent = new Intent(splash.this,MainActivity.class);
                startActivity(intent);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


    }
}
