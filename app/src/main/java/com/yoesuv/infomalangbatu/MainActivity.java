package com.yoesuv.infomalangbatu;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.yoesuv.infomalangbatu.fragment.AboutFragment;
import com.yoesuv.infomalangbatu.fragment.HomeFragment;
import com.yoesuv.infomalangbatu.fragment.InfoGalleryFragment;
import com.yoesuv.infomalangbatu.fragment.ListPlaceFragment;
import com.yoesuv.infomalangbatu.fragment.MapsFragment;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private String currentTitle;
    private int currentIdentifier;

    private Drawer myDrawer;

    private StartAppAd ads = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        StartAppSDK.init(this, "201323394", true);
        StartAppAd.disableSplash();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        tvTitle = toolbar.findViewById(R.id.textView_title);
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"pacifico.ttf");
        tvTitle.setTypeface(tf);

        AccountHeader header = new AccountHeaderBuilder().withActivity(this).withHeaderBackground(R.drawable.drawer_header_2)
                .withSavedInstance(savedInstanceState).build();
        myDrawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.home).withIcon(R.drawable.home_96_drawer).withSelectedIcon(R.drawable.home_96).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.list_place).withIcon(R.drawable.tails_96_drawer).withSelectedIcon(R.drawable.tails_96).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.gallery).withIcon(R.drawable.gallery_96_drawer).withSelectedIcon(R.drawable.gallery_96).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.maps).withIcon(R.drawable.map_marker_96_drawer).withSelectedIcon(R.drawable.map_marker_96).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.about).withIcon(R.drawable.about_96_drawer).withSelectedIcon(R.drawable.about_96).withIdentifier(5)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (currentIdentifier != drawerItem.getIdentifier()) {
                                if (drawerItem.getIdentifier() == 1) {
                                    currentTitle = getResources().getString(R.string.home);
                                    currentIdentifier = 1;
                                    HomeFragment hm = new HomeFragment();
                                    hm.setDrawer(myDrawer);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, hm).commit();
                                    if(getSupportActionBar()!=null){
                                        //getSupportActionBar().setTitle(currentTitle);
                                        tvTitle.setText(currentTitle);
                                        getSupportActionBar().setElevation(5);
                                    }

                                } else if (drawerItem.getIdentifier() == 2) {
                                    currentTitle = getResources().getString(R.string.list_place);
                                    currentIdentifier = 2;
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListPlaceFragment()).commit();
                                    if(getSupportActionBar()!=null){
                                        //getSupportActionBar().setTitle(currentTitle);
                                        tvTitle.setText(currentTitle);
                                        getSupportActionBar().setElevation(5);
                                    }

                                } else if (drawerItem.getIdentifier() == 3) {
                                    currentTitle = getResources().getString(R.string.gallery);
                                    currentIdentifier = 3;
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new InfoGalleryFragment()).commit();
                                    if(getSupportActionBar()!=null){
                                        //getSupportActionBar().setTitle(currentTitle);
                                        tvTitle.setText(currentTitle);
                                        getSupportActionBar().setElevation(5);
                                    }

                                } else if (drawerItem.getIdentifier() == 4) {
                                    currentTitle = getResources().getString(R.string.maps);
                                    currentIdentifier = 4;
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapsFragment()).commit();
                                    if(getSupportActionBar()!=null){
                                        //getSupportActionBar().setTitle(currentTitle);
                                        tvTitle.setText(currentTitle);
                                        getSupportActionBar().setElevation(5);
                                    }

                                } else if (drawerItem.getIdentifier() == 5) {
                                    currentTitle = getResources().getString(R.string.about);
                                    currentIdentifier = 5;
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new AboutFragment()).commit();
                                    if(getSupportActionBar()!=null){
                                        //getSupportActionBar().setTitle(currentTitle);
                                        tvTitle.setText(currentTitle);
                                        getSupportActionBar().setElevation(0);
                                    }

                                } else {
                                    currentTitle = getResources().getString(R.string.home);

                                }
                            }
                        }
                        return false;
                    }
                }).withSavedInstance(savedInstanceState).build();

        currentTitle = getResources().getString(R.string.home);
        if(getSupportActionBar()!=null){
            //getSupportActionBar().setTitle(currentTitle);
            tvTitle.setText(currentTitle);
            getSupportActionBar().setElevation(5);
        }

        HomeFragment h = new HomeFragment();
        h.setDrawer(myDrawer);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, h).commit();
        currentIdentifier = 1;
    }


    @Override
    protected void onResume() {
        super.onResume();
        ads.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ads.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ads.onBackPressed();
    }

}
