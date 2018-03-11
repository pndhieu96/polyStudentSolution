package com.example.hieudev.polystudentsolution.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.ThongTin;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class thongTinHocVienDialog extends Dialog{
    TextView tvDangNhap, tvHoTen, tvMaSv, tvGioiTinh, tvNgaySinh, tvEmail, tvNganhHoc, tvNoiCap;
    Realm realm;
    public thongTinHocVienDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinhocvien_dialog);

        tvDangNhap = (TextView) findViewById(R.id.tvTenDangNhap);
        tvHoTen = (TextView) findViewById(R.id.tvHoTen);
        tvMaSv = (TextView) findViewById(R.id.tvMaSv);
        tvGioiTinh = (TextView) findViewById(R.id.tvGioiTinh);
        tvNgaySinh = (TextView) findViewById(R.id.tvNgaySinh);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvNganhHoc = (TextView) findViewById(R.id.tvNganhHoc);
        tvNoiCap = (TextView) findViewById(R.id.tvNoiCap);

        setTitle("Thông tin học viên");

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
        tvDangNhap.setText(" " +thongTins.get(0).getTenDangNhap());
        tvHoTen.setText(thongTins.get(0).getHo() + " " + thongTins.get(0).getTenDem() + " " + thongTins.get(0).getTen());
        tvMaSv.setText(thongTins.get(0).getMaSV());
        tvGioiTinh.setText(thongTins.get(0).getGioiTinh());
        tvNgaySinh.setText(thongTins.get(0).getNgaySinh());
        tvEmail.setText(thongTins.get(0).getEmail());
        tvNganhHoc.setText(thongTins.get(0).getNganhHoc());
        tvNoiCap.setText(thongTins.get(0).getNoiCap());
    }
}
