package com.example.itracku.itracku;

/**
 * Created by dongs on 2016. 11. 25..
 */

public class TestClassRoom {
    //좌측 상단
    private double uX;
    private double uY;
    //우측 하단
    private double dX;
    private double dY;
    //좌측 상단
    private double originuX;
    private double originuY;
    //우측 하단
    private double origindX;
    private double origindY;

    private String roomName;

    public TestClassRoom(double originuX, double originuY, double origindX, double origindY, double scaleX,double scaleY,String roomName) {
        this.uX = originuX * scaleX;
        this.uY = originuY * scaleY;
        this.dX = origindX * scaleX;
        this.dY = origindY * scaleY;
        this.originuX = originuX;
        this.originuY = originuY;
        this.origindX = origindX;
        this.origindY = origindY;
        this.roomName = roomName;
    }

    public double getuX() {
        return uX;
    }

    public void setuX(double uX) {
        this.uX = uX;
    }

    public double getuY() {
        return uY;
    }

    public void setuY(double uY) {
        this.uY = uY;
    }

    public double getdX() {
        return dX;
    }

    public void setdX(double dX) {
        this.dX = dX;
    }

    public double getdY() {
        return dY;
    }

    public void setdY(double dY) {
        this.dY = dY;
    }

    public double getOriginuX() {
        return originuX;
    }

    public void setOriginuX(double originuX) {
        this.originuX = originuX;
    }

    public double getOriginuY() {
        return originuY;
    }

    public void setOriginuY(double originuY) {
        this.originuY = originuY;
    }

    public double getOrigindX() {
        return origindX;
    }

    public void setOrigindX(double origindX) {
        this.origindX = origindX;
    }

    public double getOrigindY() {
        return origindY;
    }

    public void setOrigindY(double origindY) {
        this.origindY = origindY;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
