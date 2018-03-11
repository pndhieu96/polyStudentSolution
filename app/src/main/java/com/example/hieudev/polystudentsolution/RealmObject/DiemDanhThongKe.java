package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DiemDanhThongKe extends RealmObject{
    private String tenMon;
    private int buoiVang;
    private int tongSoBuoi;
    private float phanTramVang;
    public RealmList<DiemDanh> diemDanh;

    public String getTenMon() {
        return tenMon;
    }

    public int getBuoiVang() {
        return buoiVang;
    }

    public int getTongSoBuoi() {
        return tongSoBuoi;
    }

    public float getPhanTramVang() {
        return phanTramVang;
    }

    public void setPhanTramVang(float phanTramVang) {
        this.phanTramVang = phanTramVang;
    }

    public void setTongSoBuoi(int tongSoBuoi) {
        this.tongSoBuoi = tongSoBuoi;
    }

    public void setBuoiVang(int buoiVang) {
        this.buoiVang = buoiVang;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }
}
