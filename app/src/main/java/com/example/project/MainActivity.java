package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<QuestionModel> pitanja = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dugme = findViewById(R.id.posalji);
        dugme.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {
           @Override
           protected String doInBackground(String... strings) {
               String response="";
               try {
                   URL link = new URL(strings[0]);
                   HttpsURLConnection nova_veza = (HttpsURLConnection) link.openConnection();
                   nova_veza.setDoInput(true);
                   nova_veza.connect();

                   BufferedReader br = new BufferedReader(new InputStreamReader(nova_veza.getInputStream()));

                   String red;
                   while( (red = br.readLine()) != null ){
                       response += red + "\n";
                   }

                   br.close();
                   nova_veza.disconnect();

                   return parseHTML(response);
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (JSONException e) {
                   e.printStackTrace();
               }

               return "Doslo je do neke neocekivane greske!";
           }

           @Override
           protected void onPostExecute(String s) {
               TextView rezultati = (TextView) findViewById(R.id.rezultati);
               rezultati.setText(parseResultString(s, pitanja));
               super.onPostExecute(s);
           }
       };
       task.execute(url);
//TODO: dovrsi ovo za prelazak na novi activity, posalji pitanja ARrayList
//       Intent i = new Intent(this,QuestionsActivity.class);
//       Bundle b = new Bundle();
//       b.putInt("stranica",1);
//       i.putExtras(b);
//       startActivity(i);
    }

    private String parseResultString(String s, ArrayList<QuestionModel> pitanja) {

        s = s.substring(15);
        int questionNumber=1;
        String parametar;
        String sadrzaj;

        ArrayList<String> lista = new ArrayList<>();
        for(String red : s.split("[\\n]+")){
            parametar = red.substring(0,red.indexOf(':')).toLowerCase();
            sadrzaj = red.substring(red.indexOf(':')+1).trim();
            switch (parametar){
                case "magic":
                    lista.add(2,sadrzaj);
                    break;
                case "stealth":
                    lista.add(3,sadrzaj);
                    break;
                case "combat":
                    lista.add(1,sadrzaj);
                    break;
                default:
                    lista.add(0,sadrzaj);
                    break;
            }
            if(lista.size() == 4){
                System.out.println("Stancaj objekat");
                pitanja.add(new QuestionModel( lista.get(0), lista.get(1), lista.get(2), lista.get(3), questionNumber ) );
                questionNumber++;
                lista.clear();
            }

        }

        String rezultat = "";

        for(QuestionModel pitanje : pitanja){
            rezultat += pitanje.toString();
        }
        return rezultat;
    }

    public String parseHTML(String string_to_parse) throws JSONException {

        String jsonString = parseJSON(new JSONObject(string_to_parse));

        return Html.fromHtml(jsonString).toString();
    }

    public String parseJSON(JSONObject json) throws JSONException {

        return json.getJSONObject("mobileview").getJSONArray("sections").getJSONObject(2).getString("text");
    }



}