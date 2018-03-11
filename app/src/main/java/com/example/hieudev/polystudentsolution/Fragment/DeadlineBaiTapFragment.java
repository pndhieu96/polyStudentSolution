package com.example.hieudev.polystudentsolution.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieudev.polystudentsolution.Adapter.customBaiTapAdapter;
import com.example.hieudev.polystudentsolution.Adapter.customGhiNhoAdapter;
import com.example.hieudev.polystudentsolution.Dialog.themBaiTapDialog;
import com.example.hieudev.polystudentsolution.Dialog.themGhiNhoDialog;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocBaiTap;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DeadlineBaiTapFragment extends android.support.v4.app.Fragment {
    private RecyclerView baiTapRecyclerView;
    private FloatingActionButton fABaiTap;
    private String tenMon;
    private Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.deadline_baitap_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        baiTapRecyclerView = (RecyclerView) view.findViewById(R.id.BaiTapRecyclerView);
        fABaiTap = (FloatingActionButton) view.findViewById(R.id.floatingButtonBaiTap);
        baiTapRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHome = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        baiTapRecyclerView.setLayoutManager(layoutManagerHome);
        tenMon = MonDangHocActivity.getTenMon();

        try{
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        List<MonDangHocBaiTap> monDangHocBaiTapList = new ArrayList<MonDangHocBaiTap>();
        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        RealmList<MonDangHocBaiTap> monDangHocBaiTaps = monDangHocs.get(0).monDangHocBaiTap;
        if(monDangHocBaiTaps.size()>0){
            for(int i=0; i<monDangHocBaiTaps.size(); i++){
                monDangHocBaiTapList.add(monDangHocBaiTaps.get(i));
            }
            customBaiTapAdapter customBaiTapAdapter = new customBaiTapAdapter(monDangHocBaiTaps, tenMon);
            baiTapRecyclerView.setAdapter(customBaiTapAdapter);
        }

        fABaiTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),themBaiTapDialog.class);
                i.putExtra("TENMON", tenMon);
                startActivity(i);
            }
        });
    }
}
