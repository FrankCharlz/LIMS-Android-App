package com.mj.lims;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApp.log("signed in: "+Remember.getBoolean(Constants.SIGNED_IN, false));

        if(Remember.getBoolean(Constants.SIGNED_IN, false)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            //go to login
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();

    }

}
