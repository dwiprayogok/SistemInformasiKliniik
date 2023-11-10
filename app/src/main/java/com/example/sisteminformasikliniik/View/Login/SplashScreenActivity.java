package com.example.sisteminformasikliniik.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sisteminformasikliniik.R;

public class SplashScreenActivity extends AppCompatActivity {
    private int loadingTime=4000;
    private ImageView imgSplash;
    Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        imgSplash = findViewById(R.id.imgSplash);
        new Handler().postDelayed((Runnable) () -> {
            Intent login=new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(login);
            finish();

        },loadingTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
