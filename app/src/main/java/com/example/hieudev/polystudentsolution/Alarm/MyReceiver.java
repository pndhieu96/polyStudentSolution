package com.example.hieudev.polystudentsolution.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

public class MyReceiver extends BroadcastReceiver{
    String tenMon;
    int id;
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "My Tag");
        mWakeLock.acquire();
        mWakeLock.release();
        Bundle extras = intent.getExtras();
        tenMon = extras.getString("TENMON");
        id = extras.getInt("ID");
        System.out.println("receiverId" + id);
        Intent intent1 = new Intent(context, MyAlarmService.class);
        intent1.putExtra("TENMON", tenMon);
        intent1.putExtra("ID", id);
        context.startService(intent1);
    }
}
