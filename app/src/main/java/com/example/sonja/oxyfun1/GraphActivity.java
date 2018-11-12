package com.example.sonja.oxyfun1;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class GraphActivity extends AppCompatActivity {

    public static final String EXTRA_ACTIVITYID = "activityid"; //ist eine Konstante


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        read_csv();
        int hr_avg=0;
        int hr_sum=0;
        for(int i=0;i<track_sample.size();i++){
            hr_sum+=track_sample.get(i).getHr();
        }
        hr_avg=hr_sum/track_sample.size();

        GraphView graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] avg_line=new DataPoint[track_sample.size()];
        DataPoint[] track_array=new DataPoint[track_sample.size()];
        DataPoint[] track_altitude=new DataPoint[track_sample.size()];
        for(int i=0;i<=track_sample.size()-1;i++){
            track_array[i]=new DataPoint(track_sample.get(i).getDistance(),track_sample.get(i).getHr());
            track_altitude[i]=new DataPoint(track_sample.get(i).getDistance(),track_sample.get(i).getAltitude());
            avg_line[i]=new DataPoint(track_sample.get(i).getDistance(),hr_avg);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(track_array);
        LineGraphSeries<DataPoint> series_altitude=new LineGraphSeries<>(track_altitude);
        LineGraphSeries<DataPoint> series_avg = new LineGraphSeries<DataPoint>(avg_line);



        graph.setTitle("Heart-Rate Track");
        graph.setTitleTextSize(80);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(100);
        graph.getViewport().setMaxY(220);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(80);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        series.setColor(Color.RED);
        series_avg.setColor(Color.GREEN);

        graph.addSeries(series);
        graph.addSeries(series_avg);
        graph.getSecondScale().addSeries(series_altitude);
        graph.getSecondScale().setMinY(175);
        graph.getSecondScale().setMaxY(490);

    }

    public List<HR_Sample> track_sample =new ArrayList<>();



        public void read_csv() {
            InputStream is = getResources().openRawResource(R.raw.dist_hr);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );

            String line="";
            try {
                while ((line= bufferedReader.readLine())!=null) {
                    String[] tokens = line.split(";");


                    HR_Sample sample = new HR_Sample();
                    sample.setDistance(Integer.parseInt(tokens[0]));
                    sample.setHr(Integer.parseInt(tokens[1]));
                    sample.setAltitude(Integer.parseInt(tokens[2]));
                    track_sample.add(sample);

                    Log.d("MyActivity", "Just created " + sample);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.wtf("MyActivity", "Error Reading File" + line, e);
                e.printStackTrace();
            }
        }



            //Log.i("MyActivityClass",Arrays.toString(track_sample()));




    }


