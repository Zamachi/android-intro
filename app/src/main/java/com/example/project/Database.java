package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_FILE_NAME = "saved_classes";

    public Database(Context context){super(context, DATABASE_FILE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT UNIQUE, %s INTEGER)",DBModel.TABLE_NAME,DBModel.FIELD_RESULT_ID,DBModel.FIELD_CLASS_NAME,DBModel.FIELD_OCCURENCE
        );

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DBModel.TABLE_NAME));
        onCreate(db);
    }

    public void addClassResult(String class_name, int occurence){
        //dohvatamo bazu u rezimu za upis
        SQLiteDatabase db = this.getWritableDatabase();
        //da bismo dodali nesto u bazu - potrebno nam  je da imamo set vrednosti
        ContentValues cv = new ContentValues(); //radi isto kao bundle, mapa sa parovima key, value
        cv.put(DBModel.FIELD_CLASS_NAME, class_name);
        cv.put(DBModel.FIELD_OCCURENCE, occurence);
        //insert u bazu
        db.insert(DBModel.TABLE_NAME, null, cv);
    }

    public void editClassResult(String class_name, int occurence){
        //potrebna baza u rezimu za upis, jer menjamo podatke, odnosno upisujemo nove vrednosti
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBModel.FIELD_CLASS_NAME, class_name);
        cv.put(DBModel.FIELD_OCCURENCE, occurence);
        //UPDATE contact (name, email, phone) VALUES (dato_name, dato_email, dato_phone) WHERE contact_id =  dato_contactId)
        db.update(DBModel.TABLE_NAME, cv, DBModel.FIELD_CLASS_NAME + " = ?", new String[] {String.valueOf(class_name)});
    }

    public void updateResult(String class_name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format(
                "UPDATE %s " +
                        "SET `%s` = `%s` +1 " +
                        "WHERE lower(`%s`) = lower('%s')",DBModel.TABLE_NAME,DBModel.FIELD_OCCURENCE,DBModel.FIELD_OCCURENCE,DBModel.FIELD_CLASS_NAME,class_name
        );

        db.execSQL(query);
    }

    public int deleteClassResult(int result_id){
        //ponovo azuriramo bazu, mora ici rezim za upis
        SQLiteDatabase db = this.getWritableDatabase();
        //DELETE FROM contact where contact_id = dato_contactId
        return db.delete(DBModel.TABLE_NAME,DBModel.FIELD_RESULT_ID + " = ?", new String[] {String.valueOf(result_id)});
    }

    public List<DBModel> getAllSavedClasses(){
        //db u read rezimu
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT * FROM contacts
        String query = String.format("SELECT * FROM %s ORDER BY %s DESC", DBModel.TABLE_NAME, DBModel.FIELD_OCCURENCE);
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst(); //pomeramo se na pocetak rezultata

        List<DBModel> lista = new ArrayList<>(result.getCount());

        //provera da li smo pomerili kursor iza poslednjeg rezultata
        while (!result.isAfterLast()){
            int result_id = result.getInt(result.getColumnIndex(DBModel.FIELD_RESULT_ID));
            String class_name = result.getString(result.getColumnIndex(DBModel.FIELD_CLASS_NAME));
            int occurence = result.getInt(result.getColumnIndex(DBModel.FIELD_OCCURENCE));
            DBModel post = new DBModel(result_id,class_name,occurence);
            lista.add(post);
            result.moveToNext();
        }
        return lista;

    }
}
