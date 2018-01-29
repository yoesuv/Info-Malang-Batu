package com.yoesuv.infomalangbatu.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.yoesuv.infomalangbatu.MainPlaceDetail;
import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.adapter.ListPlaceAdapter;
import com.yoesuv.infomalangbatu.model.ListPlace;
import com.yoesuv.infomalangbatu.utils.ListPlaceApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPlaceFragment extends ListFragment{

    private ListPlaceAdapter adapter;
    private Retrofit retrofit;
    private Call<List<ListPlace>> callData;

    private CoordinatorLayout cLayout;
    private Snackbar snackbar;

    private int idLokasi,idLokasiBefore;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        idLokasiBefore = 100;

        cLayout = getActivity().findViewById(R.id.coordinator_layout);
        setHasOptionsMenu(true);

        getListView().setDivider(new ColorDrawable(Color.TRANSPARENT));
        getListView().setPadding(0, 5, 0, 5);
        getListView().setVerticalScrollBarEnabled(false);
        getListView().setDrawSelectorOnTop(true);
        getListView().setClipToPadding(false);

        adapter = new ListPlaceAdapter(getActivity(),0);

        idLokasi = 0;
        loadTempat(idLokasi);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(getActivity(), MainPlaceDetail.class);
        i.putExtra(MainPlaceDetail.EXTRA_PLACE, adapter.getItem(position));
        startActivity(i);
    }

    private void loadTempat(int id){

        if(snackbar!=null){
            if(snackbar.isShown()){
                snackbar.dismiss();
            }
        }

        if(idLokasiBefore==id){
            return;
        }

        setListShown(false);
        adapter.clear();

        if(id==0){
            retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.place_feed)).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }else if(id==1){
            retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.place_feed_kota_malang)).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }else if(id==2){
            retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.place_feed_kota_batu)).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }else if(id==3){
            retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.place_feed_kab_malang)).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        ListPlaceApiInterface iface = retrofit.create(ListPlaceApiInterface.class);

        if(id==0){
            callData = iface.callSemua();
        }else if(id==1){
            callData = iface.callkotaMalang();
        }else if(id==2){
            callData = iface.callkotaBatu();
        }else if(id==3){
            callData = iface.callkabMalang();
        }

        callData.enqueue(new Callback<List<ListPlace>>() {
            @Override
            public void onResponse(Call<List<ListPlace>> call, Response<List<ListPlace>> response) {
                if(response.isSuccessful()){
                    for(ListPlace dt:response.body()){
                        adapter.add(dt);
                    }
                    adapter.notifyDataSetChanged();
                    setListAdapter(adapter);
                    if(ListPlaceFragment.this.isVisible()){
                        setListShown(true);
                    }
                }else{
                    if(ListPlaceFragment.this.isVisible()){
                        setListShown(true);
                        snackbar = Snackbar.make(cLayout, getResources().getString(R.string.connection_fail), Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadTempat(idLokasi);
                            }
                        }).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListPlace>> call, Throwable t) {
                if(ListPlaceFragment.this.isVisible()){
                    setListShown(true);
                    snackbar = Snackbar.make(cLayout, getResources().getString(R.string.no_inet), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadTempat(idLokasi);
                        }
                    }).show();
                }
            }
        });

        idLokasiBefore = id;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.semua){
            idLokasi = 0;
            item.setChecked(true);
            loadTempat(idLokasi);
        }else if(id==R.id.list_kota_malang){
            idLokasi = 1;
            item.setChecked(true);
            loadTempat(idLokasi);
        }else if(id==R.id.list_kota_batu){
            idLokasi = 2;
            item.setChecked(true);
            loadTempat(idLokasi);
        }else if(id==R.id.list_kab_malang){
            idLokasi = 3;
            item.setChecked(true);
            loadTempat(idLokasi);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(snackbar!=null) {
            if (snackbar.isShown()) {
                snackbar.dismiss();
            }
        }
    }

}
