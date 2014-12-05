package com.DiaDiem.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Class lay du lieu load Database cho Listview */
public class JSONParser {

    // Receives a JSONObject and returns a list
    public List<HashMap<String, Object>> parse(JSONObject jObject) {

        JSONArray jdiadiem = null;
        try {
            // Retrieves all the elements in the 'countries' array
            jdiadiem = jObject.getJSONArray("diadiem");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Invoking getCountries with the array of json object
        // where each json object represent a country
        return getDIADIEM(jdiadiem);
    }


    private List<HashMap<String, Object>> getDIADIEM(JSONArray jdiadiem) {
        int diadiemCount = jdiadiem.length();
        List<HashMap<String, Object>> diadiemList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> diadiem = null;

        // Taking each country, parses and adds to list object
        for (int i = 0; i < diadiemCount; i++) {
            try {
                // Call getCountry with country JSON object to parse the country
                diadiem = getCountry((JSONObject) jdiadiem.get(i));
                diadiemList.add(diadiem);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return diadiemList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getCountry(JSONObject jdiadiem) {

        HashMap<String, Object> diadiem = new HashMap<String, Object>();
        String TenDiaDiem = "";
        String image = "";
        String ThongTin = "";
        String DiaChi = "";
        String ToaDox = "-NA-";
        String ToaDoy = "-NA-";

        try {
            TenDiaDiem = jdiadiem.getString("TenDiaDiem");
            image = jdiadiem.getString("image");
            ThongTin = jdiadiem.getString("ThongTin");
            DiaChi = jdiadiem.getString("DiaChi");
            // Extracting latitude, if available
            if (!jdiadiem.isNull("ToaDox")) {
                ToaDox = jdiadiem.getString("ToaDox");
            }

            // Extracting longitude, if available
            if (!jdiadiem.isNull("ToaDoy")) {
                ToaDoy = jdiadiem.getString("ToaDoy");
            }
            diadiem.put("TenDiaDiem", TenDiaDiem);
            diadiem.put("image", R.drawable.blank);
            diadiem.put("flag_path", image);
            diadiem.put("ThongTin", ThongTin);
            diadiem.put("DiaChi", DiaChi);
            diadiem.put("ToaDox", ToaDox);
            diadiem.put("ToaDoy", ToaDoy);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return diadiem;
    }
}