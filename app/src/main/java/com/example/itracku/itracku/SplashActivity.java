package com.example.itracku.itracku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Arnm on 2016-10-21.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_TIME = 3000;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);

        ImageView image = (ImageView) findViewById(R.id.splash_circle);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        image.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }


}
