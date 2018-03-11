package com.example.hieudev.polystudentsolution.Dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ChinhSuaGhiNho extends Activity {
    private Button btnChinhSua, btnXoa;
    private int position;
    private String tenMon;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chinh_sua_ghi_nho);
        btnChinhSua = (Button) findViewById(R.id.buttonGhiNhoChinhSua);
        btnXoa = (Button) findViewById(R.id.buttonGhiNhoXoa);
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChinhSuaGhiNho.this);
                alertDialogBuilder.setMessage("Bạn có muốn xóa ghi nhớ này ?");
                alertDialogBuilder.setPositiveButton("Đồng ý",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    List<MonDangHocGhiNho> MonDangHocGhiNhoList = new ArrayList<MonDangHocGhiNho>();
                                    RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                                    RealmList<MonDangHocGhiNho> monDangHocGhiNhos = monDangHocs.get(0).monDangHocGhiNho;
                                    monDangHocGhiNhos.get(position).deleteFromRealm();
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    Intent go = new Intent(ChinhSuaGhiNho.this,MonDangHocActivity.class);
                                    go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    go.putExtra("TENMON", tenMon);
                                    go.putExtra("viewpager_position", 1);
                                    startActivity(go);
                                    ChinhSuaGhiNho.this.finish();
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
                Intent go = new Intent(ChinhSuaGhiNho.this,ChinhSuaGhiNhoDetail.class);
                go.putExtra("POSITION", position);
                go.putExtra("TENMON", tenMon);
                go.putExtra("viewpager_position", 1);
                startActivity(go);
                ChinhSuaGhiNho.this.finish();
            }
        });
    }
}
