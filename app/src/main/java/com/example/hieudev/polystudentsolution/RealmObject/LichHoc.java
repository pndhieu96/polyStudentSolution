package com.example.hieudev.polystudentsolution.RealmObject;

import java.util.Date;

import io.realm.RealmObject;

public class LichHoc extends RealmObject{
    private int count;
    private String ngay;
    private String phong;
    private String giangDuong;
    private String mon;
    private String lop;
    private String giangVien;
    private String thoiGian;

    public String getNgay() {
        return ngay;
    }

    public String getPhong() {
        return phong;
    }

    public String getGiangDuong() {
        return giangDuong;
    }

    public String getMon() {
        return mon;
    }

    public String getLop() {
        return lop;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public void setGiangDuong(String giangDuong) {
        this.giangDuong = giangDuong;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public void setGiangVien(String giangVien) {
        this.giangVien = giangVien;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
