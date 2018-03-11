package com.example.hieudev.polystudentsolution.Dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class GhiNhoDetail extends AppCompatActivity {
    private int position;
    private String tenMon;
    private Toolbar toolbarTop;
    private TextView mTitle, txtDetailND;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghi_nho_detail);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("POSITION");
            tenMon = extras.getString("TENMON");
        }
        toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        txtDetailND = (TextView) findViewById(R.id.textViewDetailND);
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        List<MonDangHocGhiNho> MonDangHocGhiNhoList = new ArrayList<MonDangHocGhiNho>();
        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        RealmList<MonDangHocGhiNho> monDangHocGhiNhos = monDangHocs.get(0).monDangHocGhiNho;
        mTitle.setText(monDangHocGhiNhos.get(position).getTieude());
        txtDetailND.setText(monDangHocGhiNhos.get(position).getNoiDung());

        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
