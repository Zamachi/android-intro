package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

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

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static String url = "https://en.uesp.net//w/api.php?action=mobileview&format=json&page=Morrowind%3AClass_Quiz&sections=1-&prop=text%7Csections";

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
               rezultati.setText(s);
               super.onPostExecute(s);
           }
       };
       task.execute(url);
    }

    public String parseHTML(String string_to_parse) throws JSONException {

        String jsonString = parseJSON(new JSONObject(string_to_parse));

        return Html.fromHtml(jsonString).toString();
    }

    public String parseJSON(JSONObject json) throws JSONException {

        return json.getJSONObject("mobileview").getJSONArray("sections").getJSONObject(2).getString("text");
    }
}