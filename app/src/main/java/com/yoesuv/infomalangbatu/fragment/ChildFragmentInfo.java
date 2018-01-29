package com.yoesuv.infomalangbatu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoesuv.infomalangbatu.BuildConfig;
import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.utils.GravityLightTextView;

public class ChildFragmentInfo extends Fragment {

    public static ChildFragmentInfo getInstance(){
        return new ChildFragmentInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        GravityLightTextView textVersion = v.findViewById(R.id.textView_version_app);
        String appVersion = "Versi "+ BuildConfig.VERSION_NAME;
        textVersion.setText(appVersion);

        return v;
    }
}
