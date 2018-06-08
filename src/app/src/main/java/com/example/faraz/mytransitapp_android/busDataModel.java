package com.example.faraz.mytransitapp_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class busDataModel {
    private JSONObject jsonObject;
    private ArrayList<String> predictions;

    public busDataModel(JSONObject obj) {
        jsonObject = obj;
        predictions = new ArrayList<String>();
        fromJSON();
    }

    private void fromJSON() {
        try {
            JSONArray arr = jsonObject.getJSONObject("bustime-response").getJSONArray("prd");

            for (int i = 0; i < arr.length(); i++) {
                String time = arr.getJSONObject(i).getString("prdctdn");
                if (time.equals("DUE"))
                    predictions.add(time);
                else
                    predictions.add(time + " minutes away");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPredictions() {
        return predictions;
    }
}