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

import org.altbeacon.beacon.Beacon;

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
        if (roomSearchToggle == true) {
            canvas.drawLine(room.x - 20 , room.y, room.x , room.y, room.paint);
            canvas.drawLine(room.x - 20, room.y , room.x - 20 , user.y, room.paint);
            canvas.drawLine(room.x - 20, user.y , room.x, user.y, room.paint);
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
        ArrayList<Beacon> copyBeacons = (ArrayList) beacons.clone();
        for(Beacon b : beacons) {
            if(b.getId3().toString().equals("5")) {
                copyBeacons.remove(b);
            }
        }

        if(copyBeacons.size() >= 3) {
            ArrayList<TestBeacon> testBeacons = new ArrayList<>();
            ArrayList<Double> distances = new ArrayList<>();

            int count = 0;

            for(TestBeacon t : tbl.getBeaconList()) {
                for(int i = 0; i < copyBeacons.size(); i++) {
                    if(t.isEquals(copyBeacons.get(i))) {
                        testBeacons.add(t);
                        distances.add(calculateBeaconDistance(copyBeacons.get(i)));
                        //distances.add(copyBeacons.get(i).getDistance() * M_TO_PX);
                        count++;
                        break;
                    }
                }

                if(count >= 3) { break; }
            }

            double x1, x2, x3, y1, y2, y3;
            double x, y;

            x1 = (testBeacons.get(1).getOriginY() - testBeacons.get(0).getOriginY()) * (Math.pow(testBeacons.get(1).getOriginX(), 2) - Math.pow(testBeacons.get(2).getOriginX(), 2) + Math.pow(testBeacons.get(1).getOriginY(), 2) - Math.pow(testBeacons.get(2).getOriginY(), 2) - Math.pow(distances.get(1), 2) + Math.pow(distances.get(2), 2));
            x2 = (testBeacons.get(2).getOriginY() - testBeacons.get(1).getOriginY()) * (Math.pow(testBeacons.get(0).getOriginX(), 2) - Math.pow(testBeacons.get(1).getOriginX(), 2) + Math.pow(testBeacons.get(0).getOriginY(), 2) - Math.pow(testBeacons.get(1).getOriginY(), 2) - Math.pow(distances.get(0), 2) + Math.pow(distances.get(1), 2));
            x3 = 2 * ((testBeacons.get(1).getOriginX() - testBeacons.get(0).getOriginX()) * (testBeacons.get(2).getOriginY() - testBeacons.get(1).getOriginY()) - (testBeacons.get(2).getOriginX() - testBeacons.get(1).getOriginX()) * (testBeacons.get(1).getOriginY() - testBeacons.get(0).getOriginY()));
            x = (x1 - x2) / x3;

            y1 = (testBeacons.get(1).getOriginX() - testBeacons.get(0).getOriginX()) * (Math.pow(testBeacons.get(1).getOriginX(), 2) - Math.pow(testBeacons.get(2).getOriginX(), 2) + Math.pow(testBeacons.get(1).getOriginY(), 2) - Math.pow(testBeacons.get(2).getOriginY(), 2) - Math.pow(distances.get(1), 2) + Math.pow(distances.get(2), 2));
            y2 = (testBeacons.get(2).getOriginX() - testBeacons.get(1).getOriginX()) * (Math.pow(testBeacons.get(0).getOriginX(), 2) - Math.pow(testBeacons.get(1).getOriginX(), 2) + Math.pow(testBeacons.get(0).getOriginY(), 2) - Math.pow(testBeacons.get(1).getOriginY(), 2) - Math.pow(distances.get(0), 2) + Math.pow(distances.get(1), 2));
            y3 = 2 * ((testBeacons.get(2).getOriginX() - testBeacons.get(1).getOriginX()) * (testBeacons.get(1).getOriginY() - testBeacons.get(0).getOriginY()) - (testBeacons.get(1).getOriginX() - testBeacons.get(0).getOriginX()) * (testBeacons.get(2).getOriginY() - testBeacons.get(1).getOriginY()));
            y = (y1 - y2) / y3;

            /*double w, z, x, y, y2;
            w = Math.pow(distances.get(0), 2) - Math.pow(distances.get(1), 2) - Math.pow(testBeacons.get(0).getX(), 2) - Math.pow(testBeacons.get(0).getY(), 2) + Math.pow(testBeacons.get(1).getX(), 2) + Math.pow(testBeacons.get(1).getY(), 2);
            z = Math.pow(distances.get(1), 2) - Math.pow(distances.get(2), 2) - Math.pow(testBeacons.get(1).getX(), 2) - Math.pow(testBeacons.get(1).getY(), 2) + Math.pow(testBeacons.get(2).getX(), 2) + Math.pow(testBeacons.get(2).getY(), 2);
            x = (w * (testBeacons.get(2).getY() - testBeacons.get(1).getY()) - z * (testBeacons.get(1).getY() - testBeacons.get(0).getY())) / (2 * ((testBeacons.get(1).getX() - testBeacons.get(0).getX()) * (testBeacons.get(2).getX() - testBeacons.get(1).getY()) - (testBeacons.get(2).getX() - testBeacons.get(1).getX()) * (testBeacons.get(1).getY() - testBeacons.get(0).getY())));
            y = (w - 2 * x * (testBeacons.get(1).getX() - testBeacons.get(0).getX())) / (2 * (testBeacons.get(1).getY() - testBeacons.get(0).getY()));
            y2 = (z - 2 * x * (testBeacons.get(2).getX() - testBeacons.get(1).getX())) / (2 * (testBeacons.get(2).getX() - testBeacons.get(1).getY()));
            y = (y + y2) / 2;*/

            user.x = (int) (x * mapInfo.getScaleX());
            user.y = (int) (y * mapInfo.getScaleY());
            Log.d("test", String.valueOf(user.x) + ", " + String.valueOf(user.y));
        }
    }

    public double calculateBeaconDistance(Beacon beacon) {
        if(beacon.getRssi() == 0) {
            return -1.0;
        }

        double ratio = (double) beacon.getRssi() * 1.0 / ((double) -74.0);

        if(ratio < 1.0) {
            return Math.pow(ratio, 10) * M_TO_PX;
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy * M_TO_PX;
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