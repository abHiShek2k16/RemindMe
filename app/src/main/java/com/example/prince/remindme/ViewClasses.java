package com.example.prince.remindme;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ViewClasses extends Activity implements AdapterView.OnItemSelectedListener{
    ListView listView;
    Spinner day;
    Context context;
    SQLiteDatabase myDatabase;
    android.database.Cursor cursor;
    Cursor adapter;
    Button back;
    Switch enables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);
        this.context = this;

        day=(Spinner)findViewById(R.id.spinner2);

        if (day != null) {
            day.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);
        }

        List<String> daysOfWeek=new ArrayList<String>();
        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,daysOfWeek);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day.setAdapter(dataAdapter);

        Intent getIntent=getIntent();
        int x=getIntent.getIntExtra("day",0);
        day.setSelection(x);

        listView = (ListView) findViewById(R.id.listView2);

        Database handler = new Database(this);

        myDatabase = handler.getWritableDatabase();

        String[] FROM={"_id","day","class_name","batch_name","location","start_time","end_time","enable"};
        cursor=myDatabase.query("Demo",FROM,"day="+day.getSelectedItemPosition(),null,null,null,null);

        enables=(Switch)findViewById(R.id.enable_disable2);
        enables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                if (enables != null && !enables.isChecked()) {
                    values.put("enable", 0);
                    Toast.makeText(context,"Just Relaxed You are now on leave for a day....!!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    values.put("enable", 1);
                    Toast.makeText(context,"Now It's Working Time get ready...!!! ",Toast.LENGTH_SHORT).show();
                }
                myDatabase.update("Demo", values, "day=" + day.getSelectedItemPosition(), null);
                String[] FROM={"_id","day","class_name","batch_name","location","start_time","end_time","enable"};
                android.database.Cursor cursorTemp=myDatabase.query("Demo",FROM,"day="+day.getSelectedItemPosition(),null,null,null,null);
                adapter.changeCursor(cursorTemp);
                Intent changeAlarmStatus=new Intent(context,ResetAlarm.class);
                changeAlarmStatus.putExtra("ref",0);
                changeAlarmStatus.putExtra("day",day.getSelectedItemPosition());
                context.startService(changeAlarmStatus);
            }
        });


        adapter = new Cursor(this, cursor);
        listView.setAdapter(adapter);

        //setting up an itemclick listener for the listview object
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.database.Cursor mycursor=(android.database.Cursor)listView.getItemAtPosition(position);
                int rowid=mycursor.getInt(mycursor.getColumnIndexOrThrow("_id"));
                Intent myIntent=new Intent(context,EditClass.class);
                myIntent.putExtra("rowid",rowid);
                finish();
                startActivity(myIntent);
            }
        });

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] FROM={"_id","day","class_name","batch_name","location","start_time","end_time","enable"};
        android.database.Cursor cursorTemp=myDatabase.query("Demo",FROM,"day="+day.getSelectedItemPosition(),null,null,null,null);
        adapter.changeCursor(cursorTemp);
        enables.setChecked(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //DO Nothing
    }

}

