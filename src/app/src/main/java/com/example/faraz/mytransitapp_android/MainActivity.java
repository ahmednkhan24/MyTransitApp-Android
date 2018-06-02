package com.example.faraz.mytransitapp_android;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // the track button
    private Button trackButton;

    // spinners
    private Spinner busRouteSpinner;
    private Spinner busDirectionSpinner;
    private Spinner busStopSpinner;

    // strings to hold the data
    private String selectedRoute;
    private String selectedDirection;
    private String selectedStop;
    private String ID;

    // cta data objects
    private final CTA cta = new CTA();
    private Map<String, KeyValue> ctaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ctaDB = new HashMap<>();
        ctaDB.put("008N", new KeyValue(createList(R.array.Eight_North_Stops), createList(R.array.Eight_North_IDs)));
        ctaDB.put("008S", new KeyValue(createList(R.array.Eight_South_Stops), createList(R.array.Eight_South_IDs)));
        ctaDB.put("007W", new KeyValue(createList(R.array.Seven_West_Stops), createList(R.array.Seven_West_IDs)));
        ctaDB.put("007E", new KeyValue(createList(R.array.Seven_East_Stops), createList(R.array.Seven_East_IDs)));
        ctaDB.put("012W", new KeyValue(createList(R.array.Twelve_West_Stops), createList(R.array.Twelve_West_IDs)));
        ctaDB.put("012E", new KeyValue(createList(R.array.Twelve_East_Stops), createList(R.array.Twelve_East_IDs)));
        ctaDB.put("060W", new KeyValue(createList(R.array.Sixty_West_Stops), createList(R.array.Sixty_West_IDs)));
        ctaDB.put("060E", new KeyValue(createList(R.array.Sixty_East_Stops), createList(R.array.Sixty_East_IDs)));
        ctaDB.put("157W", new KeyValue(createList(R.array.OneFiveSeven_West_Stops), createList(R.array.OneFiveSeven_West_IDs)));
        ctaDB.put("157E", new KeyValue(createList(R.array.OneFiveSeven_East_Stops), createList(R.array.OneFiveSeven_East_IDs)));

        // Bus Stop Spinner
        busStopSpinner = findViewById(R.id.Bus_Stop_Spinner);
        final ArrayList<String> busStopList = createList(R.array.Empty_Stop);
        final ArrayAdapter<String> busStopAdapter = createAdapter(busStopList);
        busStopSpinner.setAdapter(busStopAdapter);
        busStopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStop = parent.getItemAtPosition(position).toString();
                if (selectedRoute.equals("Select Route") ||
                    selectedDirection.equals("Select Direction") ||
                    selectedStop.equals("Select Stop")) {
                    return;
                }
                Log.d("Transit", selectedRoute + ", " + selectedDirection + ", " + selectedStop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Bus Direction Spinner
        busDirectionSpinner = findViewById(R.id.Bus_Direction_Spinner);
        final ArrayList<String> busDirectionList = createList(R.array.Empty_Direction);
        final ArrayAdapter<String> busDirectionAdapter = createAdapter(busDirectionList);
        busDirectionSpinner.setAdapter(busDirectionAdapter);
        busDirectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDirection = parent.getItemAtPosition(position).toString();

                busStopAdapter.clear();
                busStopAdapter.addAll(createList(R.array.Empty_Stop));
                busStopAdapter.notifyDataSetChanged();

                String key = cta.checkBus(selectedRoute, selectedDirection);
                if (key.equals(""))
                    return;

                KeyValue value = ctaDB.get(key);
                if (value == null)
                    return;

                busStopAdapter.clear();
                busStopAdapter.addAll(value.getNames());
                busStopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Bus Route Spinner
        busRouteSpinner = findViewById(R.id.Bus_Route_Spinner);
        final ArrayList<String> busRouteList = createList(R.array.Bus_Routes_Array);
        final ArrayAdapter<String> busRouteAdapter = createAdapter(busRouteList);
        busRouteSpinner.setAdapter(busRouteAdapter);
        busRouteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = parent.getItemAtPosition(position).toString();

                busStopAdapter.clear();
                busStopAdapter.addAll(createList(R.array.Empty_Stop));
                busStopAdapter.notifyDataSetChanged();

                if (selectedRoute.equals("Select Route")) {
                    return;
                }
                else if (selectedRoute.equals("8 - Halsted")) {
                    busDirectionAdapter.clear();
                    busDirectionAdapter.addAll(createList(R.array.NorthSouth_Direction));
                    busDirectionAdapter.notifyDataSetChanged();
                }
                else {
                    busDirectionAdapter.clear();
                    busDirectionAdapter.addAll(createList(R.array.EastWest_Direction));
                    busDirectionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // find the track button and add a click listener to it
        trackButton = findViewById(R.id.Track_Bus_Button);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked the button", Toast.LENGTH_SHORT).show();
                //EightNorth.Print();
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
