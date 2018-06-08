package com.example.faraz.mytransitapp_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DisplayResults extends AppCompatActivity {

    private final String CTA_TRAIN_URL = "TEST";
    private final String CTA_BUS_URL = "http://ctabustracker.com/bustime/api/v2/getpredictions?";
    private final String CTA_TRAIN_KEY = "TEST";
    private final String CTA_BUS_KEY = "ARXsvPdwAMNqNgnbMuyVreNbq";

    private String route;
    private String direction;
    private String stop;
    private String stpid;
    private String routeNum;

    private ImageButton newSearchButton;
    private String previousScreen;

    private TextView currentStopDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        currentStopDisplay = findViewById(R.id.Current_Stop_Display);
        currentStopDisplay.setText("");

        newSearchButton = findViewById(R.id.NewSearchButton);
        newSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent;

                if (previousScreen.equals("BUS"))
                    myIntent = new Intent(DisplayResults.this, MainActivity.class);
                else
                    myIntent = new Intent(DisplayResults.this, TrainActivity.class);

                finish();
                startActivity(myIntent);
            }
        });
    }

    // onResume() will be called before the activity starts to run.
    // The only way this activity will start running is
    // if MainActivity.java or TrainActivity.java called it
    @Override
    protected void onResume() {
        super.onResume();

        Intent myIntent = getIntent();
        route = myIntent.getStringExtra("route");
        direction = myIntent.getStringExtra("direction");
        stop = myIntent.getStringExtra("stop");
        stpid = myIntent.getStringExtra("stpid");
        routeNum = myIntent.getStringExtra("rtNum");

        if (routeNum.equals("BLUE")) {
            previousScreen = "TRAIN";
            loadTrainData();
        }
        else {
            previousScreen = "BUS";
            loadBusData();
        }
    }

    private void loadTrainData() {
        currentStopDisplay.setText(stop + " (" + direction + ")");

    }

    private void loadBusData() {
        currentStopDisplay.setText(stop + " (" + direction + ")");

        RequestParams params = new RequestParams();
        params.put("key", CTA_BUS_KEY);
        params.put("rt", routeNum);
        params.put("stpid", stpid);
        params.put("format", "json");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(CTA_BUS_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Transit", "Success! JSON: " + response.toString());

                busDataModel busDataModel = new busDataModel(response);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Transit", "Fail " + e.toString());
                Log.d("Transit", "Status Code: " + statusCode);
                Toast.makeText(DisplayResults.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}