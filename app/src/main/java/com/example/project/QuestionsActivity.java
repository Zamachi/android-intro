package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{

    RadioButton odabraniOdgovor = null;
    ArrayList<QuestionModel> pitanja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        RadioButton A = (RadioButton) findViewById(R.id.A);
        A.setOnClickListener(this);
        RadioButton B = (RadioButton) findViewById(R.id.B);
        B.setOnClickListener(this);
        RadioButton C = (RadioButton) findViewById(R.id.C);
        C.setOnClickListener(this);

        pitanja = getIntent().getExtras().getParcelableArrayList("pitanja");
//        String test = "";
//        for (QuestionModel qm:pitanja
//             ) {
//            test += qm.toString();
//        }
//        ((TextView) (findViewById(R.id.aopsd))).setText(test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.A: case R.id.B: case R.id.C:
                odabraniOdgovor = (RadioButton)v;
                break;
            case R.id.next:
                if(odabraniOdgovor == null){
                    Toast.makeText(this,"Morate odabrati nesto!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,odabraniOdgovor.getText(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this,"Morate odabrati nesto!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //TODO: treba da sadrzi polje o stranici(to se u prethodnoj aktivnosti postavlja u Bundle-u pod kljucem "stranica" sa vrednoscu 1
}