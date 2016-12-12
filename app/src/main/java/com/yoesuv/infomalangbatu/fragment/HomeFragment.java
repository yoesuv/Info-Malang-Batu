package com.yoesuv.infomalangbatu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.yoesuv.infomalangbatu.R;

public class HomeFragment extends Fragment {

    private TextView tvHome;
    private View v;
    private Button btnStart;

    private Drawer drawer;

    public void setDrawer(Drawer d){
        this.drawer = d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        tvHome = (TextView) v.findViewById(R.id.textView_home_text);
        tvHome.setText(Html.fromHtml(getResources().getString(R.string.home_text)));

        btnStart = (Button) v.findViewById(R.id.buttonMulai);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer();
            }
        });
        return v;
    }
}
