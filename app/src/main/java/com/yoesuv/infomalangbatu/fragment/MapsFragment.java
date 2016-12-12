package com.yoesuv.infomalangbatu.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.model.PinMaps;
import com.yoesuv.infomalangbatu.utils.PinsApiInterface;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener {

    private CoordinatorLayout cLayout;
    private Snackbar snackbar;
    private GoogleMap gMap;

    private int mapType;
    private boolean moveCamera;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
        setHasOptionsMenu(true);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        mapType = 1;
        moveCamera = true;
        if (MapsFragment.this.isVisible()) {
            loadMap(googleMap, mapType);
        }
    }

    /* LOAD MAP */
    private void loadMap(final GoogleMap map, int type) {

        if (snackbar != null) {
            if (snackbar.isShown()) {
                snackbar.dismiss();
            }
        }

        if (type == 1) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (type == 2) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (type == 3) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else if (type == 4) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        map.getUiSettings().setZoomControlsEnabled(true);

        if(moveCamera) {
            /* LOAD PIN LOKASI */
            Retrofit retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.pins_feed))
                    .addConverterFactory(GsonConverterFactory.create()).build();

            PinsApiInterface iface = retrofit.create(PinsApiInterface.class);
            Call<List<PinMaps>> cMaps = iface.call();
            cMaps.enqueue(new Callback<List<PinMaps>>() {
                @Override
                public void onResponse(Response<List<PinMaps>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        for (PinMaps pin : response.body()) {
                            MarkerOptions options = new MarkerOptions().position(new LatLng(pin.getLatitude(), pin.getLongitude()));
                            options.title(pin.getName());

                            if (pin.getLokasi() == 1) {
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            } else if (pin.getLokasi() == 2) {
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                            } else if (pin.getLokasi() == 3) {
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                            }

                            map.addMarker(options);
                        }

                    } else {
                        /* CONNECTION LOST */
                        if (MapsFragment.this.isVisible()) {
                            snackbar = Snackbar.make(cLayout, getResources().getString(R.string.connection_fail), Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadMap(map, mapType);
                                }
                            }).show();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    snackbar = Snackbar.make(cLayout, getResources().getString(R.string.no_inet), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadMap(map, mapType);
                        }
                    }).show();
                }
            });

            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-7.982914, 112.630875)));
            map.animateCamera(CameraUpdateFactory.zoomTo(9));
        }

        map.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.map_normal:
                item.setChecked(true);
                mapType = 1;
                moveCamera = false;
                loadMap(gMap, mapType);
                return true;

            case R.id.map_satellite:
                item.setChecked(true);
                mapType = 2;
                moveCamera = false;
                loadMap(gMap, mapType);
                return true;

            case R.id.map_terrain:
                item.setChecked(true);
                mapType = 3;
                moveCamera = false;
                loadMap(gMap, mapType);
                return true;

            case R.id.map_hybrid:
                item.setChecked(true);
                mapType = 4;
                moveCamera = false;
                loadMap(gMap, mapType);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (snackbar != null) {
            if (snackbar.isShown()) {
                snackbar.dismiss();
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void enableMyLocation() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
            Log.e("permission","ACCESS LOCATION REQUIRED");
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            ab.setMessage(getResources().getString(R.string.location_permission_denied));
            ab.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
        }else if(gMap!=null) {
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            gMap.getUiSettings().setCompassEnabled(true);
        }
    }


}
