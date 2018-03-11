package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MonDangHoc extends RealmObject{
    private String tenMon;
    private float diemMucTieu;
    private int soBuoiChoPhep;
    public RealmList<MonDangHocBaiTap> monDangHocBaiTap;
    public RealmList<MonDangHocGhiNho> monDangHocGhiNho;
    public Boolean checkDangHoc;

    public String getTenMon() {
        return tenMon;
    }
    public float getDiemMucTieu() {
        return diemMucTieu;
    }
    public int getSoBuoiChoPhep() {
        return soBuoiChoPhep;
    }
    public Boolean getCheckDangHoc() {
        return checkDangHoc;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }
    public void setDiemMucTieu(float diemMucTieu) {
        this.diemMucTieu = diemMucTieu;
    }
    public void setSoBuoiChoPhep(int soBuoiChoPhep) {
        this.soBuoiChoPhep = soBuoiChoPhep;
    }
    public void setCheckDangHoc(Boolean checkDangHoc) {
        this.checkDangHoc = checkDangHoc;
    }
}
