package com.example.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceData {
    public static int deviceWidthDpi = 0;
    public static int deviceHeightDpi = 0;
    public static int deviceWidthPx = 0;
    public static int deviceHeightPx = 0;
 
    public static void initDeviceScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics(); 
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
         
        //setDeviceWidthPx(metrics.widthPixels); 
        //setDeviceHeightPx(metrics.heightPixels); 
         
        //px to dp
        //setDeviceWidthDpi(pxToDp(context, getDeviceWidthPx()));
        //setDeviceHeightDpi(pxToDp(context, getDeviceHeightPx()));
 
        return;
    }
     
    public static int pxToDp(Context context, int px) {
        DisplayMetrics metrics = new DisplayMetrics(); 
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int)((double)px * (metrics.densityDpi / 160f));
    }
     
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics metrics = new DisplayMetrics(); 
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int)((double)dp / (metrics.densityDpi / 160f));
    }
 
}
