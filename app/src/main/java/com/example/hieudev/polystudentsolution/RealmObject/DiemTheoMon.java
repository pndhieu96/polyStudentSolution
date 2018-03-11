package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class DiemTheoMon extends RealmObject{
    private int kyThu;
    private String hocKy;
    private String mon;
    private String maMon;
    private String maChuyenDoi;
    private int soTinChi;
    private float diem;
    private String trangThai;

    public int getKyThu() {
        return kyThu;
    }

    public String getHocKy() {
        return hocKy;
    }

    public String getMon() {
        return mon;
    }

    public String getMaMon() {
        return maMon;
    }

    public String getMaChuyenDoi() {
        return maChuyenDoi;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public float getDiem() {
        return diem;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setKyThu(int kyThu) {
        this.kyThu = kyThu;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public void setMaChuyenDoi(String maChuyenDoi) {
        this.maChuyenDoi = maChuyenDoi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
