package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassesModel {
    private String class_name, combat, magic,stealth;
    public ClassesModel() {
    }

    public ClassesModel(String class_name, String combat, String magic, String stealth) {
        this.class_name = class_name;
        this.combat = combat;
        this.magic = magic;
        this.stealth = stealth;
    }

    public String getClass_name() {
        return class_name;
    }

    public String getCombat() {
        return combat;
    }

    public String getMagic() {
        return magic;
    }

    public String getStealth() {
        return stealth;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public void setCombat(String combat) {
        this.combat = combat;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public void setStealth(String stealth) {
        this.stealth = stealth;
    }

    @Override
    public String toString() {
        return
                class_name + "\n" +
                        "A:" + combat + "\n" +
                        "B:" + magic + "\n" +
                        "C:" + stealth + "\n";
    }
}
