package com.mj.lims;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApp.log("on create launcher");

        startActivity(new Intent(this, LoginActivity.class));
        finish();

        if(Remember.getBoolean(Constants.FIRST_LAUNCH, true)) {
            Remember.putBoolean(Constants.FIRST_LAUNCH, false);
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.log("exitung launcher activity");
    }
}
