package com.example.hieudev.polystudentsolution.Dialog;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hieudev.polystudentsolution.Adapter.customDiemDanhAdapter;
import com.example.hieudev.polystudentsolution.Adapter.customDiemTheoMonAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoKy;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoMon;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DiemTheoMonDialog extends AppCompatActivity {
    ListView lvDTM;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_theo_mon);
        lvDTM = (ListView) findViewById(R.id.lvDTM);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Điểm theo môn");

        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        RealmResults<DiemTheoMon> diemTheoMons = realm.where(DiemTheoMon.class).findAll();
        ArrayList<DiemTheoMon> diemTheoMons1 = new ArrayList<DiemTheoMon>();
        for(int i=0; i<diemTheoMons.size(); i++){
            diemTheoMons1.add(diemTheoMons.get(i));
        }
        lvDTM.setAdapter(new customDiemTheoMonAdapter(DiemTheoMonDialog.this, diemTheoMons1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
