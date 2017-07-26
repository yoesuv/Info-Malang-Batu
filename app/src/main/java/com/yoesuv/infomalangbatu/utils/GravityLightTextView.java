package com.yoesuv.infomalangbatu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 *  Created by yusuf on 7/26/17.
 */

public class GravityLightTextView extends AppCompatTextView {

    public GravityLightTextView(Context context) {
        super(context);
        init();
    }

    public GravityLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GravityLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"gravity-light.ttf");
        setTypeface(tf);
    }
}
