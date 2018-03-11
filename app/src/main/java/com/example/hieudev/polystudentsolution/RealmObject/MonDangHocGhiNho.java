package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class MonDangHocGhiNho extends RealmObject{
    private String tieude;
    private String noiDung;

    public String getTieude() {
        return tieude;
    }
    public String getNoiDung() {
        return noiDung;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
