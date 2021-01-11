package com.example.project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizResult extends AppCompatActivity implements View.OnClickListener{

    ArrayList<String> odabrani_odgovori;
    TextView rezultati;
    private static String url = "https://en.uesp.net//w/api.php?action=mobileview&format=json&page=Morrowind%3AClass_Quiz&sections=1-&prop=text%7Csections";
    private static String urlKlase = "https://en.uesp.net/wiki/Morrowind:Classes";
    ArrayList<ClassesModel> klase = new ArrayList<>();
    ClassesModel odabranaKlasa = new ClassesModel();
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        initializeComponents();
    }

    private void initializeComponents() {
        rezultati = (TextView)findViewById(R.id.rezultati);
        odabrani_odgovori = getIntent().getExtras().getStringArrayList("odabrani_odgovori");
        APIComms.getQClassAnswers(klase,url,rezultati,odabrani_odgovori,odabranaKlasa,new Database(this));

        Button close = (Button) findViewById(R.id.show_statistic);
        close.setOnClickListener(this);
        Button show_all_classes = (Button) findViewById(R.id.show_all_classes);
        show_all_classes.setOnClickListener(this);

        username = getIntent().getExtras().getString("inputUsername");
        rezultati.setText(username+rezultati.getText().toString());
        WebView klasa_pogled= (WebView) (findViewById(R.id.klasa_pogled));
        klasa_pogled.getSettings().setJavaScriptEnabled(true);
        klasa_pogled.getSettings().setDomStorageEnabled(true);
        klasa_pogled.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                String izvrsiti = "javascript: (function(){" +
                        "var x = document.querySelector(\"#"+odabranaKlasa.getClass_name()+"\").parentElement.parentElement; " +
                        "document.body.innerHTML = \"\";" +
                        "document.body.append(x);" +
                        "}) ();";
                klasa_pogled.loadUrl(izvrsiti);
            }
        });
        klasa_pogled.loadUrl(urlKlase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_all_classes:
                startActivity(new Intent(this, ClassInspectionScreen.class));
                break;
            case R.id.show_statistic:
                startActivity(new Intent(this, RezultatiBaze.class));
                break;
        }
    }
}