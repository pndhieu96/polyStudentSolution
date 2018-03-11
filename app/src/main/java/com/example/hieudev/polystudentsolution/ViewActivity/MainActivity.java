package com.example.hieudev.polystudentsolution.ViewActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieudev.polystudentsolution.Adapter.PagerAdapter;
import com.example.hieudev.polystudentsolution.Custom.NetworkInfo;
import com.example.hieudev.polystudentsolution.Dialog.LoginGoogleDialog;
import com.example.hieudev.polystudentsolution.Dialog.thietLapDialog;
import com.example.hieudev.polystudentsolution.Dialog.thongTinHocVienDialog;
import com.example.hieudev.polystudentsolution.Fragment.DeadlineFragment;
import com.example.hieudev.polystudentsolution.Fragment.HomeFragment;
import com.example.hieudev.polystudentsolution.Fragment.LichHocFragment;
import com.example.hieudev.polystudentsolution.Fragment.ThongTinFragment;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoMon;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.ThongTin;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager homeViewPager;
    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.calendar,
            R.drawable.deadlines,
            R.drawable.file
    };
    private TextView tenNguoiDung, emailNguoiDung;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tenNguoiDung = (TextView) headerView.findViewById(R.id.tenNguoiDung);
        emailNguoiDung = (TextView) headerView.findViewById(R.id.emailNguoiDung);

        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("viewpager_position");
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
        RealmResults<ThongTin> thongTins = realm.where(ThongTin.class).findAll();
        if(thongTins.size()>0) {
            tenNguoiDung.setText(thongTins.get(0).getHo() + " " +thongTins.get(0).getTenDem() + " " + thongTins.get(0).getTen());
            emailNguoiDung.setText(thongTins.get(0).getEmail());
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(homeViewPager);
        homeViewPager.setCurrentItem(position);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(homeViewPager);
        setupTabIcons();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getMonDangHoc(realm);
        checkMonDangHoc(realm);
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "");
        adapter.addFragment(new LichHocFragment(), "");
        adapter.addFragment(new DeadlineFragment(), "");
        adapter.addFragment(new ThongTinFragment(), "");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miCompose) {
            if(NetworkInfo.isConnected(getApplicationContext())){
                Intent i = new Intent(MainActivity.this.getApplicationContext(), ChooseCampusActivity.class);
                MainActivity.this.startActivity(i);
            }else{
                Toast.makeText(getApplicationContext(),"Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_caNhan:
                new thongTinHocVienDialog(MainActivity.this).show();
                break;
            case R.id.nav_thoatTK:
                new AlertDialog.Builder(MainActivity.this).setMessage("Bạn có chắc chắn muốn đăng xuất ?").setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CookieSyncManager.createInstance(getApplicationContext());
                        CookieManager.getInstance().removeAllCookie();
                        SharedPreferences settings = getApplicationContext().getSharedPreferences("mode", getApplicationContext().MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent i = new Intent(MainActivity.this,ChooseCampusActivity.class);
                        startActivity(i);
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();

                break;
            case R.id.nav_thietLap:
                new thietLapDialog(MainActivity.this).show();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getMonDangHoc(Realm realm){
        RealmResults<DiemTheoMon> diemTheoMons = realm.where(DiemTheoMon.class).contains("trangThai", "Đang học").findAll();
        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).findAll();
        if(diemTheoMons.size() > 0){
            if(monDangHocs.size() >0){
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int thu;
                        RealmResults<DiemTheoMon> diemTheoMon1s = realm.where(DiemTheoMon.class).contains("trangThai", "Đang học").findAll();
                        RealmResults<MonDangHoc> monDangHoc1s = realm.where(MonDangHoc.class).findAll();
                        if(monDangHoc1s.size() > 0){
                            for (int i = 0; i < diemTheoMon1s.size(); i++) {
                                thu = 0;
                                for(int j=0; j<monDangHoc1s.size(); j++){
                                    if(diemTheoMon1s.get(i).getMon().equals(monDangHoc1s.get(j).getTenMon())){
                                        thu = -1;
                                    }
                                }
                                if(thu != -1){
                                    MonDangHoc monDangHoc = realm.createObject(MonDangHoc.class);
                                    monDangHoc.setTenMon(diemTheoMon1s.get(i).getMon());
                                }
                            }
                        }
                    }
                });
            }else {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<DiemTheoMon> diemTheoMon1s = realm.where(DiemTheoMon.class).contains("trangThai", "Đang học").findAll();
                        for (int i = 0; i < diemTheoMon1s.size(); i++) {
                            MonDangHoc monDangHoc = realm.createObject(MonDangHoc.class);
                            monDangHoc.setTenMon(diemTheoMon1s.get(i).getMon());
                        }
                    }
                });
            }
        }
    }

    private void checkMonDangHoc(Realm realm){
        RealmResults<MonDangHoc> checkMonDangHocs = realm.where(MonDangHoc.class).findAll();
        if(checkMonDangHocs.size()>0){
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<MonDangHoc> checkMonDangHoc1s = realm.where(MonDangHoc.class).findAll();
                    RealmResults<DiemTheoMon> checkDiemTheoMon1s = realm.where(DiemTheoMon.class).contains("trangThai", "Đang học").findAll();
                    for (int i = 0; i < checkMonDangHoc1s.size(); i++) {
                        int checkMonDangHoc = 0;
                        String tenMOn = checkMonDangHoc1s.get(i).getTenMon();
                        if (checkDiemTheoMon1s.size() > 0) {
                            for (int j = 0; j < checkDiemTheoMon1s.size(); j++) {
                                if (tenMOn.equals(checkDiemTheoMon1s.get(j).getMon())) {
                                    checkMonDangHoc++;
                                }
                            }
                        }
                        if(checkMonDangHoc == 0){
                            checkMonDangHoc1s.get(i).setCheckDangHoc(false);
                        }else{
                            checkMonDangHoc1s.get(i).setCheckDangHoc(true);
                        }
                    }
                }
            });
        }
    }
}
