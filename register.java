package com.example.priyankamoorthy.notesshare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class register extends AppCompatActivity  {
    EditText e1,e2,e3,e4,e5;
    Button b1;
    SQLiteDatabase db = null;
    String uname,uemail,upass,ucollege,udept;
    Cursor c;
    String a1,a2,a3,a4,a5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        e1=(EditText) findViewById(R.id.username);
        e2=(EditText) findViewById(R.id.email);
        e3=(EditText) findViewById(R.id.password);
         e4=(EditText) findViewById(R.id.college);
        e5=(EditText) findViewById(R.id.dept);

        b1 = (Button) findViewById(R.id.registerbutton);

        db=openOrCreateDatabase("SNDB", Context.MODE_PRIVATE,null);
        String query1="CREATE TABLE IF NOT EXISTS USER(userid INTEGER PRIMARY KEY AUTOINCREMENT,usrname varchar,email varchar,password varchar,college varchar,dept varchar);";
        db.execSQL(query1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //id=Integer.parseInt(e1.getText().toString());
                uname= e1.getText().toString();
                        uemail=e2.getText().toString();
                upass=e3.getText().toString();
                ucollege=e4.getText().toString();
                udept=e5.getText().toString();


                String query2="INSERT INTO USER(usrname,email,password,college,dept)VALUES ( '"+uname+"','"+uemail+"','"+upass+"','"+ucollege+"','"+udept+"' );";
                db.execSQL(query2);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                c=db.query("USER",null,"usrname=?",new String[]{uname},null,null,null,null);
                //String query3="SELECT * FROM DETAM;";
                //c=db.rawQuery(query3,null);
                c.moveToFirst();
                do{
                    a1=c.getString(c.getColumnIndex("usrname"));
                    a2=c.getString(c.getColumnIndex("email"));
                    a3=c.getString(c.getColumnIndex("password"));
                    a4=c.getString(c.getColumnIndex("college"));
                    a5=c.getString(c.getColumnIndex("dept"));

                }while (c.moveToNext());
                Toast.makeText(getApplicationContext(), "Success" + a1 + a2 + a3 + a4 + a5 , Toast.LENGTH_LONG).show();
                e1.setText("");
                e2.setText("");
                e3.setText("");


                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);


            }}



        );
    }

}



