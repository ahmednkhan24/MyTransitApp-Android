package com.example.faraz.mytransitapp_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;

public class TrainDataModel {
    private JSONObject jsonObject;
    private ArrayList<String> predictions;

    public TrainDataModel(JSONObject obj) {
        jsonObject = obj;
        predictions = new ArrayList<String>();
        fromJSON();
    }

    public ArrayList<String> getPredictions() {
        return predictions;
    }

    private void fromJSON() {
        try {
            JSONArray arr = jsonObject.getJSONObject("ctatt").getJSONArray("eta");

            for (int i = 0; i < arr.length(); i++) {
                String isApproaching = arr.getJSONObject(i).getString("isApp");

                if (isApproaching.equals("1"))
                    predictions.add("DUE");
                else {
                    String prdt = arr.getJSONObject(i).getString("prdt");
                    String arrt = arr.getJSONObject(i).getString("arrT");

                    Calendar curTime = setTime(prdt);
                    Calendar arrTime = setTime(arrt);

                    long diffInMilli = arrTime.getTimeInMillis() - curTime.getTimeInMillis();
                    int minutes = (int) Math.floor(diffInMilli / 60000);

                    predictions.add(Integer.toString(minutes) + " minutes away");
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Calendar setTime(String time) {
        int pYear = Integer.parseInt(time.substring(0, 4));
        int pMonth = Integer.parseInt(time.substring(5, 7));
        int pDate = Integer.parseInt(time.substring(8, 10));
        int pHour = Integer.parseInt(time.substring(11, 13));
        int pMin = Integer.parseInt(time.substring(14, 16));

        Calendar curTime = Calendar.getInstance();
        curTime.set(pYear, pMonth, pDate, pHour, pMin);

        return curTime;
    }
}
