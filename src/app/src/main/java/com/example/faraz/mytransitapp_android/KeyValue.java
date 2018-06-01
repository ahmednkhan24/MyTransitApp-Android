package com.example.faraz.mytransitapp_android;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyValue {
    private Map<String, String> myMap;

    public KeyValue(ArrayList<String> names, ArrayList<String> ids) {
        myMap = new HashMap<>();

        for (int i = 1; i < names.size(); i++) {
            myMap.put(names.get(i), ids.get(i));
        }
    }

    public String Find(String id) {
        return myMap.get(id);
    }

    public void Print() {
        for (Map.Entry m : myMap.entrySet()) {
            Log.d("Transit", m.getKey() + ", " + m.getValue());
        }
    }
}
