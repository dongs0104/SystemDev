package com.example.itracku.itracku;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import static com.example.itracku.itracku.R.anim.splash;

/**
 * Created by Arnm on 2016-10-21.
 * modified by Dong on 2016-11-28.
 */

public class SplashActivity extends Activity {
    VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        vv = (VideoView) findViewById(R.id.videoView);
        Uri path = Uri.parse("android.resource://"+ getPackageName() +"/"+ R.raw.main);
        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) vv.getLayoutParams();
        params.width =  metrics.widthPixels ;
        params.height = metrics.heightPixels ;
        params.leftMargin = 0;
        vv.setLayoutParams(params);
        vv.setVideoURI(path);
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        });
        vv.start();
    }
}