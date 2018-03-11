package com.example.hieudev.polystudentsolution.Dialog;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hieudev.polystudentsolution.Adapter.customDiemDanhChiTietAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanh;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DiemDanhChiTiet extends AppCompatActivity {
    ListView lvDDCT;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh_chi_tiet);
        lvDDCT = (ListView) findViewById(R.id.lvDiemDanhChiTiet);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Điểm danh chi tiết");

        Intent intent = getIntent();
        Bundle diemDanhCount = intent.getBundleExtra("diemdanhcount");
        int position = diemDanhCount.getInt("DIEMDANH");
        ArrayList<DiemDanh> diemDanhs = new ArrayList<DiemDanh>();
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        RealmResults<DiemDanhThongKe> diemDanhThongKes = realm.where(DiemDanhThongKe.class).findAll();
        diemDanhThongKes.get(position);
        for(int i = 0; i <diemDanhThongKes.get(position).diemDanh.size(); i++){
            diemDanhs.add(diemDanhThongKes.get(position).diemDanh.get(i));
        }
        lvDDCT.setAdapter(new customDiemDanhChiTietAdapter(DiemDanhChiTiet.this, diemDanhs));
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
