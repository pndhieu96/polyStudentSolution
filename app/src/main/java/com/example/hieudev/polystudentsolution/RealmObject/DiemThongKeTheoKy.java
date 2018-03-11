package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DiemThongKeTheoKy extends RealmObject{
    private String tenMon;
    private float diemTrungBinh;
    private String trangThai;
    public RealmList<DiemTheoKy> diemTheoKy;

    public String getTenMon() {
        return tenMon;
    }

    public float getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setDiemTrungBinh(float diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
