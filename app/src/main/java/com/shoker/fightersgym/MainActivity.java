package com.shoker.fightersgym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    public void videos(View view) {
        Intent i = new Intent(this ,ChannelActivity.class);
        startActivity(i);
    }

    public void info(View view) {
        Intent i = new Intent(this ,InfoActivity.class);
        startActivity(i);
    }

    public void classes(View view) {
        Intent i = new Intent(this ,ClassesActivity.class);
        startActivity(i);


    }

    public void social(View view) {
        Intent i = new Intent(this ,SocialMediaActivity.class);
        startActivity(i);
    }

    public void schedule(View view) {
        Intent i = new Intent(this ,ScheduleActivity.class);
        startActivity(i);
    }

    public void offer(View view) {
        Intent i = new Intent(this ,OfferActivity.class);
        startActivity(i);
    }

}