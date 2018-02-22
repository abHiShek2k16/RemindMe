package com.example.prince.remindme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by prince on 30/10/17.
 */

public class AutoRestart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
            Intent serviceIntent=new Intent(context,ResetAlarm.class);
            serviceIntent.putExtra("ref",1);
            context.startService(serviceIntent);
        }
    }
}
