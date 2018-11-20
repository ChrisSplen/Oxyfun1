package com.example.sonja.oxyfun1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OxyfunDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "oxyfun"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database


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
    }

    private static void insertData(SQLiteDatabase db, Integer datum,
                                   Integer distance, Integer heartrate, Integer sauerstoffsaettigung) {
        ContentValues dataValues = new ContentValues(); //ContentValue Objekte sind ein Datenset; wird zum Befüllen einer Zeile verwendet
        dataValues.put("Datum", datum); //Datum=Spalte; datum=Wert der eingefügt werden soll
        dataValues.put("Distance", distance);
        dataValues.put("Heartrate", heartrate);
        dataValues.put("Sauerstoffsaettigung", sauerstoffsaettigung);
        db.insert("Messungen", null, dataValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {

            db.execSQL("CREATE TABLE Messungen (id INTEGER PRIMARY KEY , "
                    + "Date INTEGER, "
                    + "Distance INTEGER,"
                    + "Heartrate INTEGER,"
                    + "Sauerstoffsaettigung INTEGER);");
            insertData(db, 2011, 200, 80, 10);
        }
    }
}
