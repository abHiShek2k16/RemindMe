package com.example.prince.remindme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPage extends AppCompatActivity{
    TabHost tab;

    AppCompatButton login;
    EditText name,pass;

    AppCompatButton signup;
    EditText nam;
    EditText phon;
    EditText pas;
    TextView skipfrnw;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //Id's
        login=(AppCompatButton)findViewById(R.id.loginbutton);
        signup=(AppCompatButton)findViewById(R.id.signuppagebutton);


        //Tab
        tab = (TabHost)findViewById(R.id.tabhost);
        tab.setup();

        TabHost.TabSpec spec1 = tab.newTabSpec("LOGIN");
        spec1.setIndicator("LOGIN");
        spec1.setContent(R.id.login);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("SIGN UP");
        spec2.setIndicator("SIGN UP");
        spec2.setContent(R.id.signup);
        tab.addTab(spec2);

        tab.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.backtabactive);
        tab.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.backtabinactive);

        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            public void onTabChanged(String arg0) {
                for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                    tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.backtabinactive); // unselected
                }

                tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundResource(R.drawable.backtabactive); // selected

            }
        });


        //Connection Check
        ConnectivityManager con = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(con.getActiveNetworkInfo() != null && con.getActiveNetworkInfo().isAvailable() && con.getActiveNetworkInfo().isConnected()){

            TextView txt = (TextView)findViewById(R.id.checkconnection);
            txt.setVisibility(View.INVISIBLE);

            //Login Page
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();


            name=(EditText)findViewById(R.id.user);
            pass=(EditText)findViewById(R.id.password);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = name.getText().toString();
                    String password = pass.getText().toString();

                    str.trim();
                    password.trim();

                    if(str.isEmpty()||password.isEmpty())
                    {
                        Toast.makeText(MainPage.this,"HI!! please fill all the above detail...",Toast.LENGTH_LONG).show();
                    }
                    else {
                        
                        ProgressDialog pd = new ProgressDialog(MainPage.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("Please wait...!!!");
                        pd.setIndeterminate(true);
                        pd.setCancelable(true);
                        pd.show();

                        mFirebaseAuth.signInWithEmailAndPassword(str,password).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainPage.this,"Please..Enter correct Detail for Continue !!!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainPage.this, MainPage.class));
                            }
                        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(MainPage.this,HomePage.class);
                                    i.putExtra("Value","0");
                                    MainPage.this.startActivity(i);
                                }
                                else{

                                }
                            }
                        });
                    }
                }
            });


            //SignUp page

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            nam=(EditText)findViewById(R.id.editname);
            phon=(EditText)findViewById(R.id.editphone);
            pas=(EditText)findViewById(R.id.editpass);


            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = nam.getText().toString().trim();
                    String mob = phon.getText().toString().trim();
                    String pass = pas.getText().toString().trim();


                    if(name.isEmpty()||mob.isEmpty()||pass.isEmpty())
                    {
                        Toast.makeText(MainPage.this,"HI!! please fill all the above detail...",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (pass.length() < 6) {
                            Toast.makeText(MainPage.this, "Please ,Chosse a strong password...", Toast.LENGTH_SHORT).show();
                        } else {

                            ProgressDialog pd = new ProgressDialog(MainPage.this);
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setMessage("Please wait...!!!");
                            pd.setIndeterminate(true);
                            pd.setCancelable(true);
                            pd.show();

                            mFirebaseAuth.createUserWithEmailAndPassword(name, pass).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    startActivity(new Intent(MainPage.this, MainPage.class));
                                    Toast.makeText(MainPage.this, "HI!! please fill all the above detail correctly...", Toast.LENGTH_LONG).show();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(MainPage.this, MainPage.class));
                                        Toast.makeText(MainPage.this,"Succesfully SignUp....Please Login to Continue",Toast.LENGTH_LONG).show();
                                    } else {

                                    }
                                }
                            });
                        }
                    }

                }
            });

        }
        else{
            Toast.makeText(MainPage.this,"Please..Check Your Connection!!!",Toast.LENGTH_LONG).show();

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainPage.this,"Can't Login Due to Poor Connectivity..!!!",Toast.LENGTH_LONG).show();
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainPage.this,"Please...Connect to Internet before signup..!!!",Toast.LENGTH_LONG).show();
                }
            });

            TextView txt = (TextView)findViewById(R.id.checkconnection);
            txt.setVisibility(View.VISIBLE);

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainPage.this,MainPage.class));
                }
            });
        }




        //Skip Button
         skipfrnw = (TextView)findViewById(R.id.skip);
         skipfrnw.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(MainPage.this,HomePage.class);
                 i.putExtra("Value","1");
                 MainPage.this.startActivity(i);
             }
         });

    }
}
