package com.example.hieudev.polystudentsolution.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hieudev.polystudentsolution.Custom.NetworkInfo;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.TermId;
import com.example.hieudev.polystudentsolution.ViewActivity.ChooseCampusActivity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.example.hieudev.polystudentsolution.R.id.spinnerShowCout;

public class thietLapDialog extends Dialog{

    Spinner spinnerSoBuoi, spinnerHocKy;
    ArrayList<String> termName;
    ArrayList<Integer> showCount;
    Realm realm;
    Button btnSettingsThoat, btnSettingsOk;
    int[] showNumOfDay={7,14,30,60,90,-7,-14,-30,-60,-90};
    int term_Id;
    int show_Count;

    public thietLapDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_settings);
        setTitle("Thiết lập");
        try{
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        termName = new ArrayList<String>();
        RealmResults<TermId> termIds = realm.where(TermId.class).findAll().sort("value");
        for(int i=termIds.size()-1; i >= 0; i--){
            termName.add(termIds.get(i).getText());
        }
        ArrayAdapter<String> adapterHocKy =
                new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, termName);
        adapterHocKy.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        showCount = new ArrayList<Integer>();
        for (int count=0; count<showNumOfDay.length; count++){
            showCount.add(showNumOfDay[count]);
        }
        ArrayAdapter<Integer> adapterSoBuoi =
                new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item, showCount);
        adapterSoBuoi.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        SharedPreferences sharedPreferencesSettings = getContext().getSharedPreferences("mode", Context.MODE_PRIVATE);
        term_Id = sharedPreferencesSettings.getInt("TERM_ID", 20);
        show_Count = sharedPreferencesSettings.getInt("SHOW_COUNT", 14);
        RealmResults<TermId> termIdSpinner = realm.where(TermId.class).equalTo("value",term_Id).findAll();

        int positionTermId = adapterHocKy.getPosition(String.valueOf(termIdSpinner.get(0).getText()));
        int positionShowCount = adapterSoBuoi.getPosition(show_Count);

        spinnerSoBuoi = (Spinner) findViewById(spinnerShowCout);
        spinnerHocKy = (Spinner) findViewById(R.id.spinnerHocKy);
        btnSettingsThoat = (Button) findViewById(R.id.buttonSettingsThoat);
        btnSettingsOk = (Button) findViewById(R.id.buttonSettingsOk);

        spinnerHocKy.setAdapter(adapterHocKy);
        spinnerHocKy.setSelection(positionTermId);
        spinnerSoBuoi.setAdapter(adapterSoBuoi);
        spinnerSoBuoi.setSelection(positionShowCount);

        btnSettingsThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thietLapDialog.this.cancel();
            }
        });

        btnSettingsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<TermId> termIdValue = realm.where(TermId.class).contains("text", spinnerHocKy.getSelectedItem().toString()).findAll();
                SharedPreferences.Editor edit = getContext().getSharedPreferences("mode", Context.MODE_PRIVATE).edit();
                edit.putInt("TERM_ID", termIdValue.get(0).getValue());
                edit.putInt("SHOW_COUNT", Integer.parseInt(spinnerSoBuoi.getSelectedItem().toString()));
                edit.commit();
                thietLapDialog.this.cancel();
                if(NetworkInfo.isConnected(getContext())){
                    Intent i = new Intent(getContext(), ChooseCampusActivity.class);
                    getContext().startActivity(i);
                }else{
                    Toast.makeText(getContext(),"Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
