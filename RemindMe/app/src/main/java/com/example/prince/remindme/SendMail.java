package com.example.prince.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMail extends AppCompatActivity {

    EditText to;
    EditText sub;
    EditText message;
    AppCompatButton send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        to = (EditText)findViewById(R.id.mailAdd);
        sub = (EditText)findViewById(R.id.mailSub);
        message = (EditText)findViewById(R.id.mailMessage);
        send = (AppCompatButton)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess  = message.getText().toString();
                String subject = sub.getText().toString();
                String receiverAdd = to.getText().toString();

                if(mess.isEmpty() || subject.isEmpty() || receiverAdd.isEmpty()){
                    Toast.makeText(SendMail.this, "Hi..Please Fill all the above Detail !!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent gmail = new Intent(Intent.ACTION_SEND);
                    gmail.setType("message/rfc822");
                    gmail.putExtra(Intent.EXTRA_EMAIL,new String[]{receiverAdd});
                    gmail.putExtra(Intent.EXTRA_SUBJECT,subject);
                    gmail.putExtra(Intent.EXTRA_TEXT,mess);

                    try{
                        startActivity(Intent.createChooser(gmail,"Choose One to send Mail.."));
                    }
                    catch(Exception e){

                    }

                }
            }
        });

    }
}
