package com.example.indevelUpChallenge.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indevelUpChallenge.R;

import static com.example.indevelUpChallenge.utils.Constants.SPLASH_TIME;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setHandler(SPLASH_TIME);
    }

    public void setHandler(int time) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, time);
    }
}