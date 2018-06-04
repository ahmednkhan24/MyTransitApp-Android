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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class trainActivity extends AppCompatActivity {
    private Button trackButton;
    private Switch trainToBusSwitch;
    private Spinner trainStopSpinner;
    private RadioGroup radioButtonGroup;

    private String selectedStop;
    private String ID;

    // cta data objects
    private CTA cta;
    private Map<String, KeyValue> ctaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        cta = new CTA();
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

                int checkedRadioBtnID = radioButtonGroup.getCheckedRadioButtonId();
                if (checkedRadioBtnID == R.id.FP_RadioButton) {
                    ID = ctaDB.get("FP").find(selectedStop);
                }
                else {
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
                    Toast.makeText(trainActivity.this, "Oops! You missed something", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(trainActivity.this, "Tracking...", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(trainActivity.this, DisplayResults.class);
                myIntent.putExtra("stpid", ID);
                myIntent.putExtra("rt", "Blue");
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
