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
                    response = readFromURL(strings[0]);
                    return parseHTML(response,0);
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
                parseResultString(s,pitanja);
                super.onPostExecute(s);
            }
        };
        task.execute(url);
    }

    public static void getQClassAnswers(TextView rezultati, String url){
        AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response="";
                try {
                    response = readFromURL(strings[0]);
                    return parseHTML(response,1);
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
                rezultati.setText(s);
                super.onPostExecute(s);
            }
        };
        task.execute(url);
    }

    private static String readFromURL(String url) throws IOException {
        String response = "";

        URL link = new URL(url);
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


        return response;
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

    public static String parseHTML(String string_to_parse, int kontrolna_cifra) throws JSONException {

        String jsonString = kontrolna_cifra != 1 ? parseJSONQuestions(new JSONObject(string_to_parse)) : parseJSONAnswers(new JSONObject(string_to_parse));

        return  Html.fromHtml(jsonString).toString();
    }

    public static String parseJSONQuestions(JSONObject json) throws JSONException {

        return json.getJSONObject("mobileview").getJSONArray("sections").getJSONObject(2).getString("text");
    }

    public static String parseJSONAnswers(JSONObject json) throws JSONException {

       String sadrzaj = json.getJSONObject("mobileview").getJSONArray("sections").getJSONObject(1).getString("text");


       return sadrzaj;
    }
}
