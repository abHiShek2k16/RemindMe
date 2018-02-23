package com.example.prince.remindme;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage extends AppCompatActivity {
    AppCompatButton add;
    AppCompatButton view;

    FloatingActionButton call;
    FloatingActionButton mail;
    FloatingActionButton fab;

    Animation close;
    Animation open;
    Animation rotateClock;
    Animation rotateAnti;

    boolean isopen = false;

    String str  = "2";
    String zero = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add = (AppCompatButton) findViewById(R.id.addclass);
        view = (AppCompatButton) findViewById(R.id.viewclass);

        Intent intent = this.getIntent();
        str  = intent.getStringExtra("Value");
        if(str.equals(zero)){
            Toast.makeText(HomePage.this,"To add Class Just Login with your Id...!!!",Toast.LENGTH_LONG).show();
            add.setVisibility(View.INVISIBLE);
        }
        else {
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomePage.this, ActivityAddClass.class));
                }
            });
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ViewClasses.class));
            }
        });

        close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fabopen);
        rotateAnti = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        rotateClock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        call = (FloatingActionButton)findViewById(R.id.fabcall);
        mail = (FloatingActionButton)findViewById(R.id.fabmail);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isopen){
                    call.setAnimation(close);
                    mail.setAnimation(close);
                    fab.setAnimation(rotateAnti);
                    call.setClickable(false);
                    mail.setClickable(false);
                    isopen = false;
                }
                else{
                    call.startAnimation(open);
                    mail.startAnimation(open);
                    fab.startAnimation(rotateClock);
                    call.setClickable(true);
                    mail.setClickable(true);
                    isopen = true;

                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(HomePage.this,CallActivity.class));
                        }
                    });

                    mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(HomePage.this,SendMail.class));
                        }
                    });
                }
            }
        });

        final TextClock clock = (TextClock) findViewById(R.id.clock);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss a");
        clock.setText(ft.format(dNow));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.about:
                startActivity(new Intent(HomePage.this,AboutMe.class));
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                System.exit(0);
                break;
            case R.id.exit:
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }

        return true;
    }
}

