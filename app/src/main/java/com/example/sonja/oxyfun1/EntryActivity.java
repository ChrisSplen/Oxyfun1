package com.example.sonja.oxyfun1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks);
        ListView listDrinks = (ListView) findViewById(R.id.list_activities);
        listDrinks.setAdapter(listAdapter);

        //Create the listener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id) {
//Pass the drink the user clicks on to DrinkActivity
                        Intent intent = new Intent(EntryActivity.this,
                                GraphActivity.class);
                        intent.putExtra(GraphActivity.EXTRA_ACTIVITYID, (int) id);
                        startActivity(intent);
                    }
                };
//Assign the listener to the list view
        listDrinks.setOnItemClickListener(itemClickListener);

        /*
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int position,
                                    long id) {
                if (position == 0) {
                    Intent intent = new Intent(EntryActivity.this, GraphActivity.class);
                    startActivity(intent);
                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_activities);
        listView.setOnItemClickListener(itemClickListener);
*/
/*
        //Adapter, um Daten aus dem Array der ListView zuzuweisen
        ArrayAdapter<Entry> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, Entry.activities);
        ListView listActivities = (ListView) findViewById(R.id.list_activities);
        listActivities.setAdapter(listAdapter);

        //Erstellung des OnItemClickListeners
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Gib dem Intent eine extra Info zu dem Item, das geklickt wurde, damit beide Aktiviäten wissen, welches Item geklickt wurde
                Intent intent = new Intent(EntryActivity.this, GraphActivity.class);
                intent.putExtra(GraphActivity.EXTRA_ACTIVITYID, (int) id);
                startActivity(intent);
            }
        };

        //listener zur listview setzen
        listActivities.setOnItemClickListener(itemClickListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_to_add_measurement:
                Intent intent1=new Intent(this, MeasurementActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_to_home:
                Intent intent2=new Intent(this,MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_to_view_measurement:
                Intent intent3=new Intent(this, EntryActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
