package com.example.hieudev.polystudentsolution.Dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.hieudev.polystudentsolution.Alarm.CreateAlarm;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocBaiTap;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ChinhSuaBaiTap extends Activity {
    private Button btnChinhSua, btnXoa;
    private int position;
    private String tenMon;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chinh_sua_bai_tap);
        btnChinhSua = (Button) findViewById(R.id.buttonBaiTapChinhSua);
        btnXoa = (Button) findViewById(R.id.buttonBaiTapXoa);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("POSITION");
            tenMon = extras.getString("TENMON");
        }
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChinhSuaBaiTap.this);
                alertDialogBuilder.setMessage("Bạn có muốn xóa ghi nhớ này ?");
                alertDialogBuilder.setPositiveButton("Đồng ý",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                                RealmList<MonDangHocBaiTap> monDangHocBaiTaps = monDangHocs.get(0).monDangHocBaiTap;
                                int monid = monDangHocBaiTaps.get(position).getId();
                                CreateAlarm createAlarm = new CreateAlarm(getApplicationContext());
                                createAlarm.delAlarm(monid);

                                realm.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                                        RealmList<MonDangHocBaiTap> monDangHocBaiTaps = monDangHocs.get(0).monDangHocBaiTap;
                                        monDangHocBaiTaps.get(position).deleteFromRealm();
                                    }
                                }, new Realm.Transaction.OnSuccess() {
                                    @Override
                                    public void onSuccess() {
                                        Intent go = new Intent(ChinhSuaBaiTap.this,MonDangHocActivity.class);
                                        go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        go.putExtra("TENMON", tenMon);
                                        go.putExtra("viewpager_position", 0);
                                        startActivity(go);
                                        ChinhSuaBaiTap.this.finish();
                                    }
                                });
                            }
                        });
                alertDialogBuilder.setNegativeButton("Không",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ChinhSuaBaiTap.this,ChinhSuaBaiTapDetail.class);
                go.putExtra("POSITION", position);
                go.putExtra("TENMON", tenMon);
                go.putExtra("viewpager_position", 0);
                startActivity(go);
                ChinhSuaBaiTap.this.finish();
            }
        });
    }
}
