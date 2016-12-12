package com.yoesuv.infomalangbatu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainGalleryDetail extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView tvTitle,textView_desc;
    private ImageView img;

    public static String EXTRA_IMAGE = "extra_image";
    public static String EXTRA_DESC = "extra_description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar_gallery);
        setSupportActionBar(toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.textView_title);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setElevation(5);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"pacifico.ttf");
        tvTitle.setTypeface(tf);
        tvTitle.setText(R.string.gallery);

        textView_desc = (TextView) findViewById(R.id.textView_gallery_detail);
        img = (ImageView) findViewById(R.id.imageView_gallery_detail);
        img.setAdjustViewBounds(false);

        textView_desc.setText(getIntent().getExtras().getString(EXTRA_DESC));
        Picasso.with(this).load(getIntent().getExtras().getString(EXTRA_IMAGE)).placeholder(R.drawable.img_default).into(img, new Callback() {
            @Override
            public void onSuccess() {
                img.setAdjustViewBounds(true);
            }
            @Override
            public void onError() {
                img.setImageResource(R.drawable.img_default);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                MainGalleryDetail.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
