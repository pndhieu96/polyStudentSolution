package com.example.hieudev.polystudentsolution.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieudev.polystudentsolution.Adapter.customLichHocAdapter;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;
import com.example.hieudev.polystudentsolution.RealmObject.ThongTin;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LichHocFragment extends Fragment {
    RecyclerView lichHocRecyclerView;
    Realm realm;
    private static int DIEM_THONG_KE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lichhoc_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lichHocRecyclerView = (RecyclerView) view.findViewById(R.id.lichHocRecyclerView);
        lichHocRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lichHocLLM = new LinearLayoutManager(getContext());
        lichHocLLM.setOrientation(LinearLayoutManager.VERTICAL);
        lichHocRecyclerView.setLayoutManager(lichHocLLM);
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
            List<LichHoc> lichHocs = new ArrayList<LichHoc>();
            RealmResults<LichHoc> lichHocs1 = realm.where(LichHoc.class).findAll();
            lichHocs1 = lichHocs1.sort("count");
            for (int i = 0; i < lichHocs1.size(); i++) {
                lichHocs.add(lichHocs1.get(i));
            }
            customLichHocAdapter customLichHocAdapter = new customLichHocAdapter(lichHocs);
            lichHocRecyclerView.setAdapter(customLichHocAdapter);
        }
    }
}
