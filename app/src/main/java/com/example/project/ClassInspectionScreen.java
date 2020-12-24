package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassInspectionScreen extends AppCompatActivity {

    private String url = "https://en.uesp.net//w/api.php?action=mobileview&format=json&page=Morrowind%3AClasses&sections=1-&prop=text%7Csections.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_inspection_screen);

        TextView klase= (TextView) (findViewById(R.id.klase));
        APIComms.getClassIntrospection(new ArrayList<ClassesModel>(),url,klase);
    }
}