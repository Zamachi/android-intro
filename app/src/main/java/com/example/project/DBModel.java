package com.example.project;

public class DBModel {
    public static final String TABLE_NAME = "results";
    //imena kolona u bazi (odnosno polja)
    public static final String FIELD_RESULT_ID = "result_id";
    public static final String FIELD_CLASS_NAME = "class_name";
    public static final String FIELD_OCCURENCE = "occurence";

    private int result_id;
    private String class_name;
    private int occurence;

    public DBModel(int result_id, String class_name, int occurence) {
        this.result_id = result_id;
        this.class_name = class_name;
        this.occurence = occurence;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    @Override
    public String toString() {
        return "DBModel{" +
                "result_id=" + result_id +
                ", class_name='" + class_name + '\'' +
                ", occurence=" + occurence +
                '}';
    }
}
