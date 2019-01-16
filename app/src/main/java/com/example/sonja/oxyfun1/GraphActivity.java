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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
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

import static java.nio.file.Files.createLink;
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
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Switch altitudeSwitch = findViewById(R.id.AltitudeSwitch);
        final Switch speedSwitch = findViewById(R.id.SpeedSwitch);
        final Switch distanceSwitch = findViewById(R.id.DistanceSwitch);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //read_csv();
        read_excel();

        int hr_avg = 0;
        int hr_sum = 0;
        for (int i = 0; i < 998; i++) {
            hr_sum += track_sample.get(i).getHr();
        }
        hr_avg = hr_sum / 1000;

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        final GridLabelRenderer gridLabel = graph.getGridLabelRenderer();


/*
        DataPoint[] avg_line = new DataPoint[998];
        DataPoint[] track_array = new DataPoint[998];
        DataPoint[] track_altitude = new DataPoint[998];
        DataPoint[] track_distance = new DataPoint[998];
        DataPoint[] track_speed = new DataPoint[998];

        for (int i = 0; i <= 998 - 1; i++) {
            track_array[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getHr());
            Log.d("asdf","track: "+String.valueOf(track_sample.get(i).getDistance())+" "+String.valueOf(track_sample.get(i).getHr()));
            track_altitude[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getAltitude());
            track_distance[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getDistance());
            track_speed[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getSpeed());
            avg_line[i] = new DataPoint(track_sample.get(i).getTime(), hr_avg);
        }
        /*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(track_array);
        series.setTitle("HR");
  */

        DataPoint[] avg_line = new DataPoint[track_sample.size()];
        DataPoint[] track_array = new DataPoint[track_sample.size()];
        DataPoint[] track_altitude = new DataPoint[track_sample.size()];
        DataPoint[] track_distance = new DataPoint[track_sample.size()];
        DataPoint[] track_speed = new DataPoint[track_sample.size()];

        for (int i = 0; i <= track_sample.size() - 1; i++) {
            track_array[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getHr());
            Log.d("asdf","track: "+String.valueOf(track_sample.get(i).getDistance())+" "+String.valueOf(track_sample.get(i).getHr()));
            track_altitude[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getAltitude());
            track_distance[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getDistance());
            track_speed[i] = new DataPoint(track_sample.get(i).getTime(), track_sample.get(i).getSpeed());
            avg_line[i] = new DataPoint(track_sample.get(i).getTime(), hr_avg);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(track_array);
        series.setTitle("HR");
        final LineGraphSeries<DataPoint> series_distance = new LineGraphSeries<>(track_distance);
        final LineGraphSeries<DataPoint> series_altitude = new LineGraphSeries<>(track_altitude);
        final LineGraphSeries<DataPoint> series_speed = new LineGraphSeries<DataPoint>(track_speed);
        LineGraphSeries<DataPoint> series_avg = new LineGraphSeries<DataPoint>(avg_line);
        series_altitude.setTitle("Altitude");
        series_avg.setTitle("avg HR");

        graph.setTitle("Heart Rate Track");
        graph.setTitleTextSize(50);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(100);
        graph.getViewport().setMaxY(220);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(300);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("time [s]");
        graph.getGridLabelRenderer().setVerticalAxisTitle("heart rate [bpm]");

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        series.setColor(Color.RED);
        series_avg.setColor(Color.RED);
        series_speed.setColor(Color.BLACK);
        series_altitude.setColor(Color.BLUE);

        graph.addSeries(series);
        graph.addSeries(series_avg);
        //graph.getGridLabelRenderer().setNumVerticalLabels(13);


        altitudeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                graph.clearSecondScale();
                if(!isChecked){
                    graph.getSecondScale().removeSeries(series_altitude);
                    speedSwitch.setClickable(true);
                    distanceSwitch.setClickable(true);
                }

                if(isChecked) {
                    speedSwitch.setClickable(false);
                    distanceSwitch.setClickable(false);
                    distanceSwitch.setChecked(false);
                    graph.getSecondScale().removeSeries(series_distance);
                    graph.getSecondScale().removeSeries(series_speed);
                    speedSwitch.setChecked(false);

                    graph.getSecondScale().addSeries(series_altitude);
                    graph.getSecondScale().setMinY(175);
                    graph.getSecondScale().setMaxY(380);

                    series_altitude.setTitle("altitude [m]");
                    graph.getSecondScale().setVerticalAxisTitle("altitude \n [m]");
                }
            }
        });
        speedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                graph.clearSecondScale();
                if(!isChecked){
                    graph.getSecondScale().removeSeries(series_speed);
                    distanceSwitch.setClickable(true);
                    altitudeSwitch.setClickable(true);
                }

                if(isChecked) {
                    distanceSwitch.setClickable(false);
                    altitudeSwitch.setClickable(false);
                    distanceSwitch.setChecked(false);
                    altitudeSwitch.setChecked(false);
                    graph.getSecondScale().removeSeries(series_distance);
                    graph.getSecondScale().removeSeries(series_altitude);

                    graph.getSecondScale().addSeries(series_speed);
                    graph.getSecondScale().setMinY(0);
                    graph.getSecondScale().setMaxY(4.5);
                    series_speed.setTitle("speed [m/s]");
                    graph.getSecondScale().setVerticalAxisTitle("speed \n [m/s]");
                }
            }
        });
        distanceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                graph.clearSecondScale();
                if(!isChecked){
                    graph.getSecondScale().removeSeries(series_distance);
                    speedSwitch.setClickable(true);
                    altitudeSwitch.setClickable(true);
                }
                if(isChecked) {
                    speedSwitch.setClickable(false);
                    altitudeSwitch.setClickable(false);
                    speedSwitch.setChecked(false);
                    altitudeSwitch.setChecked(false);
                    graph.getSecondScale().removeSeries(series_speed);
                    graph.getSecondScale().removeSeries(series_altitude);

                    graph.getSecondScale().getLabelFormatter();
                    graph.getSecondScale().addSeries(series_distance);
                    graph.getSecondScale().setMinY(0);
                    graph.getSecondScale().setMaxY(10000);
                    series_distance.setTitle("distance [m]");
                    graph.getSecondScale().setVerticalAxisTitle("distance\n [m]");
                }
            }
        });

        graph.getLegendRenderer().setPadding(10);
        graph.getLegendRenderer().setTextSize(25);
        graph.getLegendRenderer().setWidth(178);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getGridLabelRenderer().setSecondScaleLabelVerticalWidth(200);
        graph.getGridLabelRenderer().setHumanRounding(true);
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
        InputStream is = getResources().openRawResource(R.raw.dist_hr_speed_alt_time_trenn);
        //InputStream is = getResources().openRawResource(R.raw.dist_hr1);
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
                sample.setSpeed(Double.parseDouble(tokens[4]));
                track_sample.add(sample);

                Log.d("MyActivity", "Just created " + sample);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf("MyActivity", "Error Reading File" + line, e);
            e.printStackTrace();
        }
    }

    //public void OnChecked


    public void read_excel(){
        int id=(Integer)getIntent().getExtras().get(EXTRA_ID);
        SQLiteOpenHelper oxyfunDatabaseHelper = new OxyfunDatabaseHelper(this);
        try {
            SQLiteDatabase db = oxyfunDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("Messungen",
                    new String[]{"Distance","Heartrate","Altitude","Speed","Time"},
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
                    sample.setSpeed(string2array_double(cursor.getString(3))[i]);
                    sample.setTime(i+1);
                    //

                    Log.d("wtf",String.valueOf(string2array(cursor.getString(0))[i]));
                   // Log.d("asdf",String.valueOf(sample.getDistance()));
                }
                track_sample.add(sample);
                Log.d("asdf", "Just created " + sample);
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            toast = Toast.makeText(this,
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
    public double[] string2array_double(String arraystring){

        String[] stringarray=arraystring.split(",");
        double[] array=new double[arraystring.length()];
        for (int i=0; i<1000;i++){
            array[i]=Double.valueOf(stringarray[i]);
        }
        return array;
    }
}