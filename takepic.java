package com.example.priyankamoorthy.notesshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class takepic extends AppCompatActivity {
    ImageView imageView;
    Button button,save,share;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newupload);
        button = (Button) findViewById(R.id.button6);
        save = (Button) findViewById(R.id.button10);
        share = (Button) findViewById(R.id.button5);
        //button = (Button) findViewById(R.id.button6);
        imageView = (ImageView) findViewById(R.id.takepic);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
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

        Toast.makeText(takepic.this, "Image saved to 'txt_imgs' folder",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap bitmap = toGrayscale(photo);
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal) {
        final int height = bmpOriginal.getHeight();
        final int width = bmpOriginal.getWidth();

        final Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(bmpGrayscale);
        final Paint paint = new Paint();
        final ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        final ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

}

