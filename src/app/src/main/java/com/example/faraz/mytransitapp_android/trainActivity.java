package com.example.faraz.mytransitapp_android;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TrainActivity extends AppCompatActivity {
    private Button trackButton;
    private Switch trainToBusSwitch;
    private Spinner trainStopSpinner;
    private RadioGroup radioButtonGroup;

    private String selectedStop;
    private String ID;
    private int checkedRadioBtnID;
    private String direction;

    // cta data objects
    private Map<String, KeyValue> ctaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        // when the app is fired up from the home screen, savedInstanceState will be null
        // When the app has already been running and the screen was rotated, the bundle exists
        if (savedInstanceState != null) {
            selectedStop = savedInstanceState.getString("stop");
            checkedRadioBtnID = savedInstanceState.getInt("dir");
            ID = savedInstanceState.getString("stpid");

            RadioButton fp = findViewById(R.id.FP_RadioButton);
            RadioButton oh = findViewById(R.id.OH_RadioButton);

            if (checkedRadioBtnID == R.id.FP_RadioButton) {
                direction = "FP";
                fp.setChecked(true);
                oh.setChecked(false);
            }
            else {
                direction = "OH";
                oh.setChecked(true);
                fp.setChecked(false);
            }
        }

        ctaDB = new HashMap<>();
        ctaDB.put("FP", new KeyValue(createList(R.array.Blueline_Stops), createList(R.array.FP_IDs)));
        ctaDB.put("OH", new KeyValue(createList(R.array.Blueline_Stops), createList(R.array.OH_IDs)));

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

                checkedRadioBtnID = radioButtonGroup.getCheckedRadioButtonId();
                if (checkedRadioBtnID == R.id.FP_RadioButton) {
                    direction = "FP";
                    ID = ctaDB.get("FP").find(selectedStop);
                }
                else {
                    direction = "OH";
                    ID = ctaDB.get("OH").find(selectedStop);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        radioButtonGroup = findViewById(R.id.Train_Direction_RadioGroup);
        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (selectedStop == null || selectedStop.equals("Select Stop")) {
                    return;
                }

                checkedRadioBtnID = checkedId;

                if (checkedId == R.id.FP_RadioButton) {
                    ID = ctaDB.get("FP").find(selectedStop);
                }
                else {
                    ID = ctaDB.get("OH").find(selectedStop);
                }
            }
        });

        // find the track button and add a click listener to it
        trackButton = findViewById(R.id.Track_Train_Button);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID == null) {
                    Toast.makeText(TrainActivity.this, "Oops! You missed something", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(TrainActivity.this, "Tracking...", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(TrainActivity.this, DisplayResults.class);
                myIntent.putExtra("route", "BLUE");
                myIntent.putExtra("direction", direction);
                myIntent.putExtra("stop", selectedStop);
                myIntent.putExtra("stpid", ID);
                myIntent.putExtra("rtNum", "BLUE");
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
                    Intent myIntent = new Intent(TrainActivity.this, MainActivity.class);
                    finish();
                    startActivity(myIntent);
                }
            }
        });
    }

    // app receives callback to this function when the Android OS detects a screen rotation
    // function stores the state of our app when the main activity is restarted due to screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("stop", selectedStop);
        outState.putInt("dir", checkedRadioBtnID);
        outState.putString("stpid", ID);
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
