package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RezultatiBaze extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati_baze);

        Database db = new Database(this);

        LinearLayout mainScrollView = findViewById(R.id.mainScrollView);

        ArrayList<DBModel> rezultati = (ArrayList<DBModel>) db.getAllSavedClasses();

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (DBModel dbm:rezultati
             ) {
            ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.list_item,null);

            TextView ime_klase = item.findViewById(R.id.ime_klase);
            ime_klase.setText(dbm.getClass_name());
            TextView vrednost_klase = item.findViewById(R.id.vrednost_klase);
            vrednost_klase.setText(""+dbm.getOccurence());

            mainScrollView.addView(item);
        }
    }
}