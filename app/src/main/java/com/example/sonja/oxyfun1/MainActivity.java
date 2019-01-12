package com.example.sonja.oxyfun1;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //need to check the permissions
        checkFilePermissions();
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
                intent=new Intent(this, MeasurementActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_home:
                intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_to_view_measurement:
                intent=new Intent(this, EntryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Button Click startet die Messungsaktivität
    public void onstartReadingButtonClick(View view){
        Button button =  findViewById(R.id.startReadingButton);
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

    public void onstartXCL(View view){
        Intent intent = new Intent(this, XCL_Loader.class);
        startActivity(intent);
    }
    private void checkFilePermissions() {

         this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number

        }
    }

