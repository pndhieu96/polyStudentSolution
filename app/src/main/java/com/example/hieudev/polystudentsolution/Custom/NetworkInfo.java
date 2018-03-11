package com.example.hieudev.polystudentsolution.Custom;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkInfo {
    public static android.net.NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context) {
        android.net.NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isConnected();
    }

    public static boolean isConnectedWifi(Context context) {
        android.net.NetworkInfo info = getNetworkInfo(context);
        if (info != null && info.isConnected() && info.getType() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isConnectedMobile(Context context) {
        android.net.NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isConnected() && info.getType() == 0;
    }
}
