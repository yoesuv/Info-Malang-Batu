package com.yoesuv.infomalangbatu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoesuv.infomalangbatu.R;

public class ChildFragmentChangelog extends Fragment {

    private View v;
    private TextView tv1,tv2,tv3,tv4;

    public static ChildFragmentChangelog getInstance(){
        return new ChildFragmentChangelog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_changelog, container, false);

        tv1 = (TextView) v.findViewById(R.id.textView_ver_1);
        tv1.setText(Html.fromHtml(getResources().getString(R.string.ver_1_info)));

        tv2 = (TextView) v.findViewById(R.id.textView_ver_2_info);
        tv2.setText(Html.fromHtml(getResources().getString(R.string.ver_2_info)));

        tv3 = (TextView) v.findViewById(R.id.textView_ver_3_info);
        tv3.setText(Html.fromHtml(getResources().getString(R.string.ver_3_info)));

        tv4 = (TextView) v.findViewById(R.id.textView_ver_4_info);
        tv4.setText(Html.fromHtml(getResources().getString(R.string.ver_4_info)));

        return v;
    }
}
