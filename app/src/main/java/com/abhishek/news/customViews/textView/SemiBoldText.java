package com.abhishek.news.customViews.textView;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SemiBoldText extends AppCompatTextView {
    public SemiBoldText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public SemiBoldText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public SemiBoldText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Nunito-SemiBold.ttf", context);
        setTypeface(customFont);
    }
}
