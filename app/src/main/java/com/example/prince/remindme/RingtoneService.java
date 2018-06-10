package com.example.prince.remindme;

/**
 * Created by prince on 29/10/17.
 */

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class RingtoneService extends Service {

    MediaPlayer media;
    boolean isRunning;
    Context context;
    Cursor cursor;
    SQLiteDatabase myDatabase;
    AlarmManager alarmManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e("Ringtone service","working");
        Calendar time;
        int flag=intent.getIntExtra("state",0);
        int idx=intent.getIntExtra("id",0);
        int id=(idx>0)?idx:-idx;
        Log.e("idx", String.valueOf(idx));

        context=getBaseContext();
        Database handler = new Database(context);
        myDatabase = handler.getWritableDatabase();
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        if(!this.isRunning&&flag==1) {

            flag = 0;

            media = MediaPlayer.create(this, R.raw.alarmtone);
            media.start();
            media.setLooping(true);
            isRunning = true;
            Intent getNotificationService = new Intent(this, NotificationService.class);
            getNotificationService.putExtra("id", idx);
            this.startService(getNotificationService);
        }

        else if(this.isRunning && flag==0){
            Log.e("Stop", "alarm" );
            media.stop();
            media.reset();
            isRunning=false;


            String[] FROM={"_id","day","class_name","batch_name","location","start_time","end_time","enable"};
            cursor = myDatabase.query("Demo", FROM, "_id="+id, null, null, null, null);
            if(cursor.moveToFirst()){

            }
            else{

            }

            int day = cursor.getInt(1);
            int enable = cursor.getInt(7);
            if (enable == 1) {
                if (idx > 0) {
                    time = getTime(cursor.getString(5), day);
                    Log.e("start time", cursor.getString(5));
                }
                else {
                    time = getTime(cursor.getString(6), day);
                    Log.e("stop time", cursor.getString(6));
                }

                if (time != null) {

                    Intent intent1 = new Intent(context, AlarmRecieverClass.class);
                    intent1.putExtra("on_off", 1);
                    intent1.putExtra("id", idx);

                    PendingIntent myPendingIntent1 = PendingIntent.getBroadcast(context, Integer.valueOf(cursor.getString(0)), intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis() - 600000,999999999, myPendingIntent1);

                }

            }
        }
        else{
            //do nothing
        }

        return START_NOT_STICKY;
    }

    public Calendar getTime(String time, int days){

        final Calendar calendar=Calendar.getInstance();
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
        Toast.makeText(context,String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)),Toast.LENGTH_SHORT).show();
        if(time.charAt(0)!='C') {
            int startHr = Integer.parseInt(String.valueOf(time.subSequence(0, 2)));
            int startMin = Integer.parseInt(String.valueOf(time.subSequence(5, 7)));
            String AM_PM = String.valueOf(time.subSequence(8, 10));
            if (AM_PM.equalsIgnoreCase("AM")||(startHr==12&&AM_PM.equalsIgnoreCase("PM")))
                calendar.set(Calendar.HOUR_OF_DAY, startHr);
            else calendar.set(Calendar.HOUR_OF_DAY, startHr + 12);
            calendar.set(Calendar.MINUTE, startMin);
            Toast.makeText(context,calendar.get(Calendar.HOUR_OF_DAY)+" : "+startMin,Toast.LENGTH_SHORT).show();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DATE,7);

            return calendar;
        }
        else return null;
    }
}