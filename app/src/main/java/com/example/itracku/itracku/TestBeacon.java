package com.example.itracku.itracku;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;

/**
 * Created by Arnm on 2016-10-30.
 */

public class TestBeacon {
    private double x;
    private double y;
    private double originX;
    private double originY;
    private String uuid;
    private String major;
    private String minor;

    public TestBeacon(double x, double y, double originX, double originY, String uuid, String major, String minor) {
        this.x = x;
        this.y = y;
        this.originX = originX;
        this.originY = originY;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getOriginX() {
        return originX;
    }

    public void setOriginX(double x) {
        this.originX = x;
    }

    public double getOriginY() {
        return originY;
    }

    public void setOriginY(double y) {
        this.originY = y;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public boolean isEquals(Beacon beacon) {
        if(Identifier.parse(uuid).equals(beacon.getId1()) && Identifier.parse(major).equals(beacon.getId2()) && Identifier.parse(minor).equals(beacon.getId3())) {
            return true;
        }

        return false;
    }
}