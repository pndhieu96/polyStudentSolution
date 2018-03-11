package com.example.hieudev.polystudentsolution.Dialog;

import android.content.Intent;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hieudev.polystudentsolution.Adapter.customDiemDanhAdapter;
import com.example.hieudev.polystudentsolution.Adapter.customDiemTheoKyAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKeTheoKy;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DiemTheoKyDialog extends AppCompatActivity {
    ListView lvDTK;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_theo_ky_dialog);

        lvDTK = (ListView) findViewById(R.id.lvDDTK);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Điểm theo kỳ");

        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        RealmResults<DiemThongKeTheoKy> diemThongKeTheoKys = realm.where(DiemThongKeTheoKy.class).findAll();
        ArrayList<DiemThongKeTheoKy> diemThongKeTheoKys1 = new ArrayList<DiemThongKeTheoKy>();
        for(int i=0; i<diemThongKeTheoKys.size(); i++){
            diemThongKeTheoKys1.add(diemThongKeTheoKys.get(i));
        }
        lvDTK.setAdapter(new customDiemTheoKyAdapter(DiemTheoKyDialog.this.getApplicationContext(), diemThongKeTheoKys1));

        lvDTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Intent intentDTK = new Intent(DiemTheoKyDialog.this, DiemTheoKyChiTietDialog.class);
                bundle.putInt("DIEMTHEOKY", position);
                intentDTK.putExtra("diemTheoKyCount", bundle);
                startActivity(intentDTK);
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
