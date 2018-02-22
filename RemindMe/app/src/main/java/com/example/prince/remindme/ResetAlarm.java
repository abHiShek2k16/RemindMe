package com.example.prince.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;


import java.util.Calendar;

/**
 * Created by prince on 30/10/17.
 */

public class ResetAlarm extends Service {
    SQLiteDatabase myDatabase;
    Cursor cursor;
    Context context;
    AlarmManager alarmManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        context = getBaseContext();
        Database handler = new Database(context);
        int ref=intent.getIntExtra("ref",1);
        myDatabase = handler.getWritableDatabase();

        String[] FROM={"_id","day","class_name","batch_name","location","start_time","end_time","enable"};
        if(ref==1) {
            cursor = myDatabase.query("Demo", FROM, null, null, null, null, null);
        }
        else{
            int x=intent.getIntExtra("day",0);
            cursor = myDatabase.query("Demo", FROM, "day="+x, null, null, null, null);
        }
        if(cursor.moveToFirst()){

        }
        else{

        }



        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        while(!cursor.isAfterLast()){
                String start = cursor.getString(5);
                String stop = cursor.getString(6);
                int day = cursor.getInt(1);
                int enable=cursor.getInt(7);
            if(enable==1) {
                Calendar starts = getTime(start, day);
                Calendar stops = getTime(stop, day);

                if (starts != null && stops != null) {

                    Intent intent1 = new Intent(context, AlarmRecieverClass.class);
                    intent1.putExtra("on_off", 1);
                    intent1.putExtra("id", Integer.valueOf(cursor.getString(0)));

                    Intent intent2 = new Intent(context, AlarmRecieverClass.class);
                    intent2.putExtra("on_off", 1);
                    intent2.putExtra("id", -(Integer.valueOf(cursor.getString(0))));

                    PendingIntent myPendingIntent1 = PendingIntent.getBroadcast(context, Integer.valueOf(cursor.getString(0)), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent myPendingIntent2 = PendingIntent.getBroadcast(context, -(Integer.valueOf(cursor.getString(0))), intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, starts.getTimeInMillis() - 600000,999999999, myPendingIntent1);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, stops.getTimeInMillis() - 600000,999999999, myPendingIntent2);
                }

            }
            else{
                Intent intent1=new Intent(context,AlarmRecieverClass.class);
                intent1.putExtra("on_off",1);
                intent1.putExtra("id", Integer.valueOf(cursor.getString(0)));

                Intent intent2=new Intent(context,AlarmRecieverClass.class);
                intent2.putExtra("on_off",1);
                intent2.putExtra("id",-(Integer.valueOf(cursor.getString(0))));

                PendingIntent myPendingIntent1=PendingIntent.getBroadcast(context,Integer.valueOf(cursor.getString(0)),intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent myPendingIntent2=PendingIntent.getBroadcast(context,-(Integer.valueOf(cursor.getString(0))),intent2,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                myPendingIntent1.cancel();
                myPendingIntent2.cancel();
                alarmManager.cancel(myPendingIntent1);
                alarmManager.cancel(myPendingIntent2);
            }
            cursor.moveToNext();
        }
        return START_NOT_STICKY;
    }


    public Calendar getTime(String time, int days){

        final Calendar calendar= Calendar.getInstance();
        int day=-1;
        int x= days;
        if(x==0){
            day=2;
        }
        else if(x==1){
            day=3;
        }
        else if(x==2){
            day=4;
        }
        else if(x==3){
            day=5;
        }
        else if(x==4){
            day=6;
        }
        else if(x==5){
            day=7;
        }
        else if(x==6){
            day=1;
        }
        else{}
        calendar.set(Calendar.DAY_OF_WEEK,day);
        if(time.charAt(0)!='C') {
            int startHr = Integer.parseInt(String.valueOf(time.subSequence(0, 2)));
            int startMin = Integer.parseInt(String.valueOf(time.subSequence(5, 7)));
            String AM_PM = String.valueOf(time.subSequence(8, 10));
            if (AM_PM.equalsIgnoreCase("AM")||(startHr==12&&AM_PM.equalsIgnoreCase("PM")))
                calendar.set(Calendar.HOUR_OF_DAY, startHr);
            else calendar.set(Calendar.HOUR_OF_DAY, startHr + 12);
            calendar.set(Calendar.MINUTE, startMin);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Calendar now= Calendar.getInstance();
            if(calendar.before(Calendar.getInstance())){
                calendar.add(Calendar.DATE,7);
            }
            return calendar;
        }
        else return null;
    }
}