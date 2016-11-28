package com.example.itracku.itracku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.itracku.itracku.trilateration.NonLinearLeastSquaresSolver;
import com.example.itracku.itracku.trilateration.TrilaterationFunction;

import org.altbeacon.beacon.Beacon;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.ArrayList;

/**
 * Created by Arnm on 2016-10-17.
 */

public class CanvasView extends View {
    private static double M_TO_PX = 21.208791;
    TestBeaconList tbl;
    TestClassRoomList tcl;
    ArrayList<Beacon> beacons;
    UserLocation user;
    RoomLocation room;
    MapInfo mapInfo = null;
    boolean roomSearchToggle ;
    public CanvasView(Context context) {
        super(context);
        user = new UserLocation();
        room = new RoomLocation();
        roomSearchToggle = false;
    }

    public void setRoomSearchToggle(boolean roomSearchToggle) {
        this.roomSearchToggle = roomSearchToggle;
    }

    public CanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        user = new UserLocation();
        room = new RoomLocation();
        roomSearchToggle = false;
    }

    public CanvasView(Context context, AttributeSet attributeSet, int re) {
        super(context, attributeSet, re);
        user = new UserLocation();
        room = new RoomLocation();
        roomSearchToggle = false;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(tbl != null) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            for (TestBeacon t : tbl.getBeaconList()) {
                canvas.drawCircle((float) t.getX(), (float) t.getY(), 10f, paint);
            }
        }
        canvas.drawCircle(user.x, user.y, user.radius, user.paint);
        if (roomSearchToggle) {
            canvas.drawLine(room.x , room.y, room.x - 30, room.y, room.paint);
            //길게 색칠
            canvas.drawLine(room.x - 30, room.y , room.x - 30 , user.y, room.paint);
            canvas.drawLine(room.x - 30, user.y , user.x, user.y, room.paint);
        }
    }

    public void setBeacons(ArrayList<Beacon> beacons) {
        this.beacons = beacons;
    }

    /*public void drawUserLocation() {
        double distance = beacons.get(0).getDistance() + beacons.get(1).getDistance();
        double d1 = beacons.get(0).getDistance() / distance;
        double d2 = beacons.get(1).getDistance() / distance;

        int px1, px2;
        double ul1, ul2;

    }*/

    public void drawUserLocation(ArrayList<Beacon> beacons) {
        if(beacons.size() >= 3) {
            ArrayList<TestBeacon> testBeacons = new ArrayList<>();
            ArrayList<Double> testdistances = new ArrayList<>();

            for(TestBeacon t : tbl.getBeaconList()) {
                for(int i = 0; i < beacons.size(); i++) {
                    if(t.isEquals(beacons.get(i))) {
                        testBeacons.add(t);
                        testdistances.add(calculateBeaconDistance(beacons.get(i)));
                        break;
                    }
                }
            }
            if (testBeacons.size() == 4) {
                double[][] positions = new double[][]{{testBeacons.get(0).getOriginX(), testBeacons.get(0).getOriginY()}, {testBeacons.get(1).getOriginX(), testBeacons.get(1).getOriginY()}, {testBeacons.get(2).getOriginX(), testBeacons.get(2).getOriginY()},{testBeacons.get(3).getOriginX(), testBeacons.get(3).getOriginY()}};
                double[] distances = new double[]{testdistances.get(0), testdistances.get(1), testdistances.get(2), testdistances.get(3)};

                NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
                LeastSquaresOptimizer.Optimum optimum = solver.solve();
                double[] centroid = optimum.getPoint().toArray();
                user.x = (int) (centroid[0] * mapInfo.getScaleX());
                user.y = (int) (centroid[1] * mapInfo.getScaleY());
            }
        }
    }
    public void calPositioning(ArrayList<Beacon> beacons) {
        boolean inroom = false, incorridor = false;
        ArrayList<Beacon> copyBeacons = (ArrayList) beacons.clone();
        ArrayList<Beacon> tempBeacons = new ArrayList<>();

        if(beacons.size() == 0) {
            return;
        }
        if(beacons.size() >= 3) {
            for(Beacon b : beacons) {
                if(b.getId3().toString().equals("5")) {
                    copyBeacons.remove(b);
                } else if(calculateBeaconDistance(b) < 3.0) {
                    copyBeacons.remove(b);
                }
            }
            if(copyBeacons.size() == 4) {
                inroom = true;
            }
        }
        if(!inroom) {
            for(Beacon b : copyBeacons) {
                if(b.getId3().toString().equals("5")) {
                    tempBeacons.add(b);
                }
            }

            if(tempBeacons.size() >= 2) {
                incorridor = true;
            }
        }

        if(inroom) {
            drawUserLocation(copyBeacons);
        } else if(incorridor) {
            drawUserCorridorLocation(tempBeacons);
        }
    }
    public void drawUserCorridorLocation(ArrayList<Beacon> beacons) {
        int count = 0;
        double d = 0, dy = 0, sd = 0;
        double totalDistance = 0;
        ArrayList<TestBeacon> testBeacons = new ArrayList<>();
        totalDistance = calculateBeaconDistance(beacons.get(0)) + calculateBeaconDistance(beacons.get(1));

        for(TestBeacon t : tbl.getBeaconList()) {
            for(int i = 0; i < beacons.size(); i++) {
                if(t.isEquals(beacons.get(i))) {
                    testBeacons.add(t);
                    count++;
                    break;
                }
            }

            if(count >= 2) { break; }
        }

        if(testBeacons.get(0).getOriginY() >= testBeacons.get(1).getOriginY()) {
            d = testBeacons.get(0).getOriginY() - testBeacons.get(1).getOriginY();
            dy = testBeacons.get(1).getOriginY();
        } else {
            d = testBeacons.get(1).getOriginY() - testBeacons.get(0).getOriginY();
            dy = testBeacons.get(0).getOriginY();
        }

        sd = calculateBeaconDistance(beacons.get(0));
        d = dy + d * (sd / totalDistance);

        user.x = (int) testBeacons.get(0).getX();
        user.y = (int) (d * mapInfo.getScaleY());
        Log.d("test", String.valueOf(user.x) + ", " + String.valueOf(user.y));
    }
    public double calculateBeaconDistance(Beacon beacon) {
        double rssi = beacon.getRssi();

        double txPower = -60.0d;
        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }

        double ratio = rssi*1.0/txPower;

        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.42093)*Math.pow(ratio,6.9476) + 0.54992;
            Log.d(beacon.getId2() + ", " + beacon.getId3(), "calculateBeaconDistance: " + accuracy);
            return accuracy;
        }
    }

    public class UserLocation {
        public int x;
        public int y;
        public int radius;
        public Paint paint;

        public UserLocation() {
            this.x = 0;
            this.y = 0;
            this.radius = 10;
            this.paint = new Paint();
            this.paint.setColor(Color.BLUE);

        }
    }
    public class RoomLocation {
        public float x;
        public float y;
        public Paint paint;

        public RoomLocation() {
            this.x = 0;
            this.y = 0;
            this.paint = new Paint();
            this.paint.setColor(Color.RED);
            this.paint.setStrokeWidth(5.0f);
        }
    }


    /**
     * drawing test beacons
     */
    public void setTestBeacon(TestBeaconList tbl) {
        this.tbl = tbl;
    }
    /*
    * DrawPathToClass
    * */
    public void setTestClassRoom(TestClassRoomList tcl){this.tcl = tcl;}
    public void drawClassRoomPath(String sRoom)
    {
        if(roomSearchToggle == true)
        for (TestClassRoom t: tcl.getClassRoomList()) {

            if(t.getRoomName().equals(sRoom))
            {
                if( t.getuY() <= user.y && user.y <= t.getdY() ){
                    Log.d("Draw호출", "drawClassRoomPath: 찾음");
                    roomSearchToggle = false;
                }
                else
                {
                    room.x = (float) t.getuX();
                    room.y = (float) ((t.getdY() + t.getuY()) / 2);
                }
            }
        }

    }

}
