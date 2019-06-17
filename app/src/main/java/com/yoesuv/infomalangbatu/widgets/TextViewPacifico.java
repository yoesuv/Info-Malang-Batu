package com.yoesuv.infomalangbatu.widgets;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewPacifico extends AppCompatTextView {

    public TextViewPacifico(Context context) {
        super(context);
        init();
    }

    public TextViewPacifico(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewPacifico(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/pacifico.ttf");
        setTypeface(tf);
    }
}
