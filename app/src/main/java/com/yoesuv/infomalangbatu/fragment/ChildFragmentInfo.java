package com.yoesuv.infomalangbatu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoesuv.infomalangbatu.R;

public class ChildFragmentInfo extends Fragment {

    private View v;

    public static ChildFragmentInfo getInstance(){
        return new ChildFragmentInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_info, container, false);
        return v;
    }
}
