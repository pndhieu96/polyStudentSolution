package com.example.hieudev.polystudentsolution.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieudev.polystudentsolution.Adapter.customDeadlineAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanh;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DeadlineFragment extends Fragment{
    Realm realm;
    RecyclerView deadlineRecyclerView;
    private int thu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.deadline_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deadlineRecyclerView = (RecyclerView) view.findViewById(R.id.deadlineRecyclerView);
        deadlineRecyclerView.setHasFixedSize(true);
        LinearLayoutManager deadlineLLM = new LinearLayoutManager(getContext());
        deadlineLLM.setOrientation(LinearLayoutManager.VERTICAL);
        deadlineRecyclerView.setLayoutManager(deadlineLLM);
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){
            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }

        List<MonDangHoc> monDangHocList = new ArrayList<MonDangHoc>();
        List<Integer> buoiVangs = new ArrayList<Integer>();
        RealmResults<MonDangHoc> monDangHoc2s = realm.where(MonDangHoc.class).findAll();
        if(monDangHoc2s.size()>0){
            for(int i=0; i<monDangHoc2s.size(); i++){
                monDangHocList.add(monDangHoc2s.get(i));
                RealmResults<DiemDanhThongKe> diemDanhThongKes = realm.where(DiemDanhThongKe.class).contains("tenMon", monDangHoc2s.get(i).getTenMon()).findAll();
                if(diemDanhThongKes.size()>0){
                    buoiVangs.add(diemDanhThongKes.get(0).getBuoiVang());
                }

            }
            customDeadlineAdapter customDeadlineAdapter = new customDeadlineAdapter(monDangHocList);
            deadlineRecyclerView.setAdapter(customDeadlineAdapter);
        }
    }
}
