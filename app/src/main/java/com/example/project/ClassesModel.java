package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassesModel implements Parcelable {
    private String class_name, description, specialization, image;
    private String[] attributes = new String[2];
    private ArrayList<String> skills;

    public ClassesModel() {
    }

    public ClassesModel(String class_name, String description, String specialization, String image, String[] attributes, ArrayList<String> skills) {
        this.class_name = class_name;
        this.description = description;
        this.specialization = specialization;
        this.image = image;
//        this.attributes = attributes;
        this.skills = skills;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    protected ClassesModel(Parcel in) {
        class_name = in.readString();
        description = in.readString();
        specialization = in.readString();
        image = in.readString();
        attributes = in.createStringArray();
        skills = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(class_name);
        dest.writeString(description);
        dest.writeString(specialization);
        dest.writeString(image);
        dest.writeStringArray(attributes);
        dest.writeStringList(skills);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassesModel> CREATOR = new Creator<ClassesModel>() {
        @Override
        public ClassesModel createFromParcel(Parcel in) {
            return new ClassesModel(in);
        }

        @Override
        public ClassesModel[] newArray(int size) {
            return new ClassesModel[size];
        }
    };

    @Override
    public String toString() {
        return image + "\n\nClass:\t" + class_name +
                "\nDescription: " + description +
                "\nSpecialization: " + specialization +
                "\nAttributes: " + getAttributes()[0] + " , " + getAttributes()[1] +
                "\nSkills: " + skills.toString();
    }
}
