package com.example.hieudev.polystudentsolution.Dialog;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hieudev.polystudentsolution.Alarm.CreateAlarm;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocBaiTap;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.example.hieudev.polystudentsolution.R.layout.support_simple_spinner_dropdown_item;

public class themBaiTapDialog extends AppCompatActivity {
    private com.neopixl.pixlui.components.edittext.EditText edtTitle, edtDes;
    private Button btnLuu, btnThoat, btnNgay, btnGio, btnGioHan;
    private Spinner spinnerNgayHan;
    private String title;
    private String tenMon;
    private Realm realm;
    private int[] showNgayHan={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    private ArrayList<Integer> showNgayHanList;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int gio, phut, ngay, thang, nam, gioHan, phutHan, ngayHan;
    private boolean checkThongBao;
    private CheckBox checkBoxThongBao;
    private int nextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_bai_tap_dialog);
        Intent i = getIntent();
        tenMon = i.getStringExtra("TENMON");
        setTitle(tenMon);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        showNgayHanList = new ArrayList<Integer>();
        for (int count=0; count<showNgayHan.length; count++){
            showNgayHanList.add(showNgayHan[count]);
        }

        //set date default
        SimpleDateFormat format = new SimpleDateFormat("dd,MM,yyyy,HH,mm");
        String theDate = format.format(Calendar.getInstance().getTime());
        String[] outDate = theDate.split(",");
        gio = Integer.parseInt(outDate[3]);
        phut = Integer.parseInt(outDate[4]);
        ngay = Integer.parseInt(outDate[0]);
        thang = Integer.parseInt(outDate[1]);
        nam = Integer.parseInt(outDate[2]);
        gioHan = Integer.parseInt(outDate[3]);
        phutHan = Integer.parseInt(outDate[4]);
        ngayHan = 1;
        //

        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        edtTitle = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextBaiTapTitle);
        edtDes = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextBaiTapDes);
        btnLuu = (Button) findViewById(R.id.buttonBaiTapLuu);
        btnThoat = (Button) findViewById(R.id.buttonBaiTapThoat);
        btnNgay = (Button) findViewById(R.id.buttonBaiTapNgay);
        btnGio = (Button) findViewById(R.id.buttonBaiTapGio);
        btnGioHan = (Button) findViewById(R.id.buttonBaiTapGioTB);
        checkBoxThongBao = (CheckBox) findViewById(R.id.checkBoxBaiTapTB);
        spinnerNgayHan = (Spinner) findViewById(R.id.spinnerBaiTapNgayTB);

        btnNgay.setText(ngay + "/" + thang + "/" + nam);
        btnGio.setText(gio+":"+phut);
        btnGioHan.setText(gio+":"+phut);

        //spinnerNgayHan
        ArrayAdapter<Integer> adapterNgayHan =
                new ArrayAdapter<Integer>(this.getApplicationContext(), R.layout.spinner_ngay_han, showNgayHanList);
        adapterNgayHan.setDropDownViewResource(R.layout.bai_tap_spinner_dropdown_item);
        spinnerNgayHan.setAdapter(adapterNgayHan);

        //btnNgay
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(themBaiTapDialog.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                btnNgay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                ngay = dayOfMonth;
                                thang = monthOfYear + 1;
                                nam = year;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //btnGio
        btnGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(themBaiTapDialog.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                btnGio.setText(hourOfDay + ":" + minute);
                                gio = hourOfDay;
                                phut = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        //btnGioHan
        btnGioHan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(themBaiTapDialog.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                btnGioHan.setText(hourOfDay + ":" + minute);
                                gioHan = hourOfDay;
                                phutHan = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngayHan = Integer.parseInt(spinnerNgayHan.getSelectedItem().toString());
                title = edtTitle.getText().toString();
                if(title.isEmpty()){
                    Intent go = new Intent(themBaiTapDialog.this,MonDangHocActivity.class);
                    go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    go.putExtra("TENMON",tenMon);
                    go.putExtra("viewpager_position", 0);
                    startActivity(go);
                    themBaiTapDialog.this.finish();
                }else {

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            String title = edtTitle.getText().toString();
                            String des = edtDes.getText().toString();
                            RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                            MonDangHocBaiTap monDangHocBaiTap = realm.createObject(MonDangHocBaiTap.class);
                            monDangHocBaiTap.setTieude(title);
                            monDangHocBaiTap.setNoiDung(des);
                            if(checkBoxThongBao.isChecked()) {
                                monDangHocBaiTap.setCheckThongBao(true);
                            }else {
                                monDangHocBaiTap.setCheckThongBao(false);
                            }
                            nextID = realm.where(MonDangHocBaiTap.class).max("id").intValue() + 1;
                            monDangHocBaiTap.setDealineGio(gio);
                            monDangHocBaiTap.setDealinePhut(phut);
                            monDangHocBaiTap.setDealineNgay(ngay);
                            monDangHocBaiTap.setDealineThang(thang);
                            monDangHocBaiTap.setDealineNam(nam);
                            monDangHocBaiTap.setThongBaoNgay(ngayHan);
                            monDangHocBaiTap.setThongBaoGio(gioHan);
                            monDangHocBaiTap.setThongBaoPhut(phutHan);
                            monDangHocBaiTap.setId(nextID);
                            monDangHocs.get(0).monDangHocBaiTap.add(monDangHocBaiTap);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            setThongBao();
                            Intent go = new Intent(themBaiTapDialog.this, MonDangHocActivity.class);
                            go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            go.putExtra("TENMON",tenMon);
                            go.putExtra("viewpager_position", 0);
                            startActivity(go);
                            themBaiTapDialog.this.finish();
                        }
                    });
                }
            }
        });
    }

    private void setThongBao(){
        if(checkBoxThongBao.isChecked()) {
            String untildate = ngay + "-" + thang + "-" + nam;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            int ngayTb = Integer.parseInt("-" + ngayHan);
            try {
                cal.setTime(dateFormat1.parse(untildate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.add(Calendar.DATE, ngayTb);
            String convertedDate = dateFormat1.format(cal.getTime());
            System.out.println("Date increase by one.." + convertedDate + ":" + gioHan + ":" + phutHan);
            String[] outDate1 = convertedDate.split("-");
            int ngayTb1 = Integer.parseInt(outDate1[0]);
            int thangTb1 = Integer.parseInt(outDate1[1]);
            int namTb1 = Integer.parseInt(outDate1[2]);
            System.out.println("setIdThongBao " + nextID);
            CreateAlarm createAlarm = new CreateAlarm(getApplicationContext());
            createAlarm.addAlarm(ngayTb1, thangTb1, namTb1, gioHan, phutHan, nextID, tenMon);
        }
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
