package com.example.hieudev.polystudentsolution.ViewActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.hieudev.polystudentsolution.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SplashScreen extends Activity{

    private static final int SPLASHSCREEN_TIME_OUT = 1500;
    private SharedPreferences pre;
    private String useKeyString;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        pre = this.getApplicationContext().getSharedPreferences("mode", this.getApplication().MODE_PRIVATE);
        useKeyString = pre.getString("user", "error");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!useKeyString.equals("error")){
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashScreen.this, ChooseCampusActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASHSCREEN_TIME_OUT);
    }
}
