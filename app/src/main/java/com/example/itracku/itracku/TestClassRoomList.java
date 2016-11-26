package com.example.itracku.itracku;

import java.util.ArrayList;

/**
 * Created by dongs on 2016. 11. 25..
 */

public class TestClassRoomList {
    private ArrayList<TestClassRoom> classRoomList = new ArrayList<>();

    public void init(double scaleX, double scaleY){
        classRoomList.add(new TestClassRoom(365, 10, 715, 360, scaleX, scaleY, "332"));
        classRoomList.add(new TestClassRoom(365, 360, 715, 1000,scaleX,scaleY,"331"));
        classRoomList.add(new TestClassRoom(365, 1000, 715 , 1440,scaleX,scaleY,"330"));
        classRoomList.add(new TestClassRoom(365, 1440, 715, 1860,scaleX,scaleY,"329"));
    }

    public ArrayList<TestClassRoom> getClassRoomList() {
        return classRoomList;
    }
}
