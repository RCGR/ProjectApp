package com.coldfushion.MainProjectApplication;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyActivity extends Activity implements OnMapReadyCallback{

    GoogleMap Theonemap;
    /**
     * Map stuff down here
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        LatLng sydney = new LatLng(-33.867, 151.206);
        Theonemap = map;

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }

    /*a
     * Uitjes-zoeken stuff
     */

     public void ZoekNaarUitjes(View v)
     {
         Intent AlleUitjes = new Intent(getApplicationContext(), ResultActivity.class);
         startActivity(AlleUitjes);
     }

}
