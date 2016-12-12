package com.yoesuv.infomalangbatu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.fragment.ChildFragmentChangelog;
import com.yoesuv.infomalangbatu.fragment.ChildFragmentInfo;
import com.yoesuv.infomalangbatu.fragment.ChildFragmentLibrary;
import com.yoesuv.infomalangbatu.fragment.ChildFragmentThanks;

public class TabAdapter extends FragmentPagerAdapter{

    private String[] text;

    public TabAdapter(Context c, FragmentManager fm) {
        super(fm);
        this.text = c.getResources().getStringArray(R.array.tab_text);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return ChildFragmentInfo.getInstance();
        }else if(position==1){
            return ChildFragmentChangelog.getInstance();
        }else if(position==2){
            return ChildFragmentThanks.getInstance();
        }else{
            return ChildFragmentLibrary.getInstance();
        }
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return text[position];
    }
}
