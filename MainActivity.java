package com.example.priyankamoorthy.notesshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.ResultSet;

public class   MainActivity extends AppCompatActivity {

    Button b1,b2;
    EditText e1, e2;
    TextView tx1;

    SQLiteDatabase db = null;
    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.email);
        e2 = (EditText) findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.loginbutton);
        b2 = (Button) findViewById(R.id.registerb);


;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = e1.getText().toString();
                final String password = e2.getText().toString();
                db=openOrCreateDatabase("SNDB", Context.MODE_PRIVATE,null);
            /*   // String query1 = "SELECT email,password FROM NSDB WHERE email='" + username + "' and password='" + password + "' ";


                 //   Cursor c=db.rawQuery("SELECT * FROM USER WHERE usrname='" + username + "' and password='" + password + "' ",null);
                //    c.moveToFirst();
                    if(username.equals("priya") && password.equals("priya"))
                    {
                        db.close();
                        Intent i1 = new Intent(getApplicationContext(), actions.class);
                        startActivity(i1);

                        finish();
                    }
                   // else
                     //   Toast.makeText(getApplicationContext(), "Enter valid details", Toast.LENGTH_LONG).show();
*/



                   Cursor c=db.rawQuery("SELECT * FROM USER WHERE usrname='" + username + "' and password='" + password + "' ",null);
                   c.moveToFirst();
                    if(c.getCount()==1)
                    {
                        db.close();
                        Intent i1 = new Intent(getApplicationContext(), actions.class);
                        startActivity(i1);

                        finish();
                    }
                  else
                       Toast.makeText(getApplicationContext(), "Enter valid details", Toast.LENGTH_LONG).show();



                }


        });



        b2.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), register.class);
                startActivity(i);
            }
        });


    }



}






