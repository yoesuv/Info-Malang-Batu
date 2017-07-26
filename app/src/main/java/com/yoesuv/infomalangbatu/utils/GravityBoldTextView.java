package com.yoesuv.infomalangbatu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 *  Created by yusuf on 7/26/17.
 */

public class GravityBoldTextView extends AppCompatTextView{

    public GravityBoldTextView(Context context) {
        super(context);
        init();
    }

    public GravityBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GravityBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"gravity-bold.ttf");
        setTypeface(tf);
    }

}
