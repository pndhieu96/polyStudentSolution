package com.example.hieudev.polystudentsolution.Dialog;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hieudev.polystudentsolution.Adapter.customDiemDanhAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DiemDanhDialog extends AppCompatActivity {
    ListView lvDiemDanh;
    Realm realm;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh_dialog);
        lvDiemDanh = (ListView) findViewById(R.id.lvDiemDanh);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Điểm danh");

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
        ArrayList<DiemDanhThongKe> diemDanhThongKes1 = new ArrayList<DiemDanhThongKe>();
        for(int i=0; i<diemDanhThongKes.size(); i++){
            diemDanhThongKes1.add(diemDanhThongKes.get(i));
        }
        lvDiemDanh.setAdapter(new customDiemDanhAdapter(DiemDanhDialog.this.getApplicationContext(), diemDanhThongKes1));

        lvDiemDanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(DiemDanhDialog.this, DiemDanhChiTiet.class);
                bundle.putInt("DIEMDANH", position);
                intent.putExtra("diemdanhcount", bundle);
                startActivity(intent);
            }
        });
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
