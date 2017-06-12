package com.mj.lims;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Frank on 21-May-17.
 */

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void openMap(String url) {
        Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
    }




}