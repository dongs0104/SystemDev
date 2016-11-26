package com.example.itracku.itracku;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements RangeNotifier, BeaconConsumer {
    private MapInfo mapInfo;
    private TestBeaconList testBeaconList;
    private TestClassRoomList testClassRoomList;
    private ImageView mainMap;
    private CanvasView mainCanvas;
    private BackPressCloseHandler mCloseHandler;
    private ImageView drawBtn;
    private BeaconManager mBeaconManager;
    private Region mRegion = new Region("ITrackU", Identifier.parse("50CF90B0-0C8F-11E4-9191-0800200C9A66"), null, null);
    //private Region mRegion = new Region("ITrackU", Identifier.parse("617E8096-BAB7-43F3-BF96-3FD6F26D67B1"), null, null);
    /*자동 완성을 위해 사용*/
    private AutoCompleteTextView textView;
    String[] rooms={"329", "330", "331", "332"};
    /*이동 버튼*/
    private Button moveButton;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        mBeaconManager.setForegroundScanPeriod(300);
        mBeaconManager.setForegroundBetweenScanPeriod(300);
        mBeaconManager.setBackgroundScanPeriod(300);
        mBeaconManager.setBackgroundBetweenScanPeriod(300);
        mBeaconManager.bind(this);
        mCloseHandler = new BackPressCloseHandler(this);
        mainMap = (ImageView) findViewById(R.id.main_map);
        mainCanvas = (CanvasView) findViewById(R.id.main_canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        mapInfo = new MapInfo(bitmap);
        mainMap.setImageBitmap(mapInfo.getMap());
        mainCanvas.setMapInfo(mapInfo);
        /* 자동완성 기능 추가 */
        textView = (AutoCompleteTextView) findViewById(R.id.SearchText);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, rooms);
        textView.setAdapter(adapter);
        /*버튼 이벤트 설정*/
        moveButton = (Button) findViewById(R.id.button);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textView.getText().toString().length() == 3) {
                    mainCanvas.setRoomSearchToggle(true);
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View v = (View) findViewById(R.id.main_map);
        mapInfo.initialize(v.getWidth(), v.getHeight());

        testBeaconList = new TestBeaconList();
        testClassRoomList = new TestClassRoomList();
        testBeaconList.init(mapInfo.getScaleX(), mapInfo.getScaleY());
        testClassRoomList.init(mapInfo.getScaleX(), mapInfo.getScaleY());
        mainCanvas.setTestBeacon(testBeaconList);
        mainCanvas.setTestClassRoom(testClassRoomList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBeaconManager.isBound(this)){
            mBeaconManager.setBackgroundMode(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mBeaconManager.isBound(this)){
            mBeaconManager.setBackgroundMode(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeaconManager.unbind(this);
    }

    @Override
    public void onBackPressed() {
        mCloseHandler.onBackPressed();
    }

    @Override
    public void onBeaconServiceConnect() {
        try {
            mBeaconManager.startRangingBeaconsInRegion(mRegion);
        }catch(RemoteException e) {
            e.printStackTrace();
        }

        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Beacon> sortedBeacons = new ArrayList<Beacon>(beacons);
                Collections.sort(sortedBeacons, new Comparator<Beacon>() {

                    @Override
                    public int compare(Beacon beacon0, Beacon beacon1) {
                        return new Double(beacon0.getDistance()).compareTo(new Double(beacon1.getDistance()));
                    }
                });

                //mainCanvas.setBeacons();
                mainCanvas.drawUserLocation(sortedBeacons);
                if(textView.getText().toString().length() == 3)
                    mainCanvas.drawClassRoomPath(textView.getText().toString());
                mainCanvas.invalidate();
            }
        });
    }

    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Activity activity;

        public BackPressCloseHandler(Activity activity) {
            this.activity = activity;
        }

        public void onBackPressed() {
            if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(activity, "\'뒤로\' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                SlidingDrawer drawer = (SlidingDrawer)findViewById(R.id.slide);
                drawer.animateClose();
                return;
            }

            if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                activity.finish();
            }
        }
    }
}