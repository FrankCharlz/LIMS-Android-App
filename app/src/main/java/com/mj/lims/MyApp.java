package com.mj.lims;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


/**
 * Created by Frank on 5/6/2016.
 */
public class MyApp  extends Application {

    private static OkHttpClient client;
    private static Gson GSON_INSTANCE;
    private static Context context;

    public static OkHttpClient getClient() {
        return client;
    }

    public static Gson getGson() {
        if (GSON_INSTANCE == null) GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().create();
        return GSON_INSTANCE;
    }

    public static void log(String msg) {
        Log.e("lims", msg);
    }

    public static void toast(Context context, String MSG) {
        Toast.makeText(context, MSG, Toast.LENGTH_SHORT).show();
    }


    public static File getAppFolder() {
        File folder = new File(Environment.getExternalStorageDirectory(), "lims/");

        // Create the storage directory if it does not exist
        if (! folder.exists()){
            if (! folder.mkdirs()){
                MyApp.log("failed to create app directory");
                return null;
            }
        }

        return folder;
    }

    // Method for checking if the device is connected to an internet connection
    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNet = cm.getActiveNetworkInfo();
        boolean connection = activeNet != null && activeNet.isConnectedOrConnecting();
        log("Is app Connected: " + connection + "");
        return connection;
    }

    public static int currentVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        // Use 1/8th of the available memory for this memory cache.
        final int cache_size = 1024 * 1024 * 20; //((int) (Runtime.getRuntime().maxMemory() / 1024))/8;
        log("cache size : "+cache_size);

        Cache cache = new Cache(getCacheDir(), cache_size);

        client = new OkHttpClient.Builder()
                .cache(cache)
                .followRedirects(false)
                .connectTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();

        Remember.init(this, "lims_app_prefs");
    }


}

