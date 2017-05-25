package com.mj.lims;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Frank on 12/22/2015.
 *
 */
public class DopeTextView extends TextView {


    private static final String FONT_PATH = "khula.ttf";

    public DopeTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public DopeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public DopeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface(FONT_PATH, context);
        setTypeface(customFont);
        //setMaxEms(1); //// TODO: 03-Apr-17 fucked up here!
    }

}
