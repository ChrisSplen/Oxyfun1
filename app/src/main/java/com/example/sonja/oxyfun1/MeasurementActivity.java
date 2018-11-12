package com.example.sonja.oxyfun1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MeasurementActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_measurement);
        if(savedInstanceState != null){ //wenn nicht null, also wenn die Aktivität schon am laufen war, dann soll mir die vorher gelaufene Zeit und der Status der running Variable zurückgegeben werden
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        runStopWatch();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Methode, um die Variablen running und seconds zu speichern, falls die Aktivität bei Drehung zerstört wird
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }


    //starte den Timer
    public void onClickStart(View view){
        Button button = (Button) findViewById(R.id.start_button);
        running = true;
    }

    //stoppe den Timer
    public void onClickStop(View view){
        Button button = (Button) findViewById(R.id.stop_button);
        running = false;
        if(seconds != 0) {//nur die Buttons anzeigen, wenn auch die Zeit gelaufen ist
            changeVisibility();
        }
    }

    private void changeVisibility(){ //wenn der User auf Stop klickt, sollen 2 Button erscheinen: Auswahl zum speichern und zum löschen
        Button button_save = (Button) findViewById(R.id.save_button);
        Button button_delete = (Button) findViewById(R.id.delete_button);
        button_save.setVisibility(View.VISIBLE);
        button_delete.setVisibility(View.VISIBLE);
    }

    public void onClickDeleteButton(View view){ //wenn User löschen klickt, dann Zeit auf 0 setzen und löschen/speichern Button auf invisible setzen
        Button button_delete = (Button) findViewById(R.id.delete_button);
        Button button_save = (Button) findViewById(R.id.save_button);
        seconds = 0;
        button_delete.setVisibility(View.INVISIBLE);
        button_save.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(this,"Daten werden gelöscht", Toast.LENGTH_LONG);
        toast.show();


    }

    public void onClickSaveButton(View view){ //bei Save sollen die Daten in die Datenbank gelangen und die Buttons wieder verschwinden, fall eine neue Messung gestartet werden soll
        Button button_delete = (Button) findViewById(R.id.delete_button);
        Button button_save = (Button) findViewById(R.id.save_button);
        seconds = 0;
        button_delete.setVisibility(View.INVISIBLE);
        button_save.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(this, "Daten werden gespeichert", Toast.LENGTH_LONG);
        toast.show();
        //hier muss die Übermittlung der Daten in die Datenbank erfolgen
    }

    //gib mir Nachricht, ob Sensor verbunden ist oder nicht
    public void onClickConnect(View view){
        Button button = (Button) findViewById(R.id.sensor_button);
        //wenn Sensor aktiv, dann Meldung, dass alles passt, ansonsten Fehlermeldung
        //hier muss dann implementiert werden, wie die Verbindung zum Sensor funktioniert
        Toast toast = Toast.makeText(this,"Sensor erfolgreich verbunden", Toast.LENGTH_LONG);
        toast.show();
    }


    private void runStopWatch(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();//Handler sorgt dafür, dass TextView immer upgedated wird
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600; //definieren der Variablen für die Anzeige von Stunden, Minuten und Sekunden
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String timer = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);//Anzeigen des Zeitformates im TextView
                timeView.setText(timer);//setzen des Zeit im TextView

                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000); //Handler durchläuft die run() Methode alle 1000ms, also immer 1 sec
            }

        });
    }
}
