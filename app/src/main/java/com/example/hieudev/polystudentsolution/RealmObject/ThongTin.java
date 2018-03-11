package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class ThongTin extends RealmObject{
    private byte[] anhHocVien;
    private String tenDangNhap;
    private String ho;
    private String tenDem;
    private String ten;
    private String maSV;
    private String gioiTinh;
    private String ngaySinh;
    private String email;
    private String nganhHoc;
    private String noiCap;

    public byte[] getAnhHocVien() {
        return anhHocVien;
    }

    public void setAnhHocVien(byte[] anhHocVien) {
        this.anhHocVien = anhHocVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getHo() {
        return ho;
    }

    public String getTenDem() {
        return tenDem;
    }

    public String getTen() {
        return ten;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public String getNganhHoc() {
        return nganhHoc;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public void setTenDem(String tenDem) {
        this.tenDem = tenDem;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNganhHoc(String nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }
}
