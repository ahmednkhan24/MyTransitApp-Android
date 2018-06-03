package com.example.faraz.mytransitapp_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DisplayResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);


    }

    // onResume() will be called before the activity starts to run.
    // The only way this activity will start running is
    // if MainActivity.java or trainActivity.java called it
    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Transit", "In DisplayResults.java onResume()");

        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("stpid");
        String routeNum = myIntent.getStringExtra("rt");

        Log.d("Transit", "ID: " + id + ", RouteNumber: " + routeNum);
    }
}
