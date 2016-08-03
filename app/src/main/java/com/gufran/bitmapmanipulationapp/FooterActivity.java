package com.gufran.bitmapmanipulationapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gufran.bitmapmanipulationapp.footer.EmailTextConfiguration;
import com.gufran.bitmapmanipulationapp.footer.ImageManipulationUtil;
import com.gufran.bitmapmanipulationapp.footer.NameTextConfiguration;
import com.gufran.bitmapmanipulationapp.footer.PhoneTextConfiguration;
import com.gufran.bitmapmanipulationapp.footer.TextConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FooterActivity extends AppCompatActivity {

    String FILE_PATH = "BitmapManipulation";
    String fileName = "footer.jpg";

    ImageView imageView;

    final int RQS_IMAGE1 = 1;

    Uri sourceImageUri;
    Bitmap sourceBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void browseAndGenerate(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQS_IMAGE1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    sourceImageUri = data.getData();
                    generateFinalImage();
                    break;
            }

        }
    }

    public void generateFinalImage() {
        if (sourceImageUri != null) {
            ImageManipulationUtil imu = new ImageManipulationUtil();
            try {
                sourceBitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(sourceImageUri));

                Bitmap finalBitmap = imu.generateJoinedFooter(sourceBitmap, getJoinedFooter(sourceBitmap), false);

                saveFileBitMap(finalBitmap);
                imageView.setImageBitmap(finalBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(FooterActivity.this, "File Not Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FooterActivity.this, "Image Source is null", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap getJoinedFooter(Bitmap sourceBitmap) {

        int infoFooterWidth = (int) (0.40 * sourceBitmap.getWidth());
        int userFooterWidth = (int) (0.60 * sourceBitmap.getWidth());

        int footerHeight = (int) (0.25 * sourceBitmap.getHeight());

        ImageManipulationUtil imu = new ImageManipulationUtil();
        TextConfiguration textConfiguration = new TextConfiguration(getApplicationContext(), "For other details,please contact me");
        textConfiguration.setSize((int) (0.15 * footerHeight));
        Bitmap bmpInfo = imu.generateFooterInfoBitMap(infoFooterWidth, footerHeight, textConfiguration);


        File path = Environment.getExternalStorageDirectory();
        File imageFile = new File(path, "user.png");
        Bitmap userPic = BitmapFactory.decodeFile(imageFile.getPath(), null);


        //--->Name Configuration
        NameTextConfiguration nameTextConfiguration = new NameTextConfiguration(getApplicationContext(), "Gufran Khurshid");
        nameTextConfiguration.setSize((int) (0.25 * footerHeight));
        nameTextConfiguration.setColor(Color.DKGRAY);
        nameTextConfiguration.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));

        //--->Phone Configuration
        PhoneTextConfiguration phoneTextConfiguration = new PhoneTextConfiguration(getApplicationContext(), "+91 7042935653");
        phoneTextConfiguration.setSize((int) (0.15 * footerHeight));
        phoneTextConfiguration.setColor(Color.DKGRAY);
        phoneTextConfiguration.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

        //--->Email Configuration
        EmailTextConfiguration emailTextConfiguration = new EmailTextConfiguration(getApplicationContext(), "gufran.khurshid@gmail.com");
        emailTextConfiguration.setSize((int) (0.15 * footerHeight));
        emailTextConfiguration.setColor(Color.DKGRAY);
        emailTextConfiguration.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

        Bitmap bmpUser = imu.generateUserInfoBitmap(userFooterWidth, footerHeight, userPic, nameTextConfiguration, phoneTextConfiguration, emailTextConfiguration);

        return imu.generateJoinedFooter(bmpInfo, bmpUser, true);
    }


//    public void generateFooter(View v) {
//        ImageManipulationUtil imu = new ImageManipulationUtil();
//        TextConfiguration textConfiguration = new TextConfiguration(getApplicationContext(), "This is a long text");
////        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/");
////        textConfiguration.setTypeface(typeface);
//        Bitmap bmp = imu.generateFooterInfoBitMap(500, 200, textConfiguration);
//
//        saveFileBitMap(bmp);
//        imageView.setImageBitmap(bmp);
//    }


    public void generateFooterUserImage(View v) {
        ImageManipulationUtil imu = new ImageManipulationUtil();

        File path = Environment.getExternalStorageDirectory();
        File imageFile = new File(path, "user.png");
        Bitmap userPic = BitmapFactory.decodeFile(imageFile.getPath(), null);


        //--->Name Configuration
        NameTextConfiguration nameTextConfiguration = new NameTextConfiguration(getApplicationContext(), "Gufran Khurshid");
        nameTextConfiguration.setSize(40);
        nameTextConfiguration.setColor(Color.DKGRAY);
        nameTextConfiguration.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));

        //--->Phone Configuration
        PhoneTextConfiguration phoneTextConfiguration = new PhoneTextConfiguration(getApplicationContext(), "+91 7042935653");
        phoneTextConfiguration.setSize(25);
        phoneTextConfiguration.setColor(Color.DKGRAY);
        phoneTextConfiguration.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

        //--->Email Configuration
        EmailTextConfiguration emailTextConfiguration = new EmailTextConfiguration(getApplicationContext(), "gufran.khurshid@gmail.com");
        emailTextConfiguration.setSize(25);
        emailTextConfiguration.setColor(Color.DKGRAY);
        emailTextConfiguration.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));


        Bitmap bmp = imu.generateUserInfoBitmap(600, 200, userPic, nameTextConfiguration, phoneTextConfiguration, emailTextConfiguration);

        saveFileBitMap(bmp);
        imageView.setImageBitmap(bmp);
    }

    private Bitmap generateBitMap() {
        Bitmap footerBitMap = Bitmap.createBitmap(500, 300, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(footerBitMap);

        final int color = Color.CYAN;
        final Paint paint = new Paint();
        paint.setColor(color);
        final Rect rect = new Rect(0, 0, footerBitMap.getWidth(), footerBitMap.getHeight());
        //final RectF rectF = new RectF(rect);

        canvas.drawRect(rect, paint);

        canvas.drawBitmap(footerBitMap, rect, rect, paint);

        return footerBitMap;
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


    //prepareFooter Image

}
