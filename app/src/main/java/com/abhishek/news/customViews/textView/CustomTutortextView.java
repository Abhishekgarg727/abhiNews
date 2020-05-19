package com.abhishek.news.customViews.textView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTutortextView extends AppCompatTextView {
    public CustomTutortextView(Context context) {
        super(context);
    }

    public CustomTutortextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTutortextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Nunito-Regular.ttf", context);
        setTypeface(customFont);
    }
}
