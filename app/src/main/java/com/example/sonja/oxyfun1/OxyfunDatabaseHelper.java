package com.example.sonja.oxyfun1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OxyfunDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "oxyfun"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database

    OxyfunDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
        insertData(db, "Messung1", 200, 80, 10, 200, 10);
    }

    private static void insertData(SQLiteDatabase db,
                                   String name,
                                   Integer distance,
                                   Integer heartrate,
                                   Integer altitude,
                                   Integer time,
                                   Integer speed) {
        ContentValues dataValues = new ContentValues(); //ContentValue Objekte sind ein Datenset; wird zum Befüllen einer Zeile verwendet
        dataValues.put("Name", name); //Name=Spalte; name=Wert der eingefügt werden soll
        dataValues.put("Distance", distance);
        dataValues.put("Heartrate", heartrate);
        dataValues.put("Altitude", altitude);
        dataValues.put("Time", time);
        dataValues.put("Speed", speed);
        db.insert("Messungen", null, dataValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE Messungen (_id INTEGER PRIMARY KEY , "
                    + "Name TEXT, "
                    + "Distance INTEGER,"
                    + "Heartrate INTEGER,"
                    + "Altitude INTEGER,"
                    + "Time Integer,"
                    + "Speed INTEGER);");
            insertData(db, "Messung1", 200, 80, 10, 200, 10);
            insertData(db, "Messung2", 200, 80, 10, 200, 10);
            insertData(db, "Messung3", 200, 80, 10, 200, 10);
            insertData(db, "Messung4", 200, 80, 10, 200, 10);

            //insertData(db, "Messung", array2string(), array2string(), array2string(), array2string(), array2string());



        }
        if (oldVersion < 2) {
            //db.execSQL("ALTER TABLE Messungen ADD COLUMN unbekannt;");
        }
    }
    private String array2string(int[] array){
        String arraystring=String.valueOf(array[0]);
        for(int i=1;i<array.length;i++){
            arraystring+=","+ String.valueOf(array[i]);
        }
        return arraystring;
    }
}