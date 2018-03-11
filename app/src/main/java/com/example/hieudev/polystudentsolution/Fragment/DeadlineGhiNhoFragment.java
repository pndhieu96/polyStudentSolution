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

import com.example.hieudev.polystudentsolution.Adapter.customDeadlineAdapter;
import com.example.hieudev.polystudentsolution.Adapter.customGhiNhoAdapter;
import com.example.hieudev.polystudentsolution.Dialog.themGhiNhoDialog;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DeadlineGhiNhoFragment extends android.support.v4.app.Fragment {
    private RecyclerView ghiNhoRecyclerView;
    private FloatingActionButton fAGhiNho;
    private String tenMon;
    private Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.deadline_ghinho_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ghiNhoRecyclerView = (RecyclerView) view.findViewById(R.id.GhiNhoRecyclerView);
        fAGhiNho = (FloatingActionButton) view.findViewById(R.id.floatingButtonGhiNho);
        ghiNhoRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHome = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ghiNhoRecyclerView.setLayoutManager(layoutManagerHome);
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

        List<MonDangHocGhiNho> MonDangHocGhiNhoList = new ArrayList<MonDangHocGhiNho>();
        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        RealmList<MonDangHocGhiNho> monDangHocGhiNhos = monDangHocs.get(0).monDangHocGhiNho;
        if(monDangHocGhiNhos.size()>0){
            for(int i=0; i<monDangHocGhiNhos.size(); i++){
                MonDangHocGhiNhoList.add(monDangHocGhiNhos.get(i));
            }
            customGhiNhoAdapter customGhiNhoAdapter = new customGhiNhoAdapter(monDangHocGhiNhos, tenMon);
            ghiNhoRecyclerView.setAdapter(customGhiNhoAdapter);
        }

        fAGhiNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),themGhiNhoDialog.class);
                i.putExtra("TENMON", tenMon);
                startActivity(i);
            }
        });
    }
}
