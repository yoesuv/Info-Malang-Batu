package com.yoesuv.infomalangbatu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.utils.ActivityHelper;
import com.yoesuv.infomalangbatu.utils.GravityLightTextView;

public class ChildFragmentChangelog extends Fragment {

    public static ChildFragmentChangelog getInstance(){
        return new ChildFragmentChangelog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_changelog, container, false);

        TextView tv1 = v.findViewById(R.id.textView_ver_1);
        tv1.setText(ActivityHelper.fromHtml(getResources().getString(R.string.ver_1_info)));

        TextView tv2 = v.findViewById(R.id.textView_ver_2_info);
        tv2.setText(ActivityHelper.fromHtml(getResources().getString(R.string.ver_2_info)));

        TextView tv3 = v.findViewById(R.id.textView_ver_3_info);
        tv3.setText(ActivityHelper.fromHtml(getResources().getString(R.string.ver_3_info)));

        TextView tv4 = v.findViewById(R.id.textView_ver_4_info);
        tv4.setText(ActivityHelper.fromHtml(getResources().getString(R.string.ver_4_info)));

        TextView tv5 = v.findViewById(R.id.textView_ver_5_info);
        tv5.setText(ActivityHelper.fromHtml(getResources().getString(R.string.ver_5_info)));

        GravityLightTextView tv6 = v.findViewById(R.id.textView_ver_6_info);
        tv6.setText(ActivityHelper.fromHtml(getResources().getString(R.string.ver_6_info)));

        return v;
    }
}
