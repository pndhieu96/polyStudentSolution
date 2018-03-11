package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class MonDangHocBaiTap extends RealmObject{
    private String tieude;
    private String noiDung;
    private int dealineNgay;
    private int dealineThang;
    private int dealineNam;
    private int dealineGio;
    private int dealinePhut;
    private String dealineBuoi;
    private int thongBaoNgay;
    private int thongBaoGio;
    private int thongBaoPhut;
    private String thongBaoBuoi;
    private Boolean checkThongBao;
    private int id;

    public String getTieude() {
        return tieude;
    }
    public String getNoiDung() {
        return noiDung;
    }
    public int getDealineNgay() {
        return dealineNgay;
    }
    public int getDealineThang() {
        return dealineThang;
    }
    public int getDealineNam() {
        return dealineNam;
    }
    public int getDealineGio() {
        return dealineGio;
    }
    public int getDealinePhut() {
        return dealinePhut;
    }
    public String getDealineBuoi() {
        return dealineBuoi;
    }
    public int getThongBaoNgay() {
        return thongBaoNgay;
    }
    public int getThongBaoGio() {
        return thongBaoGio;
    }
    public int getThongBaoPhut() {
        return thongBaoPhut;
    }
    public String getThongBaoBuoi() {
        return thongBaoBuoi;
    }
    public Boolean getCheckThongBao() {
        return checkThongBao;
    }
    public int getId() {
        return id;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    public void setDealineNgay(int dealineNgay) {
        this.dealineNgay = dealineNgay;
    }
    public void setDealineThang(int dealineThang) {
        this.dealineThang = dealineThang;
    }
    public void setDealineNam(int dealineNam) {
        this.dealineNam = dealineNam;
    }
    public void setDealineGio(int dealineGio) {
        this.dealineGio = dealineGio;
    }
    public void setDealinePhut(int dealinePhut) {
        this.dealinePhut = dealinePhut;
    }
    public void setDealineBuoi(String dealineBuoi) {
        this.dealineBuoi = dealineBuoi;
    }
    public void setThongBaoNgay(int thongBaoNgay) {
        this.thongBaoNgay = thongBaoNgay;
    }
    public void setThongBaoGio(int thongBaoGio) {
        this.thongBaoGio = thongBaoGio;
    }
    public void setThongBaoPhut(int thongBaoPhut) {
        this.thongBaoPhut = thongBaoPhut;
    }
    public void setThongBaoBuoi(String thongBaoBuoi) {
        this.thongBaoBuoi = thongBaoBuoi;
    }
    public void setCheckThongBao(Boolean checkThongBao) {
        this.checkThongBao = checkThongBao;
    }
    public void setId(int id) {
        this.id = id;
    }
}
