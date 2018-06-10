package com.example.prince.remindme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (firebaseUser.getEmail().toString().equalsIgnoreCase(str)) {
                        startActivity(new Intent(SplashScreen.this, MainPage.class));
                        finish();
                    } else {
                        Intent i = new Intent(SplashScreen.this, HomePage.class);
                        i.putExtra("Value", "0");
                        startActivity(i);
                        finish();
                    }
                }catch (Exception e){
                    startActivity(new Intent(SplashScreen.this, MainPage.class));
                    finish();
                }
            }
        },5000);
    }
}
