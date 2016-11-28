package com.example.itracku.itracku;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Arnm on 2016-10-29.
 */

public class TestBeaconList {
    private ArrayList<TestBeacon> beaconList = new ArrayList<>();

    public void init(float scaleX, float scaleY) {
//        beaconList.add(new TestBeacon((double) 715 * scaleX, (double) 360 * scaleY, 715, 360, "617E8096-BAB7-43F3-BF96-3FD6F26D67B1", "6752", "9722"));
//        beaconList.add(new TestBeacon((double) 365 * scaleX, (double) 360 * scaleY, 365, 360, "617E8096-BAB7-43F3-BF96-3FD6F26D67B1", "4660", "64001"));
//        beaconList.add(new TestBeacon((double) 365 * scaleX, (double) 10 * scaleY, 365, 10, "617E8096-BAB7-43F3-BF96-3FD6F26D67B1", "4660", "64002"));

//        beaconList.add(new TestBeacon((int) ((float) 310 * scaleX), (int) ((float) 1809 * scaleY), "50CF90B0-0C8F-11E4-9191-0800200C9A66", "329", "5"));
//        beaconList.add(new TestBeacon((int) ((float) 310 * scaleX), (int) ((float) 893 * scaleY), "50CF90B0-0C8F-11E4-9191-0800200C9A66", "331", "5"));
//        beaconList.add(new TestBeacon((int) ((float) 310 * scaleX), (int) ((float) 316 * scaleY), "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "5"));
/*
        beaconList.add(new TestBeacon((double) 715 * scaleX, (double) 360 * scaleY, 715, 360, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "1"));
        beaconList.add(new TestBeacon((double) 365 * scaleX, (double) 360 * scaleY, 365, 360, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "2"));
        beaconList.add(new TestBeacon((double) 365 * scaleX, (double) 10 * scaleY, 365, 10, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "3"));
        beaconList.add(new TestBeacon((double) 715 * scaleX, (double) 10 * scaleY, 715, 10, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "4"));
*/
        beaconList.add(new TestBeacon((double) 310 * scaleX, (double) 1809 * scaleY, 310, 1809, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "329", "5"));
        beaconList.add(new TestBeacon((double) 310 * scaleX, (double) 893 * scaleY, 310, 893, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "331", "5"));
        beaconList.add(new TestBeacon((double) 310 * scaleX, (double) 316 * scaleY, 310, 316, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "5"));

        beaconList.add(new TestBeacon((double) 715 * scaleX, (double) 1440 * scaleY, 715, 1440, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "1"));
        beaconList.add(new TestBeacon((double) 365 * scaleX, (double) 1440 * scaleY, 365, 1440, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "2"));
        beaconList.add(new TestBeacon((double) 365 * scaleX, (double) 1005 * scaleY, 365, 1005, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "3"));
        beaconList.add(new TestBeacon((double) 715 * scaleX, (double) 1005 * scaleY, 715, 1005, "50CF90B0-0C8F-11E4-9191-0800200C9A66", "332", "4"));

    }

    public ArrayList<TestBeacon> getBeaconList() {
        return beaconList;
    }
}
