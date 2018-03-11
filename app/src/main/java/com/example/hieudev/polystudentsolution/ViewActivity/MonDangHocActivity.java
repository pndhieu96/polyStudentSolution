package com.example.hieudev.polystudentsolution.ViewActivity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hieudev.polystudentsolution.Adapter.MonDangHocAdapter;
import com.example.hieudev.polystudentsolution.R;

public class MonDangHocActivity extends AppCompatActivity {
    private static String tenMon;
    private TabLayout tlMonDangHoc;
    private ViewPager vpMonDangHoc;
    private MonDangHocAdapter monDangHocAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_dang_hoc);
        Intent i = getIntent();
        tenMon = i.getStringExtra("TENMON");
        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("viewpager_position");
        }

        tlMonDangHoc = (TabLayout) findViewById(R.id.tabMonDangHoc);
        vpMonDangHoc = (ViewPager) findViewById(R.id.viewpagerMonDangHoc);

        monDangHocAdapter = new MonDangHocAdapter(getSupportFragmentManager());
        vpMonDangHoc.setAdapter(monDangHocAdapter);
        tlMonDangHoc.setupWithViewPager(vpMonDangHoc);
        vpMonDangHoc.setCurrentItem(position);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(tenMon);
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

    public static String getTenMon() {
        return tenMon;
    }
}
