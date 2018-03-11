package com.example.hieudev.polystudentsolution.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class CreateAlarm {
    Context context;

    public CreateAlarm(Context context) {
        this.context = context;
    }

    public void addAlarm(int ngay, int thang, int nam, int gio, int phut, int alarmId, String tenMon){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, thang-1);
        calendar.set(Calendar.YEAR, nam);
        calendar.set(Calendar.DAY_OF_MONTH, ngay);

        calendar.set(Calendar.HOUR_OF_DAY, gio);
        calendar.set(Calendar.MINUTE, phut);
        calendar.set(Calendar.SECOND, 0);

        Intent myIntent = new Intent(this.context, MyReceiver.class);
        myIntent.putExtra("TENMON", tenMon);
        myIntent.putExtra("ID", alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, alarmId, myIntent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    public void delAlarm(int alarmId){
        ((AlarmManager) this.context.getSystemService(ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this.context, alarmId, new Intent(this.context, MyReceiver.class), 0));
    }
}
