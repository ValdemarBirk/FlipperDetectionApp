package com.example.myapplication.API;

import java.util.ArrayList;
import java.util.List;

public class BLEDevice {
    private ArrayList<String> results = new ArrayList<>();

    public String getDevice(String mac_addr) {
        System.out.println("results" + results);
        if(results.isEmpty()) {
            return "list is empty";
        }
        for(int i=0; i< results.size(); i++) {
            if(results.get(i).equals(mac_addr)) {
                return results.get(i);
            }
        }
        return "Mac not found in list";
    }
}
