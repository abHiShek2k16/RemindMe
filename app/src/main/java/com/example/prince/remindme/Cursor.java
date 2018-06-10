package com.example.prince.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by prince on 29/10/17.
 */

public class Cursor extends CursorAdapter {

    SQLiteDatabase myDatabase;

    public Cursor(Context context, android.database.Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, android.database.Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row,parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final android.database.Cursor cursor) {
        Database handler = new Database(context);

        myDatabase = handler.getWritableDatabase();

        TextView classN = (TextView)view.findViewById(R.id.classN);
        TextView batchN = (TextView)view.findViewById(R.id.batchN);
        final Switch enable=(Switch)view.findViewById(R.id.switch1);

        String className = cursor.getString(cursor.getColumnIndexOrThrow("class_name"));
        String batchName = cursor.getString(cursor.getColumnIndexOrThrow("batch_name"));
        int en=cursor.getInt(cursor.getColumnIndexOrThrow("enable"));

        classN.setText(className);
        batchN.setText(batchName);
        if(en==1){
            enable.setChecked(true);
        }
        else enable.setChecked(false);
    }

}

