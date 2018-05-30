package com.example.faraz.mytransitapp_android;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner busRouteSpinner;
    private Spinner busDirectionSpinner;
    private Spinner busStopSpinner;

    private ArrayAdapter<CharSequence> busRouteAdapter;
    private ArrayAdapter<CharSequence> busDirectionAdapter;
    private ArrayAdapter<CharSequence> busStopAdapter;

    private String selectedRoute;
    private String selectedDirection;
    private String selectedStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find all 3 spinners and attach adapters to them
        busRouteSpinner = findViewById(R.id.Bus_Route_Spinner);
        setRouteAdapter(R.array.Bus_Routes_Array);

        busDirectionSpinner = findViewById(R.id.Bus_Direction_Spinner);
        setDirectionAdapter(R.array.Empty_Direction);

        busStopSpinner = findViewById(R.id.Bus_Stop_Spinner);
        setStopAdapter(R.array.Empty_Stop);

        // Set an OnItemSelected listener on the bus route spinner
        busRouteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // sets an adapter with the given resource ID to the route spinner
    private void setRouteAdapter(int resID) {
        // Create an ArrayAdapter using the String array and a spinner layout
        busRouteAdapter = ArrayAdapter.createFromResource(this, resID, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        busRouteAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        busRouteSpinner.setAdapter(busRouteAdapter);
    }

    // sets an adapter with the given resource ID to the direction spinner
    private void setDirectionAdapter(int resID) {
        busDirectionAdapter = ArrayAdapter.createFromResource(this, resID, R.layout.spinner_item);
        busDirectionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        busDirectionSpinner.setAdapter(busDirectionAdapter);
    }

    // sets an adapter with the given resource ID to the stops spinner
    private void setStopAdapter(int resID) {
        busStopAdapter = ArrayAdapter.createFromResource(this, resID, R.layout.spinner_item);
        busStopAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        busStopSpinner.setAdapter(busStopAdapter);
    }
}
