package com.example.project;

import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class APIComms {

    public static void getQuizQuestions( ArrayList<QuestionModel> pitanja , String url){
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
        //TODO: OVDE TREBA ODRADITI NESTO
                super.onPostExecute(s);
            }
        };
        task.execute(url);
    }

    private static String parseResultString(String s, ArrayList<QuestionModel> pitanja) {

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

    public static String parseHTML(String string_to_parse) throws JSONException {

        String jsonString = parseJSON(new JSONObject(string_to_parse));

        return Html.fromHtml(jsonString).toString();
    }

    public static String parseJSON(JSONObject json) throws JSONException {

        return json.getJSONObject("mobileview").getJSONArray("sections").getJSONObject(2).getString("text");
    }
}
