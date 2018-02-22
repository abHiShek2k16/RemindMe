package com.example.prince.remindme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Created by prince on 29/10/17.
 */

public class AlarmRecieverClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
        int state=intent.getIntExtra("on_off",0);
        int id=intent.getIntExtra("id",0);


        Intent serviceIntent=new Intent(context,RingtoneService.class);


        serviceIntent.putExtra("state",state);
        serviceIntent.putExtra("id",id);

        context.startService(serviceIntent);
    }
}
