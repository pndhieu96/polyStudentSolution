package com.example.hieudev.polystudentsolution.Dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocBaiTap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class BaiTapDetail extends AppCompatActivity {
    private int position;
    private String tenMon;
    private Toolbar toolbarTop;
    private TextView mTitle, txtDetailND, tvNgay, tvGio;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_tap_detail);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("POSITION");
            tenMon = extras.getString("TENMON");
        }
        toolbarTop = (Toolbar) findViewById(R.id.toolbar_baitap_top);
        mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_baitap_title);
        txtDetailND = (TextView) findViewById(R.id.textViewBaiTapDetailND);
        tvNgay = (TextView) findViewById(R.id.textViewBaiTapNgay);
        tvGio = (TextView) findViewById(R.id.textViewBaiTapGio);
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        RealmList<MonDangHocBaiTap> monDangHocBaiTaps = monDangHocs.get(0).monDangHocBaiTap;
        mTitle.setText(monDangHocBaiTaps.get(position).getTieude());
        txtDetailND.setText(monDangHocBaiTaps.get(position).getNoiDung());
        tvNgay.setText(monDangHocBaiTaps.get(position).getDealineNgay()+"/"+monDangHocBaiTaps.get(position).getDealineThang()+"/"
                +monDangHocBaiTaps.get(position).getDealineNam());
        tvGio.setText(monDangHocBaiTaps.get(position).getDealineGio()+":"+monDangHocBaiTaps.get(position).getDealinePhut());
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