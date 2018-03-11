package com.example.hieudev.polystudentsolution.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import com.example.hieudev.polystudentsolution.ViewActivity.MainActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SetThongTinMonDangHoc extends Activity {

    EditText edtDeadlineDMT, edtDeadlineSBCT;
    Button btnDeadlineTTTOk, btnDeadlineTTTC, btnDeadlineXoa;
    String tenMon;
    float diemMucTieu;
    int buoiChoPhep;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_thongtin_mondanghoc_dialog);

        edtDeadlineDMT = (EditText) findViewById(R.id.editTextMucTieu);
        edtDeadlineSBCT = (EditText) findViewById(R.id.editTextBuoiChoPhep);
        btnDeadlineTTTOk = (Button) findViewById(R.id.buttonMonDangHocOk);
        btnDeadlineTTTC = (Button) findViewById(R.id.buttonMonDangHocThoat);
        btnDeadlineXoa = (Button) findViewById(R.id.buttonMonDangHocXoa);

        btnDeadlineXoa.setVisibility(View.GONE);

        try{
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        Intent i = getIntent();
        tenMon = i.getStringExtra("TENMON");

        setTitle(tenMon);
        setTitleColor(Color.parseColor("#ff5722"));

        final RealmResults<MonDangHoc> monDangHoc1s = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        if(monDangHoc1s.size()>0){
            if(monDangHoc1s.get(0).getDiemMucTieu()>0) {
                edtDeadlineDMT.setText(monDangHoc1s.get(0).getDiemMucTieu() + "");
            }
            if(monDangHoc1s.get(0).getDiemMucTieu()>0) {
                edtDeadlineSBCT.setText(monDangHoc1s.get(0).getSoBuoiChoPhep() + "");
            }
            if(!monDangHoc1s.get(0).getCheckDangHoc()){
                btnDeadlineXoa.setVisibility(View.VISIBLE);
            }
        }

        btnDeadlineTTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetThongTinMonDangHoc.this.finish();
            }
        });

        btnDeadlineTTTOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!edtDeadlineDMT.getText().toString().isEmpty()){
                diemMucTieu = Float.parseFloat(edtDeadlineDMT.getText().toString());
            }
            if(!edtDeadlineSBCT.getText().toString().isEmpty()){
                buoiChoPhep = Integer.parseInt(edtDeadlineSBCT.getText().toString());
            }
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                    monDangHocs.get(0).setDiemMucTieu(diemMucTieu);
                    monDangHocs.get(0).setSoBuoiChoPhep(buoiChoPhep);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Intent go = new Intent(SetThongTinMonDangHoc.this,MainActivity.class);
                    go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    go.putExtra("viewpager_position", 2);
                    startActivity(go);
                    SetThongTinMonDangHoc.this.finish();
                }
            });
            }
        });

        btnDeadlineXoa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                        monDangHocs.get(0).deleteFromRealm();
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Intent go = new Intent(SetThongTinMonDangHoc.this,MainActivity.class);
                        go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        go.putExtra("viewpager_position", 2);
                        startActivity(go);
                        SetThongTinMonDangHoc.this.finish();
                    }
                });
            }
        });
    }
}
