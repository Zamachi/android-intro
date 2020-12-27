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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void getQClassAnswers(ArrayList<ClassesModel> rezultati, String url, TextView asd,ArrayList<String> odabrani_odgovori, ClassesModel odabrana_klasa){
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
                Pattern pattern = Pattern.compile("[A-Z][a-z]+(Any|0-6|[0-7]){3}");
                Pattern ime = Pattern.compile("[A-Z][a-z]+");
                Pattern parametar = Pattern.compile("(Any|0-6|[0-9]){1}");

                Matcher matcher = pattern.matcher(s);
                Matcher matcherIme;
                Matcher matcherParametar;

                String class_name, combat, magic,stealth, klasa;
                String resenje = "";
                int redni_broj = 0;
                while(matcher.find()){
                    klasa = matcher.group(0);

                    matcherIme = ime.matcher(klasa);
                    matcherIme.find();

                    class_name = matcherIme.group(0);

                    matcherParametar = parametar.matcher(klasa);
                    matcherParametar.find();
                    combat = matcherParametar.group(0);
                    matcherParametar.find();
                    magic = matcherParametar.group(0);
                    matcherParametar.find();
                    stealth = matcherParametar.group(0);

                    rezultati.add(new ClassesModel(class_name,combat,magic,stealth));
                    //resenje += rezultati.get(redni_broj++).toString();
                }
                pronadjiKlasu(odabrani_odgovori,rezultati, odabrana_klasa);
                super.onPostExecute(s);
            }
        };
        task.execute(url);
    }

    private static ClassesModel pronadjiKlasu(ArrayList<String> odabrani_odgovori, ArrayList<ClassesModel> rezultati, ClassesModel odabrana_klasa) {
        int A=0,B=0,C=0;
        for (String odgovor: odabrani_odgovori
             ) {
            if(odgovor.equals("A"))
                A++;
            else if(odgovor.equals("B"))
                B++;
            else
                C++;
        }

        return proveriIspravnost(rezultati,A,B,C,odabrana_klasa);
    }

    private static ClassesModel proveriIspravnost(ArrayList<ClassesModel> rezultati, int a, int b, int c,ClassesModel odabrana_klasa) {
        ClassesModel odabrani = null;
        boolean is_pronadjen = false;
        for (ClassesModel cm:rezultati
             ) {
            if(cm.getCombat().equals(""+a) && cm.getMagic().equals(""+b) && cm.getStealth().equals(""+c)){
                odabrani = cm;
                odabrana_klasa.setClass_name(odabrani.getClass_name());
                odabrana_klasa.setCombat(odabrani.getCombat());
                odabrana_klasa.setMagic(odabrani.getMagic());
                odabrana_klasa.setStealth(odabrani.getStealth());
                is_pronadjen = true;
            }
        }
        if(!is_pronadjen){
            if(a==7)
                odabrani = getWarrior(rezultati, odabrana_klasa);
            else if(b==7)
                odabrani = getMage(rezultati, odabrana_klasa);
            else if(c==7)
                odabrani = getThief(rezultati, odabrana_klasa);
            else
                odabrani = getRogue(rezultati, odabrana_klasa);
        }
        return odabrani;
    }

    private static ClassesModel getRogue(ArrayList<ClassesModel> rezultati,ClassesModel odabrana_klasa) {
        for (ClassesModel rogue:rezultati
             ) {
            if(rogue.getClass_name().toLowerCase().equals("rogue")){
                odabrana_klasa.setClass_name(rogue.getClass_name());
                odabrana_klasa.setCombat(rogue.getCombat());
                odabrana_klasa.setMagic(rogue.getMagic());
                odabrana_klasa.setStealth(rogue.getStealth());
                return rogue;
            }
        }
        return null;
    }

    private static ClassesModel getThief(ArrayList<ClassesModel> rezultati,ClassesModel odabrana_klasa) {
        for (ClassesModel thief:rezultati
        ) {
            if(thief.getClass_name().toLowerCase().equals("thief")){
                odabrana_klasa.setClass_name(thief.getClass_name());
                odabrana_klasa.setCombat(thief.getCombat());
                odabrana_klasa.setMagic(thief.getMagic());
                odabrana_klasa.setStealth(thief.getStealth());
                return thief;
            }
        }
        return null;
    }

    private static ClassesModel getMage(ArrayList<ClassesModel> rezultati,ClassesModel odabrana_klasa) {
        for (ClassesModel mage:rezultati
        ) {
            if(mage.getClass_name().toLowerCase().equals("mage")){
                odabrana_klasa.setClass_name(mage.getClass_name());
                odabrana_klasa.setCombat(mage.getCombat());
                odabrana_klasa.setMagic(mage.getMagic());
                odabrana_klasa.setStealth(mage.getStealth());
                return mage;
            }
        }
        return null;
    }

    private static ClassesModel getWarrior(ArrayList<ClassesModel> rezultati,ClassesModel odabrana_klasa) {
        for (ClassesModel warrior:rezultati
        ) {
            if(warrior.getClass_name().toLowerCase().equals("warrior")){
                odabrana_klasa.setClass_name(warrior.getClass_name());
                odabrana_klasa.setCombat(warrior.getCombat());
                odabrana_klasa.setMagic(warrior.getMagic());
                odabrana_klasa.setStealth(warrior.getStealth());
                return warrior;
            }
        }
        return null;

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
