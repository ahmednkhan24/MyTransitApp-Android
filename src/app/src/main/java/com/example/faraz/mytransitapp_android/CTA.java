package com.example.faraz.mytransitapp_android;

import android.util.Log;

public class CTA {
    public String checkBus(String selectedRoute, String selectedDirection) {
        if (selectedRoute == null || selectedRoute.equals("Select Route"))
            return "";
        else if (selectedDirection == null || selectedDirection.equals("Select Direction"))
            return "";

        switch (selectedRoute) {
            case "8 - Halsted":
                if (selectedDirection.equals("Northbound"))
                    return "008N";
                else
                    return "008S";
            case "7 - Harrison":
                if (selectedDirection.equals("Eastbound"))
                    return "007E";
                else
                    return "007W";
            case "12 - Roosevelt":
                if (selectedDirection.equals("Eastbound"))
                    return "012E";
                else
                    return "012W";
            case "60 - Blue Island/26th":
                if (selectedDirection.equals("Eastbound"))
                    return "060E";
                else
                    return "060W";
            case "157 - Streeterville/Taylor":
                if (selectedDirection.equals("Eastbound"))
                    return "157E";
                else
                    return "157W";
            default:
                return "";
        }
    }
}
