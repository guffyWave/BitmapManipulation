package com.gufran.bitmapmanipulationapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnLoadImage1, btnLoadImage2;
    TextView textSource1, textSource2;
    Button btnProcessing;
    ImageView imageResult;

    final int RQS_IMAGE1 = 1;
    final int RQS_IMAGE2 = 2;

    Uri source1, source2;

    String FILE_PATH = "BitmapManipulation";
    String fileName = "output.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoadImage1 = (Button) findViewById(R.id.loadimage1);
        btnLoadImage2 = (Button) findViewById(R.id.loadimage2);
        textSource1 = (TextView) findViewById(R.id.sourceuri1);
        textSource2 = (TextView) findViewById(R.id.sourceuri2);
        btnProcessing = (Button) findViewById(R.id.processing);
        imageResult = (ImageView) findViewById(R.id.result);

        btnLoadImage1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
            }
        });

        btnLoadImage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE2);
            }
        });

        btnProcessing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (source1 != null && source2 != null) {
                    Bitmap processedBitmap = processBitmap();
                    if (processedBitmap != null) {
                        imageResult.setImageBitmap(processedBitmap);

                        saveFileBitMap(processedBitmap);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    source1 = data.getData();
                    textSource1.setText(source1.toString());
                    break;
                case RQS_IMAGE2:
                    source2 = data.getData();
                    textSource2.setText(source2.toString());
                    break;
            }

        }
    }

    private void saveFileBitMap(Bitmap bitMap) {
        String path = Environment.getExternalStorageDirectory().toString();
        File dir = new File(path, FILE_PATH);
        if (!dir.exists() == true) {
            dir.mkdir();
        }
        File file = new File(path, FILE_PATH + "/" + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap processBitmap() {
        Bitmap bm1 = null;
        Bitmap bm2 = null;
        Bitmap newBitmap = null;

        try {
            bm1 = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(source1));
            bm2 = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(source2));

            int w = bm1.getWidth() + bm2.getWidth();
            int h;
            if (bm1.getHeight() >= bm2.getHeight()) {
                h = bm1.getHeight();
            } else {
                h = bm2.getHeight();
            }

            Bitmap.Config config = bm1.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(w, h, config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm1, 0, 0, null);
            newCanvas.drawBitmap(bm2, bm1.getWidth(), 0, null);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }


}
