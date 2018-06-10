package com.example.prince.remindme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditClass extends Activity implements AdapterView.OnItemSelectedListener{

    Context context;


    EditText classN;
    EditText batchN;
    EditText loc;
    TextView start;
    TextView stop;

    Button cancel;
    Button delete;
    Button update;

    Switch enable;

    Cursor cursor;

    AlarmManager alarmManager;
    SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_database);
        this.context = this;

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        final Spinner day = (Spinner) findViewById(R.id.dayedit);

        if (day != null) {
            day.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);
        }

        List<String> daysOfWeek = new ArrayList<String>();
        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, daysOfWeek);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day.setAdapter(dataAdapter);

        final Intent getIntent = getIntent();
        final int rowid = getIntent.getIntExtra("rowid", 1);
        myDatabase = openOrCreateDatabase("Schedules", MODE_PRIVATE, null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Demo(_id INTEGER PRIMARY KEY AUTOINCREMENT,day VARCHAR,class_name VARCHAR,batch_name VARCHAR,location VARCHAR, start_time VARCHAR, end_time VARCHAR,enable INTEGER);");

        String[] FROM = {"_id", "day", "class_name", "batch_name", "location", "start_time", "end_time", "enable"};
        cursor = myDatabase.query("Demo", FROM, "_id=" + rowid, null, null, null, null);

        classN = (EditText) findViewById(R.id.subjectedit);
        batchN = (EditText) findViewById(R.id.batchedit);
        loc = (EditText) findViewById(R.id.classnameedit);
        start = (TextView) findViewById(R.id.start_time_edit);
        stop = (TextView) findViewById(R.id.end_time_edit);
        enable = (Switch) findViewById(R.id.enable_disable);

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                if (enable.isChecked()){
                    Calendar starts=getTime(start,day);
                    Calendar stops=getTime(stop,day);
                    if(starts!=null&&stops!=null){
                        Intent intent=new Intent(context,AlarmRecieverClass.class);
                        intent.putExtra("on_off",1);
                        intent.putExtra("id",Integer.valueOf(cursor.getString(0)));

                        Intent intent2=new Intent(context,AlarmRecieverClass.class);
                        intent2.putExtra("on_off",1);
                        intent2.putExtra("id",-(Integer.valueOf(cursor.getString(0))));

                        PendingIntent myPendingIntent1=PendingIntent.getBroadcast(context,Integer.valueOf(cursor.getString(0)),intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        PendingIntent myPendingIntent2=PendingIntent.getBroadcast(context,-(Integer.valueOf(cursor.getString(0))),intent2,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, starts.getTimeInMillis()-600000,999999999, myPendingIntent1);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, stops.getTimeInMillis()-600000,999999999, myPendingIntent2);
                    }
                    values.put("enable", 1);
                    Toast.makeText(context, "Now It's Working Time Reminder on...!!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(context,AlarmRecieverClass.class);
                    intent.putExtra("on_off",1);
                    intent.putExtra("id",Integer.valueOf(cursor.getString(0)));

                    Intent intent2=new Intent(context,AlarmRecieverClass.class);
                    intent2.putExtra("on_off",1);
                    intent2.putExtra("id",-(Integer.valueOf(cursor.getString(0))));

                    PendingIntent myPendingIntent1=PendingIntent.getBroadcast(context,Integer.valueOf(cursor.getString(0)),intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent myPendingIntent2=PendingIntent.getBroadcast(context,-(Integer.valueOf(cursor.getString(0))),intent2,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    myPendingIntent1.cancel();
                    myPendingIntent2.cancel();
                    alarmManager.cancel(myPendingIntent1);
                    alarmManager.cancel(myPendingIntent2);

                    values.put("enable", 0);
                    Toast.makeText(context, "Just Relaxed You are On Leaved....!!!!", Toast.LENGTH_SHORT).show();
                }
                myDatabase.update("Demo", values, "_id=" + rowid, null);


            }
        });
        if (cursor.moveToFirst()) {
            day.setSelection(cursor.getInt(1));
            classN.setText(cursor.getString(2));
            batchN.setText(cursor.getString(3));
            loc.setText(cursor.getString(4));
            start.setText(cursor.getString(5));
            stop.setText(cursor.getString(6));
            if (Integer.valueOf(cursor.getString(7)) == 1) {
                enable.setChecked(true);
            } else enable.setChecked(false);

            update = (Button) findViewById(R.id.update);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (enable.isChecked()) {
                        Calendar starts = getTime(start, day);
                        Calendar stops = getTime(stop, day);
                        if (starts != null && stops != null) {
                            Intent intent=new Intent(context,AlarmRecieverClass.class);
                            intent.putExtra("on_off",1);
                            intent.putExtra("id",Integer.valueOf(cursor.getString(0)));

                            Intent intent2=new Intent(context,AlarmRecieverClass.class);
                            intent2.putExtra("on_off",1);
                            intent2.putExtra("id",-(Integer.valueOf(cursor.getString(0))));

                            PendingIntent myPendingIntent1=PendingIntent.getBroadcast(context,Integer.valueOf(cursor.getString(0)),intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            PendingIntent myPendingIntent2=PendingIntent.getBroadcast(context,-(Integer.valueOf(cursor.getString(0))),intent2,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, starts.getTimeInMillis()-600000,999999999, myPendingIntent1);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, stops.getTimeInMillis()-600000,999999999, myPendingIntent2);
                        }
                    }

                    ContentValues values = new ContentValues();
                    values.put("day", day.getSelectedItemPosition());
                    values.put("class_name", String.valueOf(classN.getText()));
                    values.put("batch_name", String.valueOf(batchN.getText()));
                    values.put("location", String.valueOf(loc.getText()));
                    values.put("start_time", String.valueOf(start.getText()));
                    values.put("end_time", String.valueOf(stop.getText()));

                    myDatabase.update("Demo", values, "_id=" + rowid, null);
                    Toast.makeText(context, "Your Classes set up on new Time.....!!!", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(context, ViewClasses.class);
                    myIntent.putExtra("day",day.getSelectedItemPosition());
                    finish();
                    startActivity(myIntent);
                }
            });
            delete = (Button) findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,AlarmRecieverClass.class);
                    intent.putExtra("on_off",1);
                    intent.putExtra("id",Integer.valueOf(cursor.getString(0)));

                    Intent intent2=new Intent(context,AlarmRecieverClass.class);
                    intent2.putExtra("on_off",1);
                    intent2.putExtra("id",-(Integer.valueOf(cursor.getString(0))));

                    PendingIntent myPendingIntent1=PendingIntent.getBroadcast(context,Integer.valueOf(cursor.getString(0)),intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent myPendingIntent2=PendingIntent.getBroadcast(context,-(Integer.valueOf(cursor.getString(0))),intent2,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    myPendingIntent1.cancel();
                    myPendingIntent2.cancel();
                    alarmManager.cancel(myPendingIntent1);
                    alarmManager.cancel(myPendingIntent2);

                    myDatabase.delete("Demo", "_id=" + rowid, null);
                    Toast.makeText(context, "Wow..You have get Relaxed from a class...!!!", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(context, ViewClasses.class);
                    myIntent.putExtra("day",day.getSelectedItemPosition());
                    finish();
                    startActivity(myIntent);
                }
            });

            cancel = (Button) findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(context, ViewClasses.class);
                    myIntent.putExtra("day",day.getSelectedItemPosition());
                    finish();
                    startActivity(myIntent);
                }
            });

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerMethod(start);
                }
            });
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerMethod(stop);
                }
            });
        }
    }

    public void TimePickerMethod(final TextView view1){
        final Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String AM_PM = (hourOfDay >= 12&&hourOfDay<24) ? "PM" : "AM";
                int hour=(hourOfDay > 12) ? hourOfDay - 12 : hourOfDay;
                String hr=toString().valueOf(hour);
                String minutes=toString().valueOf(minute);
                if(minute<10)minutes="0"+minutes;
                if(hour<10)hr="0"+hr;
                String finalTime = (hr + " : " +
                        minutes + " " + AM_PM);
                view1.setText(finalTime);
            }
        }, mHour, mMinute, false);
        timePicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Do nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //DO Nothing
    }
    public Calendar getTime(TextView view1,Spinner days){
        final Calendar calendar=Calendar.getInstance();
        CharSequence s1= view1.getText();
        int day=-1;
        String x= String.valueOf(days.getSelectedItem());
        if(x.equalsIgnoreCase("Monday")){
            day=2;
        }
        else if(x.equalsIgnoreCase("Tuesday")){
            day=3;
        }
        else if(x.equalsIgnoreCase("Wednesday")){
            day=4;
        }
        else if(x.equalsIgnoreCase("Thursday")){
            day=5;
        }
        else if(x.equalsIgnoreCase("Friday")){
            day=6;
        }
        else if(x.equalsIgnoreCase("Saturday")){
            day=7;
        }
        else if(x.equalsIgnoreCase("Sunday")){
            day=1;
        }
        else{}
        calendar.set(Calendar.DAY_OF_WEEK,day);
        if(s1.charAt(0)!='C') {
            int startHr = Integer.parseInt(String.valueOf(s1.subSequence(0, 2)));
            int startMin = Integer.parseInt(String.valueOf(s1.subSequence(5, 7)));
            String AM_PM = String.valueOf(s1.subSequence(8, 10));
            if (AM_PM.equalsIgnoreCase("AM")||(startHr==12&&AM_PM.equalsIgnoreCase("PM")))
                calendar.set(Calendar.HOUR_OF_DAY, startHr);
            else calendar.set(Calendar.HOUR_OF_DAY, startHr + 12);
            calendar.set(Calendar.MINUTE, startMin);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if(calendar.before(Calendar.getInstance())){
                calendar.add(Calendar.DATE,7);
            }
            return calendar;
        }
        else return null;
    }
}