package com.example.hieudev.polystudentsolution.Dialog;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class ChinhSuaGhiNhoDetail extends AppCompatActivity {
    private com.neopixl.pixlui.components.edittext.EditText edtTitle, edtDes;
    private Button btnLuu, btnThoat;
    private String title, tieuDe, noiDung;
    private String tenMon;
    private int position;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ghi_nho_detail);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("POSITION");
            tenMon = extras.getString("TENMON");
        }
        setTitle(tenMon);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        edtTitle = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextGhiNhoDetailTitle);
        edtDes = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextGhiNhoDetailDes);
        btnLuu = (Button) findViewById(R.id.buttonGhiNhoDetailLuu);
        btnThoat = (Button) findViewById(R.id.buttonGhiNhoDetailThoat);

        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        RealmList<MonDangHocGhiNho> monDangHocGhiNhos = monDangHocs.get(0).monDangHocGhiNho;
        tieuDe = monDangHocGhiNhos.get(position).getTieude();
        noiDung = monDangHocGhiNhos.get(position).getNoiDung();
        edtTitle.setText(tieuDe);
        edtDes.setText(noiDung);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edtTitle.getText().toString();
                if(title.isEmpty()){
                    Intent go = new Intent(ChinhSuaGhiNhoDetail.this,MonDangHocActivity.class);
                    go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    go.putExtra("TENMON",tenMon);
                    go.putExtra("viewpager_position", 1);
                    startActivity(go);
                    ChinhSuaGhiNhoDetail.this.finish();
                }else {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            String title = edtTitle.getText().toString();
                            String des = edtDes.getText().toString();
                            RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                            RealmList<MonDangHocGhiNho> monDangHocGhiNhos = monDangHocs.get(0).monDangHocGhiNho;
                            monDangHocGhiNhos.get(position).setTieude(title);
                            monDangHocGhiNhos.get(position).setNoiDung(des);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Intent go = new Intent(ChinhSuaGhiNhoDetail.this, MonDangHocActivity.class);
                            go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            go.putExtra("TENMON",tenMon);
                            go.putExtra("viewpager_position", 1);
                            startActivity(go);
                            ChinhSuaGhiNhoDetail.this.finish();
                        }
                    });
                }
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

