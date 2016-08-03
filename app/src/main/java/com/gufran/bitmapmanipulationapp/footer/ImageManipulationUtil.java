package com.gufran.bitmapmanipulationapp.footer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by gufran on 8/2/16.
 */
public class ImageManipulationUtil {

    final int backgroundColor = Color.WHITE;

    public Bitmap generateFooterInfoBitMap(int w, int h, TextConfiguration textConfiguration) {
        // if (textConfiguration == null) throw new Exception("Text Configuration can't be null");

        Bitmap mainBitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mainBitMap);

        //----->>creating background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        Rect rect = new Rect(0, 0, mainBitMap.getWidth(), mainBitMap.getHeight());
        canvas.drawRect(rect, backgroundPaint);

        //----->>creating text
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        if (textConfiguration != null) {
            textPaint.setColor(textConfiguration.getColor());
            textPaint.setTextSize(textConfiguration.getSize());
            int posX = (int) (w / 2 - textPaint.measureText(textConfiguration.getText()) / 2);
            int posY = ((int) h / 2) + 5;
            canvas.drawText(textConfiguration.getText(), posX, posY, textPaint);
        }
        //draw the vertical separator line
        canvas.drawLine(w - 10, 30, w - 10, h - 30, textPaint);

        canvas.drawBitmap(mainBitMap, rect, rect, backgroundPaint);
        return mainBitMap;
    }

    public Bitmap generateUserInfoBitmap(int w, int h, Bitmap bitmapUser, NameTextConfiguration nameTextConfiguration, PhoneTextConfiguration phoneTextConfiguration, EmailTextConfiguration emailTextConfiguration) {
        Bitmap mainBitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mainBitMap);

        //----->>creating background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        Rect rect = new Rect(0, 0, mainBitMap.getWidth(), mainBitMap.getHeight());
        canvas.drawRect(rect, backgroundPaint);

        //----->>creating user image
        Paint userImagePaint = new Paint();
        userImagePaint.setAntiAlias(true);
        bitmapUser = scaleDown(bitmapUser, (int) (0.70 * h), true);//scale down image to 60% of the height of canvas
        int userImagePosX = (int) (0.2 * w) - bitmapUser.getWidth() / 2;// 20% of width-half of bmp width
        int userImagePosY = (int) (0.5 * h) - bitmapUser.getHeight() / 2;// 50 % of height-half of bmp height
        canvas.drawBitmap(bitmapUser, userImagePosX, userImagePosY, userImagePaint);

        //----->> setting up name
        Paint namePaint = new Paint();
        namePaint.setAntiAlias(true);
        if (nameTextConfiguration != null) {
            namePaint.setColor(nameTextConfiguration.getColor());
            namePaint.setTextSize(nameTextConfiguration.getSize());
            if (emailTextConfiguration.getTypeface() != null)
                namePaint.setTypeface(nameTextConfiguration.getTypeface());
            // namePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            int posX = (int) (0.40 * w);// 40% of width
            int posY = (int) (0.35 * h);// 25% of height
            canvas.drawText(nameTextConfiguration.getText(), posX, posY, namePaint);
        }


        //----->> setting up phoneNumber
        Paint phoneNumberPaint = new Paint();
        phoneNumberPaint.setAntiAlias(true);
        if (phoneTextConfiguration != null) {
            phoneNumberPaint.setColor(phoneTextConfiguration.getColor());
            phoneNumberPaint.setTextSize(phoneTextConfiguration.getSize());
            if (phoneTextConfiguration.getTypeface() != null)
                phoneNumberPaint.setTypeface(phoneTextConfiguration.getTypeface());
            int phonePosX = (int) (0.40 * w);// 40% of width
            int phonePosY = (int) (0.60 * h);// 50% of height
            canvas.drawText(phoneTextConfiguration.getText(), phonePosX, phonePosY, phoneNumberPaint);
        }

        //----->> setting up email
        Paint emailPaint = new Paint();
        emailPaint.setAntiAlias(true);
        if (emailTextConfiguration != null) {
            emailPaint.setColor(emailTextConfiguration.getColor());
            emailPaint.setTextSize(emailTextConfiguration.getSize());
            if (emailTextConfiguration.getTypeface() != null)
                emailPaint.setTypeface(emailTextConfiguration.getTypeface());
            int emailPosX = (int) (0.40 * w);// 40% of width
            int emailPosY = (int) (0.80 * h);// 70% of height
            canvas.drawText(emailTextConfiguration.getText(), emailPosX, emailPosY, emailPaint);
        }


        canvas.drawBitmap(mainBitMap, rect, rect, backgroundPaint);
        return mainBitMap;
    }

    private Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                             boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public Bitmap generateJoinedFooter(Bitmap footerInfo, Bitmap footerUser, boolean isJoinHorizontally) {
        Bitmap joinedBitmap = null;

        int w;
        int h;
        if (isJoinHorizontally == true) {
            w = footerInfo.getWidth() + footerUser.getWidth();
            if (footerInfo.getHeight() >= footerUser.getHeight()) {
                h = footerInfo.getHeight();
            } else {
                h = footerUser.getHeight();
            }
        } else {
            h = footerInfo.getHeight() + footerUser.getHeight();
            if (footerInfo.getWidth() >= footerUser.getWidth()) {
                w = footerInfo.getWidth();
            } else {
                w = footerUser.getWidth();
            }
        }

        Bitmap.Config config = footerUser.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }

        joinedBitmap = Bitmap.createBitmap(w, h, config);
        Canvas newCanvas = new Canvas(joinedBitmap);

        newCanvas.drawBitmap(footerInfo, 0, 0, null);
        if (isJoinHorizontally == true) {
            newCanvas.drawBitmap(footerUser, footerInfo.getWidth(), 0, null);
        } else {
            newCanvas.drawBitmap(footerUser, 0, footerInfo.getHeight(), null);
        }
        return joinedBitmap;
    }
}
