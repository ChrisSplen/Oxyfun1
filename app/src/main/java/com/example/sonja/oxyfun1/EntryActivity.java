package com.example.sonja.oxyfun1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EntryActivity extends AppCompatActivity {
    //Code ist noch in Arbeit und nicht lauffähig
/*
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ListView listData = findViewById(R.id.list_activities);
        SQLiteOpenHelper oxyfunDatabaseHelper = new OxyfunDatabaseHelper(this);
        try {
            db = oxyfunDatabaseHelper.getWritableDatabase();
            cursor = db.query("datum",
                    new String[]{"Daten"},
                    null, null, null, null, null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"Datum"},
                    new int[]{android.R.id.text1},
                    0);
            listData.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
//Create the listener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id) {
                        Intent intent = new Intent(EntryActivity.this,
                                GraphActivity.class);
                        intent.putExtra(GraphActivity.EXTRA_ACTIVITYID, (int) id);
                        startActivity(intent);
                    }
                };

//Assign the listener to the list view
        listData.setOnItemClickListener(itemClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_to_add_measurement:
                intent = new Intent(this, MeasurementActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_view_measurement:
                intent = new Intent(this, EntryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks);
        ListView listDrinks = findViewById(R.id.list_activities);
        listDrinks.setAdapter(listAdapter);

        //Create the listener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id) {
                        Intent intent = new Intent(EntryActivity.this,
                                GraphActivity.class);
                        intent.putExtra(GraphActivity.EXTRA_ACTIVITYID, (int) id);
                        startActivity(intent);
                    }
                };

//Assign the listener to the list view
        listDrinks.setOnItemClickListener(itemClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_to_add_measurement:
                intent = new Intent(this, MeasurementActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_view_measurement:
                intent = new Intent(this, EntryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
