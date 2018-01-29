package com.yoesuv.infomalangbatu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yoesuv.infomalangbatu.MainGalleryDetail;
import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.adapter.InfoGalleryAdapter;
import com.yoesuv.infomalangbatu.model.InfoGallery;
import com.yoesuv.infomalangbatu.utils.GalleryApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoGalleryFragment extends Fragment implements AdapterView.OnItemClickListener{

    private CoordinatorLayout cLayout;
    private GridView gView;
    private InfoGalleryAdapter adapter;
    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cLayout = getActivity().findViewById(R.id.coordinator_layout);
        gView = view.findViewById(R.id.grid_thumbnail);
        gView.setOnItemClickListener(this);
        gView.setDrawSelectorOnTop(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new InfoGalleryAdapter(getActivity(),0);
        gView.setAdapter(adapter);
        gView.setVerticalScrollBarEnabled(false);

        if(InfoGalleryFragment.this.isVisible()){
            loadGallery();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InfoGallery ig = (InfoGallery) parent.getItemAtPosition(position);

        Intent i = new Intent(getActivity(), MainGalleryDetail.class);
        i.putExtra(MainGalleryDetail.EXTRA_IMAGE, ig.getImage());
        i.putExtra(MainGalleryDetail.EXTRA_DESC, ig.getCaption());
        startActivity(i);
    }

    private void loadGallery(){
        adapter.clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.gallery_feed))
                .addConverterFactory(GsonConverterFactory.create()).build();

        GalleryApiInterface iface = retrofit.create(GalleryApiInterface.class);
        Call<List<InfoGallery>> callData = iface.callGallery();
        callData.enqueue(new Callback<List<InfoGallery>>() {
            @Override
            public void onResponse(Call<List<InfoGallery>> call, Response<List<InfoGallery>> response) {
                if(response.isSuccessful()){
                    for(InfoGallery ig:response.body()){
                        adapter.add(ig);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    if(InfoGalleryFragment.this.isVisible()){
                        snackbar = Snackbar.make(cLayout, getResources().getString(R.string.connection_fail), Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadGallery();
                            }
                        }).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<InfoGallery>> call, Throwable t) {
                if(InfoGalleryFragment.this.isVisible()){
                    snackbar = Snackbar.make(cLayout, getResources().getString(R.string.no_inet), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadGallery();
                        }
                    }).show();
                }
            }
        });
    }
}
