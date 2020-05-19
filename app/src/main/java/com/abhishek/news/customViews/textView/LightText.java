package com.abhishek.news.customViews.textView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class LightText extends AppCompatTextView {
    public LightText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public LightText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public LightText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Nunito-Light.ttf", context);
        setTypeface(customFont);
    }

}
