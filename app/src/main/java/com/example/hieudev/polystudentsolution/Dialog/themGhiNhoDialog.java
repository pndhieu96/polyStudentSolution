package com.example.hieudev.polystudentsolution.Dialog;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class themGhiNhoDialog extends AppCompatActivity {
    private com.neopixl.pixlui.components.edittext.EditText edtTitle, edtDes;
    private Button btnLuu, btnThoat;
    private String title;
    private String tenMon;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ghi_nho_dialog);
        Intent i = getIntent();
        tenMon = i.getStringExtra("TENMON");
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
        edtTitle = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextTitle);
        edtDes = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextDes);
        btnLuu = (Button) findViewById(R.id.buttonGhiNhoLuu);
        btnThoat = (Button) findViewById(R.id.buttonGhiNhoThoat);

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
                    Intent go = new Intent(themGhiNhoDialog.this,MonDangHocActivity.class);
                    go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    go.putExtra("TENMON",tenMon);
                    go.putExtra("viewpager_position", 1);
                    startActivity(go);
                    themGhiNhoDialog.this.finish();
                }else {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            String title = edtTitle.getText().toString();
                            String des = edtDes.getText().toString();
                            RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                            MonDangHocGhiNho monDangHocGhiNho = realm.createObject(MonDangHocGhiNho.class);
                            monDangHocGhiNho.setTieude(title);
                            monDangHocGhiNho.setNoiDung(des);
                            monDangHocs.get(0).monDangHocGhiNho.add(monDangHocGhiNho);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Intent go = new Intent(themGhiNhoDialog.this, MonDangHocActivity.class);
                            go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            go.putExtra("TENMON",tenMon);
                            go.putExtra("viewpager_position", 1);
                            startActivity(go);
                            themGhiNhoDialog.this.finish();
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
