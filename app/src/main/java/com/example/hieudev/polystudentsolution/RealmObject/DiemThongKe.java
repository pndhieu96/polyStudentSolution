package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class DiemThongKe extends RealmObject{
    private String khoa;
    private float diemTrungBinh;
    private float phanTram;
    private String passed;
    private int chuaHoc;
    private int tongMonDat;
    private int tongHocLai;
    private int tongDangHoc;

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public float getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public float getPhanTram() {
        return phanTram;
    }

    public String getPassed() {
        return passed;
    }

    public int getChuaHoc() {
        return chuaHoc;
    }

    public int getTongMonDat() {
        return tongMonDat;
    }

    public int getTongHocLai() {
        return tongHocLai;
    }

    public int getTongDangHoc() {
        return tongDangHoc;
    }

    public void setDiemTrungBinh(float diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public void setPhanTram(float phanTram) {
        this.phanTram = phanTram;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public void setChuaHoc(int chuaHoc) {
        this.chuaHoc = chuaHoc;
    }

    public void setTongMonDat(int tongMonDat) {
        this.tongMonDat = tongMonDat;
    }

    public void setTongHocLai(int tongHocLai) {
        this.tongHocLai = tongHocLai;
    }

    public void setTongDangHoc(int tongDangHoc) {
        this.tongDangHoc = tongDangHoc;
    }
}
