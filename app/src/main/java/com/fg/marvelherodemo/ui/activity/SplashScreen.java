package com.fg.marvelherodemo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.fg.marvelherodemo.R;

public class SplashScreen extends AppCompatActivity {

    Context ctx = this;
    int splashInterval = 2000;
    public boolean sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(ctx, HeroActivity.class);
                startActivity(i);
                this.finish();
            }
            private void finish() {
        }}, splashInterval);
    }
}
