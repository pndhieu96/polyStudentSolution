package com.example.hieudev.polystudentsolution.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieudev.polystudentsolution.Adapter.customHomeAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;
import com.example.hieudev.polystudentsolution.RealmObject.ThongTin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class HomeFragment extends Fragment{
    Realm realm;
    RecyclerView homeRecyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.home_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.HomeRecyclerView);
        homeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHome = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        homeRecyclerView.setLayoutManager(layoutManagerHome);

        try{
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String tomorrowAsString = df.format(tomorrow);

        RealmResults<ThongTin> thongTins = realm.where(ThongTin.class).findAll();
        RealmResults<DiemThongKe> diemThongKes = realm.where(DiemThongKe.class).findAll();
        List<DiemThongKe> diemThongKes1 = new ArrayList<DiemThongKe>();
        RealmResults<LichHoc> lichHocRs = realm.where(LichHoc.class).findAll();
        RealmResults<DiemDanhThongKe> diemDanhThongKess = realm.where(DiemDanhThongKe.class).findAll();

        ArrayList<LichHoc> lichHocHomNay = new ArrayList<LichHoc>();
        ArrayList<LichHoc> lichHocNgayMai = new ArrayList<LichHoc>();
        ArrayList<DiemDanhThongKe> diemDanhVang = new ArrayList<DiemDanhThongKe>();

        if (diemThongKes.size() > 0) {
            for (int i = 0; i < diemThongKes.size(); i++) {
                diemThongKes1.add(diemThongKes.get(i));
            }
        }
        if (lichHocRs.size() > 0) {
            RealmResults<LichHoc> lichHocHn = realm.where(LichHoc.class).contains("ngay", date).findAll();
            RealmResults<LichHoc> lichHocNm = realm.where(LichHoc.class).contains("ngay", tomorrowAsString).findAll();

            if(lichHocHn.size() >0){
                for(int i = 0; i < lichHocHn.size(); i++){
                    lichHocHomNay.add(lichHocHn.get(i));
                }
            }

            if(lichHocNm.size() >0){
                for(int i = 0; i < lichHocNm.size(); i++){
                    lichHocNgayMai.add(lichHocNm.get(i));
                }
            }
        }
        if (diemDanhThongKess.size() > 0) {
            RealmResults<DiemDanhThongKe> diemDanhThongKes = realm.where(DiemDanhThongKe.class).greaterThan("buoiVang", 0).findAll();
            if (diemDanhThongKes.size() > 0) {
                for (int i = 0; i < diemDanhThongKes.size(); i++) {
                    diemDanhVang.add(diemDanhThongKes.get(i));
                }
            }
        }

        customHomeAdapter customhomeAdapter = new customHomeAdapter(diemThongKes1, lichHocHomNay, lichHocNgayMai, diemDanhVang, date, tomorrowAsString);
        homeRecyclerView.setAdapter(customhomeAdapter);
    }
}
