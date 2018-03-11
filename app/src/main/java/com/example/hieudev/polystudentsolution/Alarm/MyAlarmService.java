package com.example.hieudev.polystudentsolution.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocBaiTap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MyAlarmService extends Service{
    private NotificationCompat.Builder builder;
    String tenMon;
    int id;
    Realm realm;
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        builder = new NotificationCompat.Builder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        Bundle extras = intent.getExtras();
        if(extras != null) {
            tenMon = extras.getString("TENMON");
            id = extras.getInt("ID");
        }
        System.out.println("alarmServiceid " +id);
        RealmResults<MonDangHocBaiTap> monDangHocBaiTaps = realm.where(MonDangHocBaiTap.class).equalTo("id", id).findAll();
        MonDangHocBaiTap monDangHocBaiTap = monDangHocBaiTaps.get(0);
        if(monDangHocBaiTap.getCheckThongBao()){
            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(monDangHocBaiTap.getTieude())
                    .setContentInfo(tenMon)
                    .setContentText("Deadline: " + monDangHocBaiTap.getDealineNgay() + "/"
                            + monDangHocBaiTap.getDealineThang() + "/"
                            + monDangHocBaiTap.getDealineNam() + " "
                            + monDangHocBaiTap.getDealineGio() + ":"
                            + monDangHocBaiTap.getDealinePhut())
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(id, builder.build());
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<MonDangHocBaiTap> monDangHocBaiTaps = realm.where(MonDangHocBaiTap.class).equalTo("id", id).findAll();
                MonDangHocBaiTap monDangHocBaiTap = monDangHocBaiTaps.get(0);
                monDangHocBaiTap.setCheckThongBao(false);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
