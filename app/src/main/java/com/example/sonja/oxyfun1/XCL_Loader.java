package com.example.sonja.oxyfun1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class XCL_Loader extends AppCompatActivity {

    private static final String TAG = "XCL_Loader";

    // Declare variables
    String[] FilePathStrings;
    String[] FileNameStrings;
    File[] listFile;
    File file;
    Button btnUpDirectory,btnSDCard;
    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;
    public ArrayList<Values> uploadData;
    ListView lvInternalStorage;
    double[] distance=new double[5000];
    double [] heartrate=new double[5000];
    double [] t=new double[5000];
    double [] v=new double[5000];
    String distance_str;
    String heartrate_str;
    String t_str;
    String v_str;
    String Datei;
    Toast toast;
    int startspalte;

    public static int name_nr=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcl_loader);
        lvInternalStorage = findViewById(R.id.lvInternalStorage);
        btnUpDirectory = findViewById(R.id.btnUpDirectory);
        btnSDCard = findViewById(R.id.btnViewSDCard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uploadData = new ArrayList<>();



        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);

                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);

                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

    }

    /**
     *reads the excel file columns then rows. Stores data as ExcelUploadData object
     */

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
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //declare input file
        File inputFile = new File(filePath);
        Datei=filePath;
        Log.d(TAG, "readExcelData: Filename " + Datei);
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            //int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();
            int rowsCount = sheet.getPhysicalNumberOfRows();


                Row row = sheet.getRow(0);
                for (int c = 0; c < 400; c++) {
                        String value = getCellAsString2(row, c, formulaEvaluator);
                        if (value.equals("Move samples"))
                        {startspalte=c;
                        Log.d(TAG, "Startspalte: " + startspalte);
                        break;}
                }


                for (int r = 2; r < 1500; r++) {
                 row = sheet.getRow(r);
                //int cellsCount = row.getPhysicalNumberOfCells();
                for (int c = startspalte; c < (startspalte+8); c++) {
                    if( c==startspalte || c==(startspalte+2) || c==(startspalte+3) || c==(startspalte+4)) {
                        if(getCellAsString(row, c, formulaEvaluator)!=null)
                        {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);

                        sb.append(value + ", ");}
                    }
                }
                sb.append(";");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

            parseStringBuilder(sb);

        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }

    /**
     * Method for parsing imported data and storing in ArrayList<Values>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder){
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(";");
        Log.d(TAG, "row split worked.");
        //Add to the ArrayList<Values> row by row
        for(int i=0; i<rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");
            Log.d(TAG, "col split worked.");
            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                Log.d(TAG, "ParseStringBuilder: Data from row: " + columns[1]);
                double dist = Double.parseDouble(columns[1]);
                Log.d(TAG, "dist parse worked.");
                double pulse = Double.parseDouble(columns[2]);
                Log.d(TAG, "pulse parse worked.");
                double time = Double.parseDouble(columns[0]);
                Log.d(TAG, "time parse worked.");
                double speed = Double.parseDouble(columns[3]);
                String cellInfo = "(dist, pulse, time, speed): (" + dist + "," + pulse + "," + time + "," +  speed + ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
                uploadData.add(new Values(dist, pulse, time, speed));

            }catch (NumberFormatException e){

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }
        }

        printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for(int i = 0; i< uploadData.size(); i++){
            double dist = uploadData.get(i).getDist();
            double pulse = uploadData.get(i).getPulse();
            double time = uploadData.get(i).getTime();
            double speed = uploadData.get(i).getSpeed();
            Log.d(TAG, "printDataToLog: (dist, pulse, time , speed): (" + dist + "," + pulse + "," + time +"," + speed + ")");
            distance[i] = uploadData.get(i).getDist();
            heartrate [i]= uploadData.get(i).getPulse();
            t [i]= uploadData.get(i).getTime();
            v[i] = uploadData.get(i).getSpeed();

        }
        //toast= Toast.makeText(this, Double.toString(v[50]), Toast.LENGTH_LONG);
        //toast.show();
        distance_str=array2string(distance);
        heartrate_str=array2string(heartrate);
        t_str=array2string(t);
        v_str=array2string(v);

        SQLiteOpenHelper oxyfunDatabaseHelper = new OxyfunDatabaseHelper(this);
        try {
            //this.deleteDatabase("oxyfun");
            SQLiteDatabase db = oxyfunDatabaseHelper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put("Name",Datei);
            contentValues.put("Distance",distance_str);
            contentValues.put("Heartrate",heartrate_str);
            contentValues.put("Altitude",0);
            contentValues.put("speed",v_str);
            long inserted=db.insert("Messungen",null, contentValues);
             toast = Toast.makeText(this,"data loaded successfully", Toast.LENGTH_LONG);
             toast.show();
        }catch(SQLiteException e) {
            toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * Returns the cell as a string from the excel file
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        Cell cell = row.getCell(c);
        CellValue cellValue = formulaEvaluator.evaluate(cell);
        try {
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();

                    value = "" + numericValue;

                    break;

                case Cell.CELL_TYPE_STRING:

                    String buffa = cellValue.getStringValue();
                    char[] chars = buffa.toCharArray();
                    String targetDate = new String(chars, 11, 8);
                    String[] tokens = targetDate.split(":");
                    int hours = Integer.parseInt(tokens[0]);
                    int minutes = Integer.parseInt(tokens[1]);
                    int seconds = Integer.parseInt(tokens[2]);
                    int duration = 3600 * hours + 60 * minutes + seconds;
                    value = "" + duration;
                    break;
                default:
            }

        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }
    private String getCellAsString2(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);

            if (cellValue.getStringValue() == "Move samples") {
                value = "Move samples";

            }
            else{value = "" + cellValue.getStringValue();}
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;}

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    private String array2string(double[] array){
        String arraystring=String.valueOf(array[0]);
        for(int i=1;i<array.length;i++){
            arraystring+=","+ String.valueOf(array[i]);
        }
        return arraystring;
    }


}