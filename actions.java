package com.example.priyankamoorthy.notesshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by priyankamoorthy on 3/9/17.
 */
public class actions extends AppCompatActivity {


    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions);


        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button4);



        b2.setOnClickListener(new View.OnClickListener() {
@Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), takepic.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), Main22Activity.class);
                startActivity(i);
            }
        });



    }
}
