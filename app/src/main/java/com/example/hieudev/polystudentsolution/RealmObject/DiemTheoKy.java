package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class DiemTheoKy extends RealmObject{
    private String tenDauDiem;
    private float trongSo;
    private float diem;

    public String getTenDauDiem() {
        return tenDauDiem;
    }

    public float getTrongSo() {
        return trongSo;
    }

    public float getDiem() {
        return diem;
    }

    public void setTenDauDiem(String tenDauDiem) {
        this.tenDauDiem = tenDauDiem;
    }

    public void setTrongSo(float trongSo) {
        this.trongSo = trongSo;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }
}
