package com.RCGR.MainApplication;

import android.app.Activity;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyActivity extends Activity implements OnMapReadyCallback {




    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap map) {



            LatLng StartLatLng = new LatLng(-33.867, 151.206);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(StartLatLng, 13));

            map.addMarker(new MarkerOptions()
                    .title("Huidige Locatie")
                    .snippet("Hier bevindt u zich momenteel.")
                    .position(StartLatLng));



    }








}
