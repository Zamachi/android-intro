package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{

    RadioButton odabraniOdgovor;
    ArrayList<QuestionModel> pitanja;
    int broj_stranice;
    TextView questionNumber ;
    TextView question ;
    RadioButton A;
    RadioButton B;
    RadioButton C;
    Button next;
    Button previous;
    Button finish_quiz;
    ArrayList<String> odabrani_odgovori = new ArrayList<>();
    RadioGroup pitanja_odgovori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        if(savedInstanceState == null) {
            initializeComponents();
        }
        else{
            initializeComponents(savedInstanceState);
        }
    }

    private void initializeComponents(Bundle savedInstanceState) {

        pitanja = savedInstanceState.getParcelableArrayList("pitanja");
        broj_stranice = (int) savedInstanceState.getInt("broj_stranice");

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(this);
        finish_quiz = (Button) findViewById(R.id.finish_quiz);
        finish_quiz.setOnClickListener(this);
        finish_quiz.setVisibility(View.INVISIBLE);
        finish_quiz.setEnabled(false);

        A = (RadioButton) findViewById(R.id.A);
        A.setOnClickListener(this);
        B = (RadioButton) findViewById(R.id.B);
        B.setOnClickListener(this);
        C = (RadioButton) findViewById(R.id.C);
        C.setOnClickListener(this);

        questionNumber = (TextView) findViewById(R.id.questionNumber);
        question = (TextView) findViewById(R.id.question);

        odabraniOdgovor = null;

        odabrani_odgovori = savedInstanceState.getStringArrayList("odabrani_odgovori");
        pitanja_odgovori = (RadioGroup)findViewById(R.id.pitanja_odgovori);
        updateQuestions(broj_stranice);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("odabrani_odgovori",odabrani_odgovori);
        outState.putInt("broj_stranice",broj_stranice);
        outState.putParcelableArrayList("pitanja",pitanja);
    }

    private void initializeComponents() {
        pitanja = getIntent().getExtras().getParcelableArrayList("pitanja");
        broj_stranice = (int) getIntent().getExtras().getInt("stranica");

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(this);
        finish_quiz = (Button) findViewById(R.id.finish_quiz);
        finish_quiz.setOnClickListener(this);
        finish_quiz.setVisibility(View.INVISIBLE);
        finish_quiz.setEnabled(false);

        A = (RadioButton) findViewById(R.id.A);
        A.setOnClickListener(this);
        B = (RadioButton) findViewById(R.id.B);
        B.setOnClickListener(this);
        C = (RadioButton) findViewById(R.id.C);
        C.setOnClickListener(this);

        questionNumber = (TextView) findViewById(R.id.questionNumber);
        question = (TextView) findViewById(R.id.question);

        odabraniOdgovor = null;

        pitanja_odgovori = (RadioGroup)findViewById(R.id.pitanja_odgovori);

        updateQuestions(broj_stranice);
    }

    private void updateQuestions(int broj_stranice) {
        if(broj_stranice == 10){
            next.setEnabled(false);
            finish_quiz.setVisibility(View.VISIBLE);
            finish_quiz.setEnabled(true);
        }else if(broj_stranice == 1){
            previous.setEnabled(false);
            finish_quiz.setVisibility(View.INVISIBLE);
            finish_quiz.setEnabled(false);
        }else{
            next.setEnabled(true);
            previous.setEnabled(true);
            finish_quiz.setVisibility(View.INVISIBLE);
            finish_quiz.setEnabled(false);
        }
        QuestionModel trenutno_pitanje= this.pitanja.get(broj_stranice-1);
        questionNumber.setText("Question No."+broj_stranice);
        question.setText(trenutno_pitanje.getQuestionTitle());
        A.setText(getResources().getString(R.string.a)+"\t"+trenutno_pitanje.getAnswerA());
        B.setText(getResources().getString(R.string.b)+"\t"+trenutno_pitanje.getAnswerB());
        C.setText(getResources().getString(R.string.c)+"\t"+trenutno_pitanje.getAnswerC());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.A: case R.id.B: case R.id.C:
                odabraniOdgovor = (RadioButton)v;
                break;
            case R.id.next:
                if(odabraniOdgovor == null){
                    Toast.makeText(this,"You have to choose an answer!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,odabraniOdgovor.getText(), Toast.LENGTH_SHORT).show();
                    posalji(1);
                }
                break;
            case R.id.previous:
                posalji(-1);
                break;
            case R.id.finish_quiz:
                odabrani_odgovori.add("" + odabraniOdgovor.getText().charAt(0));
                Intent i = new Intent(this, QuizResult.class);
                Bundle b = new Bundle();
                b.putStringArrayList("odabrani_odgovori",odabrani_odgovori);
                i.putExtras(b);
                startActivity(i);
                break;
            default:
                Toast.makeText(this,"Morate odabrati nesto!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void posalji(int i) {

        String odgovor = "";
        pitanja_odgovori.clearCheck();
        A.setChecked(false);
        B.setChecked(false);
        C.setChecked(false);
        switch (i){
            case (-1):
                odabrani_odgovori.remove(odabrani_odgovori.size()-1);
                //this.odabraniOdgovor.setChecked(false);//TODO: vraca exception java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.RadioButton.setChecked(boolean)' on a null object reference kada se klikne back, a nijedan odg. nije selektovan.
                this.odabraniOdgovor = null;
                this.broj_stranice--;
                break;
            case 1:
                odabrani_odgovori.add(odgovor + odabraniOdgovor.getText().charAt(0));
                this.odabraniOdgovor.setChecked(false);
                this.odabraniOdgovor = null;
                this.broj_stranice++;
                break;
            default:
                Toast.makeText(this,"Neispravno uneta stranica!", Toast.LENGTH_SHORT).show();
                break;
        }
        updateQuestions(broj_stranice);
    }
}