package com.example.faraz.mytransitapp_android;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class trainActivity extends AppCompatActivity {
    // the track button
    private Button trackButton;

    // the switch to bus switch
    private Switch trainToBusSwitch;

    private Spinner trainStopSpinner;

    private String selectedStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        // Train Stop Spinner
        trainStopSpinner = findViewById(R.id.Train_Route_Spinner);
        final ArrayList<String> trainStopList = createList(R.array.Blueline_Stops);
        final ArrayAdapter<String> busStopAdapter = createAdapter(trainStopList);
        trainStopSpinner.setAdapter(busStopAdapter);
        trainStopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStop = parent.getItemAtPosition(position).toString();
                if (selectedStop.equals("Select Stop"))
                    return;
                Log.d("Transit", selectedStop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // find the track button and add a click listener to it
        trackButton = findViewById(R.id.Track_Train_Button);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(trainActivity.this, "You clicked the button", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(trainActivity.this, DisplayResults.class);
                finish();
                startActivity(myIntent);
            }
        });

        // find the switch and add a checked listener to it
        trainToBusSwitch = findViewById(R.id.Train_Bus_Switch);
        trainToBusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Intent myIntent = new Intent(trainActivity.this, MainActivity.class);
                    finish();
                    startActivity(myIntent);
                }
            }
        });
    }

    // given the resource ID, converts a XML string array to a Java ArrayList and returns it
    private ArrayList<String> createList(int resID) {
        return new ArrayList<>(Arrays.asList(getResources().getStringArray(resID)));
    }

    // given an ArrayList, creates an adapter and fills it with the contents from the given list
    // disables the first item and colors it gray
    private ArrayAdapter<String> createAdapter(ArrayList<String> list) {
        final ArrayAdapter<String> adap = new ArrayAdapter<String>(this, R.layout.spinner_item, list) {
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                if(position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the disable item text color
                if(position == 0)
                    tv.setTextColor(Color.GRAY);
                else
                    tv.setTextColor(Color.BLACK);

                return view;
            }
        };

        // Specify the layout to use when the list of choices appears
        adap.setDropDownViewResource(R.layout.spinner_dropdown_item);

        return adap;
    }
}
