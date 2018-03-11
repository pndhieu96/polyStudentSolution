package com.example.hieudev.polystudentsolution.RealmObject;

import io.realm.RealmObject;

public class TermId extends RealmObject{
    private int value;
    private String text;

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }
}
