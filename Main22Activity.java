package com.example.priyankamoorthy.notesshare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.*;
import java.util.*;
import java.io.*;

import java.io.FileNotFoundException;

public class Main22Activity extends Activity {
    Button btnLoadImage1;
    TextView textSource1;
    EditText editTextCaption;
    Button btnProcessing,save,share;
    ImageView imageView;

    final int RQS_IMAGE1 = 1;

    Uri source1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        save=(Button)findViewById(R.id.button13);
        share=(Button)findViewById(R.id.button7);
        btnLoadImage1 = (Button)findViewById(R.id.loadimage1);
        textSource1 = (TextView)findViewById(R.id.sourceuri1);
        editTextCaption = (EditText)findViewById(R.id.caption);
        btnProcessing = (Button)findViewById(R.id.processing);
        imageView = (ImageView)findViewById(R.id.result);
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.buildDrawingCache();
                Bitmap bmap = imageView.getDrawingCache();
                saveImage(bmap);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);
                picMessageIntent.setType("image/jpeg");
                File downloadedPic =  new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS),
                        "q.jpeg");

                picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(downloadedPic));
                startActivity(picMessageIntent);
                startActivity(Intent.createChooser(picMessageIntent, "Send your picture using:"));
            }
        });
        btnLoadImage1.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
            }});

        btnProcessing.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (source1 != null) {
                    Bitmap processedBitmap = ProcessingBitmap();
                    if (processedBitmap != null) {
                        imageView.setImageBitmap(processedBitmap);
                        Toast.makeText(getApplicationContext(),
                                "Done",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Something wrong in processing!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Select both image!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    void saveImage(Bitmap img) {
        String RootDir = Environment.getExternalStorageDirectory()
                + File.separator + "txt_imgs";
        File myDir=new File(RootDir);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);

            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(Main22Activity.this, "Image saved to 'txt_imgs' folder",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RQS_IMAGE1:
                    source1 = data.getData();
                    textSource1.setText(source1.toString());
                    break;
            }
        }
    }

    private Bitmap ProcessingBitmap(){
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        try {
            bm1 = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(source1));

            Config config = bm1.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm1, 0, 0, null);

            String captionString = editTextCaption.getText().toString();
            if(captionString != null){

                Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                paintText.setColor(Color.BLACK);
                paintText.setTextSize(50);
                paintText.setStyle(Style.FILL);
               // paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);

                Rect rectText = new Rect();
                paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                newCanvas.drawText(captionString,
                        0, rectText.height(), paintText);

                Toast.makeText(getApplicationContext(),
                        "drawText: " + captionString,
                        Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),
                        "caption empty!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }


}


