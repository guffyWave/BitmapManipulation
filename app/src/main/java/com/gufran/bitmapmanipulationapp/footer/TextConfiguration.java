package com.gufran.bitmapmanipulationapp.footer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

/**
 * Created by gufran on 8/2/16.
 */
public class TextConfiguration {
    String text;
    int color;
    int size;
    Typeface typeface;

    public TextConfiguration(Context context, String text) {
        this.text = text;
        this.color = Color.BLACK;
        this.size = 30;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
