package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class QuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
    }

    //TODO: treba da sadrzi polje o stranici(to se u prethodnoj aktivnosti postavlja u Bundle-u pod kljucem "stranica" sa vrednoscu 1
}