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
import com.example.hieudev.polystudentsolution.Adapter.customDiemTheoKyChiTietAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanh;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoKy;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKeTheoKy;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DiemTheoKyChiTietDialog extends AppCompatActivity {
    ListView lvDTKCT;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_theo_ky_chi_tiet_dialog);
        lvDTKCT = (ListView) findViewById(R.id.lvDTKCT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Điểm theo kỳ chi tiết");

        Intent intentDTK = getIntent();
        Bundle diemTheoKy = intentDTK.getBundleExtra("diemTheoKyCount");
        int position = diemTheoKy.getInt("DIEMTHEOKY");
        ArrayList<DiemTheoKy> diemTheoKies = new ArrayList<DiemTheoKy>();
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        RealmResults<DiemThongKeTheoKy> diemThongKeTheoKies = realm.where(DiemThongKeTheoKy.class).findAll();
        for(int i = 0; i < diemThongKeTheoKies.get(position).diemTheoKy.size(); i++){
            diemTheoKies.add(diemThongKeTheoKies.get(position).diemTheoKy.get(i));
        }
        lvDTKCT.setAdapter(new customDiemTheoKyChiTietAdapter(DiemTheoKyChiTietDialog.this, diemTheoKies));
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
