package com.example.sonja.oxyfun1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.size;
import static org.apache.xmlbeans.impl.piccolo.xml.AttributeDefinition.ID;

import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class GraphActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "ID"; //ist eine Konstante

    //int id=(Integer)getIntent().getExtras().get(EXTRA_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Log.d("asdf",EXTRA_ID);



        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //read_csv();
        read_excel();

        int hr_avg = 0;
        int hr_sum = 0;
        for (int i = 0; i < 998; i++) {
            hr_sum += track_sample.get(i).getHr();
        }
        hr_avg = hr_sum / 1000;

        GraphView graph = (GraphView) findViewById(R.id.graph);


        DataPoint[] avg_line = new DataPoint[998];

        DataPoint[] track_array = new DataPoint[998];
        DataPoint[] track_altitude = new DataPoint[998];
        for (int i = 0; i <= 998 - 1; i++) {
            track_array[i] = new DataPoint(track_sample.get(i).getDistance(), track_sample.get(i).getHr());
            Log.d("asdf","track: "+String.valueOf(track_sample.get(i).getDistance())+" "+String.valueOf(track_sample.get(i).getHr()));
            track_altitude[i] = new DataPoint(track_sample.get(i).getDistance(), track_sample.get(i).getAltitude());
            avg_line[i] = new DataPoint(track_sample.get(i).getDistance(), hr_avg);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(track_array);
  /*
        DataPoint[] avg_line = new DataPoint[track_sample.size()];
        DataPoint[] track_array = new DataPoint[track_sample.size()];
        DataPoint[] track_altitude = new DataPoint[track_sample.size()];
        DataPoint[] track_distance = new DataPoint[track_sample.size()];

        for (int i = 0; i <= track_sample.size() - 1; i++) {
            track_array[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getHr());
            track_altitude[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getAltitude());
            track_distance[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getDistance());

            avg_line[i] = new DataPoint(track_sample.get(i).getDistance(), hr_avg);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(track_array);
        LineGraphSeries<DataPoint> series_distance = new LineGraphSeries<>(track_distance);
        */
        LineGraphSeries<DataPoint> series_altitude = new LineGraphSeries<>(track_altitude);
        LineGraphSeries<DataPoint> series_avg = new LineGraphSeries<DataPoint>(avg_line);


        graph.setTitle("Heart-Rate Track");
        graph.setTitleTextSize(80);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(100);
        graph.getViewport().setMaxY(220);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(300);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        series.setColor(Color.RED);
        series_avg.setColor(Color.GREEN);

        graph.addSeries(series);
        graph.addSeries(series_avg);
        /*
        graph.getSecondScale().addSeries(series_distance);
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(1200);
        */
        //Je nach Strecke ist die Anzeige von Höhe an der Y-Achse deutlich sinnvoller. Die meisten Streckenbeispiele sind allerdings
        // auf ebenen Strecken gemessen, wodurch die Höhenanzeige deutlich langweilig ist.
        //die Datei dist_hr und deren Abwandlung dist_hr1 (mit Zeitwerten) haben jedoch ein variiertes Höhenprofil
///*
        graph.getSecondScale().addSeries(series_altitude);
        graph.getSecondScale().setMinY(175);
        graph.getSecondScale().setMaxY(490);
//*/




        //hier folgt Code für die Datenbank; ist nur eine Spielerei
        /*
        //Create a cursor

        TextView test = (TextView) findViewById(R.id.textView); //zum Testen wo Fehler liegt, da ich keinen Plan vom richtigen Debuggen habe
        SQLiteOpenHelper oxyfunDatabaseHelper = new OxyfunDatabaseHelper(this);
        try {
            SQLiteDatabase db = oxyfunDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("Messungen",
                    new String[]{"Date"},
                    "_id = ?",
                    new String[]{Integer.toString(1)},
                    null, null, null);
            //Move to the first record in the Cursor

            if (cursor.moveToFirst()) {
//Get the details from the cursor
                String datum = cursor.getString(0);
                test.setText(datum);
            }
            test.setText(Boolean.toString(cursor.moveToFirst()));
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        oxyfunDatabaseHelper.close();
        this.deleteDatabase("oxyfun"); //hier wird die Datenbank gelöscht, ist hilfreich wenn man nicht immer neue Versionsnummern macht beim testen
        */

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public List<HR_Sample> track_sample = new ArrayList<>();


    public void read_csv() {
        //InputStream is = getResources().openRawResource(R.raw.dist_hr);
        InputStream is = getResources().openRawResource(R.raw.dist_hr1);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(";");


                HR_Sample sample = new HR_Sample();
                sample.setDistance(Integer.parseInt(tokens[0]));
                sample.setHr(Integer.parseInt(tokens[1]));
                sample.setAltitude(Integer.parseInt(tokens[2]));
                sample.setTime(Integer.parseInt(tokens[3]));
                sample.setSpeed(Integer.parseInt(tokens[4]));
                track_sample.add(sample);

                Log.d("MyActivity", "Just created " + sample);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf("MyActivity", "Error Reading File" + line, e);
            e.printStackTrace();
        }
    }
    public void read_excel(){

        int id=(Integer)getIntent().getExtras().get(EXTRA_ID);
        SQLiteOpenHelper oxyfunDatabaseHelper = new OxyfunDatabaseHelper(this);
        try {
            SQLiteDatabase db = oxyfunDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("Messungen",
                    new String[]{"Distance","Heartrate","Altitude","Speed"},
                    "_id = ?",
                    new String[]{Integer.toString(id)},
                    null, null, null);
            for(int i=0;i<1000;i++){
                HR_Sample sample = new HR_Sample();

                if (cursor.moveToFirst()) {
//Get the details from the cursor

                    sample.setDistance(string2array(cursor.getString(0))[i]);
                    sample.setHr(string2array(cursor.getString(1))[i]);
                    sample.setAltitude(0);
                    //sample.setSpeed(string2array(cursor.getString(3))[i]);
                    //
                    //Log.d("asdf",String.valueOf(string2array(cursor.getString(0))[i]));
                   // Log.d("asdf",String.valueOf(sample.getDistance()));
                }
                track_sample.add(sample);
                Log.d("asdf", "Just created " + sample);
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        oxyfunDatabaseHelper.close();
        //this.deleteDatabase("oxyfun"); //hier wird die Datenbank gelöscht, ist hilfreich wenn man nicht immer neue Versionsnummern macht beim testen


    }

    public int[] string2array(String arraystring){

        String[] stringarray=arraystring.split(",");
        int[] array=new int[arraystring.length()];
        double[] double_array=new double[arraystring.length()];
        for (int i=0; i<1000;i++){

            double_array[i]=Double.valueOf(stringarray[i]);
            array[i]=(int)(double_array[i]);

        }
        return array;
    }

}