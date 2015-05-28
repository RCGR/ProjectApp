package com.coldfushion.MainProjectApplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by ceesjan on 28-5-2015.
 */
public class MyJavaScriptInterface {
    private Context ctx;
    public String PlaceID = "";


    MyJavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    @android.webkit.JavascriptInterface
    public void getHTML(String html) {
        PlaceID = html;
    }


    public String getPlaceID() {
        return PlaceID;
    }
}
