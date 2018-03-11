package com.example.hieudev.polystudentsolution.Dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.R.attr.value;
import static com.example.hieudev.polystudentsolution.R.layout.support_simple_spinner_dropdown_item;

public class ChinhSuaBaiTapDetail extends AppCompatActivity {
    private com.neopixl.pixlui.components.edittext.EditText edtTitle, edtDes;
    private Button btnLuu, btnThoat, btnNgay, btnGio, btnGioHan;
    private Spinner spinnerNgayHan;
    private String title, tieuDe, noiDung;
    private String tenMon;
    private Realm realm;
    private int[] showNgayHan={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    private ArrayList<Integer> showNgayHanList;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int gio, phut, ngay, thang, nam, gioHan, phutHan, ngayHan;
    private boolean checkThongBao;
    private CheckBox checkBoxThongBao;
    private int position, monid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_bai_tap_detail);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("POSITION");
            tenMon = extras.getString("TENMON");
        }
        setTitle(tenMon);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        showNgayHanList = new ArrayList<Integer>();
        for (int count=0; count<showNgayHan.length; count++){
            showNgayHanList.add(showNgayHan[count]);
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
        edtTitle = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextBaiTapDetailTitle);
        edtDes = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.editTextBaiTapDetailDes);
        btnLuu = (Button) findViewById(R.id.buttonBaiTapDetailLuu);
        btnThoat = (Button) findViewById(R.id.buttonBaiTapDetailThoat);
        btnNgay = (Button) findViewById(R.id.buttonBaiTapDetailNgay);
        btnGio = (Button) findViewById(R.id.buttonBaiTapDetailGio);
        btnGioHan = (Button) findViewById(R.id.buttonBaiTapDetailGioTB);
        checkBoxThongBao = (CheckBox) findViewById(R.id.checkBoxBaiTapDetailTB);
        spinnerNgayHan = (Spinner) findViewById(R.id.spinnerBaiTapDetailNgayTB);

        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
        RealmList<MonDangHocBaiTap> monDangHocBaiTaps = monDangHocs.get(0).monDangHocBaiTap;
        tieuDe = monDangHocBaiTaps.get(position).getTieude();
        noiDung = monDangHocBaiTaps.get(position).getNoiDung();
        gio = monDangHocBaiTaps.get(position).getDealineGio();
        phut = monDangHocBaiTaps.get(position).getDealinePhut();
        ngay = monDangHocBaiTaps.get(position).getDealineNgay();
        thang = monDangHocBaiTaps.get(position).getDealineThang();
        nam = monDangHocBaiTaps.get(position).getDealineNam();
        gioHan = monDangHocBaiTaps.get(position).getThongBaoGio();
        phutHan = monDangHocBaiTaps.get(position).getThongBaoPhut();
        ngayHan = monDangHocBaiTaps.get(position).getThongBaoNgay();
        checkThongBao = monDangHocBaiTaps.get(position).getCheckThongBao();

        edtTitle.setText(tieuDe);
        edtDes.setText(noiDung);
        btnNgay.setText(ngay + "/" + thang + "/" + nam);
        btnGio.setText(gio+":"+phut);
        btnGioHan.setText(gioHan+":"+phutHan);
        if(checkThongBao){
            checkBoxThongBao.setChecked(true);
        }

        //spinnerNgayHan
        ArrayAdapter<Integer> adapterNgayHan =
                new ArrayAdapter<Integer>(ChinhSuaBaiTapDetail.this, R.layout.spinner_ngay_han, showNgayHanList);
        adapterNgayHan.setDropDownViewResource(R.layout.bai_tap_spinner_dropdown_item);
        int positionSpinner = adapterNgayHan.getPosition(ngayHan);
        spinnerNgayHan.setAdapter(adapterNgayHan);
        spinnerNgayHan.setSelection(positionSpinner);
        spinnerNgayHan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //btnNgay
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ChinhSuaBaiTapDetail.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(ChinhSuaBaiTapDetail.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(ChinhSuaBaiTapDetail.this,
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
                RealmResults<MonDangHoc> monDangHocs1 = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                monid = monDangHocs1.get(0).monDangHocBaiTap.get(position).getId();
                System.out.println("monId " + monid);
                if(checkBoxThongBao.isChecked()){
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
                    CreateAlarm createAlarm = new CreateAlarm(getApplicationContext());
                    createAlarm.addAlarm(ngayTb1, thangTb1, namTb1, gioHan, phutHan, monid, tenMon);
                }
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        String title = edtTitle.getText().toString();
                        String des = edtDes.getText().toString();
                        RealmResults<MonDangHoc> monDangHocs = realm.where(MonDangHoc.class).contains("tenMon", tenMon).findAll();
                        RealmList<MonDangHocBaiTap> monDangHocBaiTap = monDangHocs.get(0).monDangHocBaiTap;
                        monDangHocBaiTap.get(position).setTieude(title);
                        monDangHocBaiTap.get(position).setNoiDung(des);
                        if(checkBoxThongBao.isChecked()) {
                            monDangHocBaiTap.get(position).setCheckThongBao(true);
                        }else {
                            monDangHocBaiTap.get(position).setCheckThongBao(false);
                        }
                        monDangHocBaiTap.get(position).setDealineGio(gio);
                        monDangHocBaiTap.get(position).setDealinePhut(phut);
                        monDangHocBaiTap.get(position).setDealineNgay(ngay);
                        monDangHocBaiTap.get(position).setDealineThang(thang);
                        monDangHocBaiTap.get(position).setDealineNam(nam);
                        monDangHocBaiTap.get(position).setThongBaoNgay(ngayHan);
                        monDangHocBaiTap.get(position).setThongBaoGio(gioHan);
                        monDangHocBaiTap.get(position).setThongBaoPhut(phutHan);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Intent go = new Intent(ChinhSuaBaiTapDetail.this, MonDangHocActivity.class);
                        go.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        go.putExtra("TENMON",tenMon);
                        go.putExtra("viewpager_position", 0);
                        startActivity(go);
                        ChinhSuaBaiTapDetail.this.finish();
                    }
                });
            }
        });
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
