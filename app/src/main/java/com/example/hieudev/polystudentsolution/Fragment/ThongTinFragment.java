package com.example.hieudev.polystudentsolution.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hieudev.polystudentsolution.Adapter.customThongTinAdapter;
import com.example.hieudev.polystudentsolution.Dialog.DiemDanhDialog;
import com.example.hieudev.polystudentsolution.Dialog.DiemTheoKyDialog;
import com.example.hieudev.polystudentsolution.Dialog.DiemTheoMonDialog;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.ThongTin;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class ThongTinFragment extends Fragment {
    ListView listView;
    Realm realm;
    int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thongtin_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            listView = (ListView) view.findViewById(R.id.listViewThongTin);
            ArrayList<String> strings = new ArrayList<String>();
            strings.add("Điểm Danh");
            strings.add("Điểm theo kỳ");
            strings.add("Điểm theo môn");
            listView.setAdapter(new customThongTinAdapter(getContext(), strings));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            Intent i = new Intent(getContext(), DiemDanhDialog.class);
                            startActivity(i);
                            break;
                        case 1:
                            Intent i1 = new Intent(getContext(), DiemTheoKyDialog.class);
                            startActivity(i1);
                            break;
                        case 2:
                            Intent i2 = new Intent(getContext(), DiemTheoMonDialog.class);
                            startActivity(i2);
                            break;
                    }
                }
            });
        }
    }
}



