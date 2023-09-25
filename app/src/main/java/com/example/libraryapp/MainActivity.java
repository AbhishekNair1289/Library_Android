package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private  static  int SPLASH_TIME_OUT = 5000;

    Animation topanimation, bottomanimation;
    ImageView first, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topanimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        first = findViewById(R.id.elibrary);
        second = findViewById(R.id.elibraryicon);

        first.setAnimation(topanimation);
        second.setAnimation(bottomanimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}