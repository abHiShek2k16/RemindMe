package com.example.prince.remindme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

public class CallActivity extends AppCompatActivity {

    EditText number;
    AppCompatButton call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        number = (EditText)findViewById(R.id.callText);
        call = (AppCompatButton) findViewById(R.id.callButton);

        assert number != null;

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = number.getText().toString();

                if(num.isEmpty()){
                    Toast.makeText(CallActivity.this,"Please !! Enter the Number to call",Toast.LENGTH_LONG).show();
                }
                else if(num.length()!=10){
                    Toast.makeText(CallActivity.this,"Please !! Enter Correct 10 digit Number to call",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + num));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Call Permission Denied", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);

                            return;
                        }
                    }

                    startActivity(intent);
                }
            }
        });


    }
}
