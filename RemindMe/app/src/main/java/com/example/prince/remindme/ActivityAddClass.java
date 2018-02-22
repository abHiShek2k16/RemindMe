package com.example.prince.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAddClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AlarmManager alarmManager;


    Button addClass;
    TextView start;
    TextView stop;
    EditText classN;
    EditText batchN;
    EditText loc;


    Context context;
    SQLiteDatabase myDatabase;


    int dayOfweek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        this.context = this;

        classN=(EditText)findViewById(R.id.subject);
        batchN=(EditText)findViewById(R.id.batch);
        loc=(EditText)findViewById(R.id.classname);

        final Spinner day=(Spinner)findViewById(R.id.day);


        //Set the Spinner Day in add class Activity
        if (day != null) {
            day.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);
        }

        final ArrayList<String> daysOfWeek=new ArrayList<String>();
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




        Calendar calendar=Calendar.getInstance();
        int x1=calendar.get(Calendar.DAY_OF_WEEK);
        int y=0;
        if(x1==2)y=0;
        else if(x1==3)y=1;
        else if(x1==4)y=2;
        else if(x1==5)y=3;
        else if(x1==6)y=4;
        else if(x1==7)y=5;
        else if(x1==1)y=6;
        int x = y;
        day.setSelection(x);

        Database handler = new Database(this);

        myDatabase = handler.getWritableDatabase();



        //Set on click Listener on Time TextView to take time
        start=(TextView)findViewById(R.id.start_time);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerMethod(start);
            }
        });

        stop=(TextView)findViewById(R.id.end_time);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerMethod(stop);
            }
        });



        // Listener on AddClass Button to add the class
        addClass = (Button) findViewById(R.id.addnow);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (classN.getText().toString() != null && batchN.getText().toString() != null && loc.getText().toString() != null && start.getText().toString() != null && stop.getText().toString() != null){

                    updateDatabase(classN, batchN, loc, start, stop);
                    String[] arr = {"_id"};
                    Cursor cursor = myDatabase.query("Demo", arr, null, null, null, null, null, null);
                    cursor.moveToLast();

                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Calendar starts = getTime(start, day);
                    Calendar stops = getTime(stop, day);


                    if (starts != null && stops != null) {

                        Intent intent = new Intent(context, AlarmRecieverClass.class);
                        intent.putExtra("on_off", 1);
                        intent.putExtra("id", Integer.valueOf(cursor.getString(0)));

                        Intent intent2 = new Intent(context, AlarmRecieverClass.class);
                        intent2.putExtra("on_off", 1);
                        intent2.putExtra("id", -(Integer.valueOf(cursor.getString(0))));


                        PendingIntent myPendingIntent1 = PendingIntent.getBroadcast(context, Integer.valueOf(cursor.getString(0)), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        PendingIntent myPendingIntent2 = PendingIntent.getBroadcast(context, -(Integer.valueOf(cursor.getString(0))), intent2, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, starts.getTimeInMillis() - 600000, 999999999, myPendingIntent1);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, stops.getTimeInMillis() - 600000, 999999999, myPendingIntent2);
                        Toast.makeText(ActivityAddClass.this, "New Class Added !!! ", Toast.LENGTH_SHORT).show();


                    }
                    Intent myIntent = getIntent();
                    finish();
                    startActivity(myIntent);

                    Intent i = new Intent(ActivityAddClass.this,HomePage.class);
                    i.putExtra("Value","0");
                    ActivityAddClass.this.startActivity(i);
                }
                else{
                    Toast.makeText(ActivityAddClass.this,"Please!! Fill all the Detail to add new Class.....",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       dayOfweek = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void TimePickerMethod(final TextView textView){
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
                textView.setText(finalTime);
            }
        }, mHour, mMinute, false);
        timePicker.show();
    }



    public void updateDatabase(final EditText className,final EditText batchName,final EditText location,final TextView startTime,final TextView endTime){
        ContentValues values=new ContentValues();
        values.put("day",dayOfweek);
        values.put("class_name", String.valueOf(className.getText()));
        values.put("batch_name", String.valueOf(batchName.getText()));
        values.put("location", String.valueOf(location.getText()));
        values.put("start_time", String.valueOf(startTime.getText()));
        values.put("end_time", String.valueOf(endTime.getText()));
        values.put("enable",1);

        long x=myDatabase.insert("Demo",null,values);
    }



    public Calendar getTime(TextView textView,Spinner days){

        final Calendar calendar=Calendar.getInstance();

        CharSequence s1= textView.getText();
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
        else{

        }

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
