package com.yoesuv.infomalangbatu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yoesuv.infomalangbatu.model.ListPlace;

public class MainPlaceDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgPlaceDetail;
    public static String EXTRA_PLACE = "extra_place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        ListPlace listPlace = getIntent().getExtras().getParcelable(EXTRA_PLACE);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(5);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(listPlace!=null){
                getSupportActionBar().setTitle(listPlace.getNama());
            }
        }

        TextView tvPlace = (TextView) findViewById(R.id.textView_place_detail);
        imgPlaceDetail = (ImageView) findViewById(R.id.imageView_place_detail);
        imgPlaceDetail.setAdjustViewBounds(false);

        if(listPlace!=null){
            tvPlace.setText(listPlace.getDeskripsi());
            Picasso.with(this).load(listPlace.getGambar()).placeholder(R.drawable.img_default).into(imgPlaceDetail, new Callback() {

                @Override
                public void onSuccess() {
                    imgPlaceDetail.setAdjustViewBounds(true);
                }

                @Override
                public void onError() {
                    imgPlaceDetail.setImageResource(R.drawable.img_default);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                MainPlaceDetail.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
