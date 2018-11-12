package com.example.sonja.oxyfun1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                Intent intent=new Intent(this, MeasurementActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_view_measurement:
                Intent intent3=new Intent(this, EntryActivity.class);
                startActivity(intent3);
                return true;
            case R.id.action_to_home:
                Intent intent2=new Intent(this,MainActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Button Click startet die Messungsaktivität
    public void onstartReadingButtonClick(View view){
        Button button = (Button) findViewById(R.id.startReadingButton);
        Intent intent = new Intent(this, MeasurementActivity.class);
        startActivity(intent);
    }

    //Button startet die Liste mit den Einträgen der Aktivitäten
    public void onstartEntryListButton(View view){
        Button button = (Button) findViewById(R.id.startEntryListButton);
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

    public void onstartGraphViewButton(View view){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }
}
