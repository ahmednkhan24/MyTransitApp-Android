package com.example.faraz.mytransitapp_android;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyValue {
    private Map<String, String> myMap;
    private ArrayList<String> names;
    private ArrayList<String> ids;

    public KeyValue(ArrayList<String> names, ArrayList<String> ids) {
        myMap = new HashMap<>();
        this.names = names;
        this.ids = ids;

        for (int i = 1; i < names.size(); i++) {
            myMap.put(names.get(i), ids.get(i));
        }
    }

    public String find(String id) {
        return myMap.get(id);
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void print() {
        for (Map.Entry m : myMap.entrySet()) {
            Log.d("Transit", m.getKey() + ", " + m.getValue());
        }
    }
}
