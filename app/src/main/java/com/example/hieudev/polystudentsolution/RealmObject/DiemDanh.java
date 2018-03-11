package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class DiemDanh extends RealmObject{
    private int buoiHoc;
    private String ngay;
    private int ca;
    private String trangThai;

    public int getBuoiHoc() {
        return buoiHoc;
    }

    public String getNgay() {
        return ngay;
    }

    public int getCa() {
        return ca;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setBuoiHoc(int buoiHoc) {
        this.buoiHoc = buoiHoc;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
