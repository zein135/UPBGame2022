package edu.upb.lp.androidInternalFiles;

import android.content.Context;
import android.widget.TextView;

public class MyTextView extends TextView {
    private int horizontal;
    private int vertical;

    public MyTextView(Context context) {
        super(context);
    }

    public void setCoords(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }
}