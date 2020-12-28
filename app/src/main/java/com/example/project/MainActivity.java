package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static String url = "https://en.uesp.net//w/api.php?action=mobileview&format=json&page=Morrowind%3AClass_Quiz&sections=1-&prop=text%7Csections";
    private static String urlKlasa = "https://en.uesp.net//w/api.php?action=mobileview&format=json&page=Morrowind%3AClass_Quiz&sections=1-&prop=text%7Csections";
    ArrayList<QuestionModel> pitanja = new ArrayList<>();
    TextView inputUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dugme = findViewById(R.id.posalji);
        dugme.setOnClickListener(this);

        Button pregledaj_klase = findViewById(R.id.pregledaj_klase);
        pregledaj_klase.setOnClickListener(this);

        inputUsername = (TextView) findViewById(R.id.inputUsername);

        APIComms.getQuizQuestions(this.pitanja,url);

        Database baza_podataka = new Database(this);
        APIComms.fillDatabase(baza_podataka,urlKlasa);
    }

    @Override
    public void onClick(View v) {
//TODO: dovrsi ovo za prelazak na novi activity, posalji pitanja ARrayList
        switch (v.getId()) {
            case R.id.posalji:
                if(inputUsername.getText().toString().equals(null) || inputUsername.getText().toString().equals("") || inputUsername.getText().toString().equals(" ")){
                    Toast.makeText(this,"You have to input your username!",Toast.LENGTH_SHORT);
                    break;
                }
                Intent i = new Intent(this, QuestionsActivity.class);
                Bundle b = new Bundle();
                b.putInt("stranica", 1);
                b.putParcelableArrayList("pitanja", this.pitanja);
                b.putString("inputUsername",inputUsername.getText().toString());
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.pregledaj_klase:
                startActivity(new Intent(this,ClassInspectionScreen.class));
                break;
        }
    }

}