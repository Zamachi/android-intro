package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizResult extends AppCompatActivity {

    ArrayList<String> odabrani_odgovori;
    TextView rezultati;
    private static String url = "https://en.uesp.net//w/api.php?action=mobileview&format=json&page=Morrowind%3AClass_Quiz&sections=1-&prop=text%7Csections";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        initializeComponents();
    }

    private void initializeComponents() {
        rezultati = (TextView)findViewById(R.id.rezultati);
        odabrani_odgovori = getIntent().getExtras().getStringArrayList("odabrani_odgovori");
        APIComms.getQClassAnswers(rezultati,url);
    }

}