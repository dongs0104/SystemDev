package com.example.itracku.itracku;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Arnm on 2016-10-21.
 */

public class MapInfo {
    private int mapWidth;
    private int mapHeight;
    private float scaleX;
    private float scaleY;
    private Bitmap map;

    public MapInfo() {}

    public MapInfo(Bitmap map) {
        this.map = map;
    }

    public void initialize(int w, int h) {
        this.scaleX = ((float) w / map.getWidth());
        this.scaleY = ((float) h / map.getHeight());
        this.mapWidth = w;
        this.mapHeight = h;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public Bitmap getMap() {
        return map;
    }

    public void setMap(Bitmap map) {
        this.map = map;
    }
}
