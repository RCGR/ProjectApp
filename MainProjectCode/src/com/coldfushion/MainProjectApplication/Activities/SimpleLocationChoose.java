package com.coldfushion.MainProjectApplication.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import com.coldfushion.MainProjectApplication.Helpers.MyJavaScriptInterface;
import com.coldfushion.MainProjectApplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kraaijeveld on 8-6-2015.
 */
public class SimpleLocationChoose extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationchoose_layout);
        webview = (WebView) findViewById(R.id.WebviewLocationChoose);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.addJavascriptInterface(myjavascriptinterface, "HTMLViewer");

        webview.loadUrl("http://school.ceesjannolen.nl/app/index.html");

        //googleapiclient
        mGoogleApiClient = new GoogleApiClient.Builder(SimpleLocationChoose.this).addApi(Places.GEO_DATA_API).addConnectionCallbacks(this).build();
        mGoogleApiClient.connect();
    }

        //VARS
        Boolean latlngset = false;
        WebView webview;
        MyJavaScriptInterface myjavascriptinterface = new MyJavaScriptInterface(this);
        GoogleApiClient mGoogleApiClient;
        private LatLng newlatlng;
        String placeId;
        ProgressDialog pDialog;

    //location choose event click
    public void ChooseLocation(View view) {

        while (myjavascriptinterface.getPlaceID().equals("")) {

            webview.loadUrl("javascript:window.HTMLViewer.getHTML(document.getElementById('placeid').innerHTML);");
        }
        placeId = myjavascriptinterface.getPlaceID();

        Test(placeId);
    }

    @Override
    public void onConnected(Bundle bundle) {
        //mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i("Log Scues Apicleint", "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e("error apicleint", "Google Places API connection suspended.");
    }

    public void Test(String placeId) {
        Log.d("place id = : ", placeId);
        if (mGoogleApiClient.isConnected() && mGoogleApiClient != null) {
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        } else {
            Toast.makeText(getApplicationContext(), " googleapicleint error", Toast.LENGTH_SHORT).show();
        }
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("errormesgae", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                places.release();
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            Log.d("PLACE FOUND", place.getName() + " latlng= " + place.getLatLng());
            newlatlng = place.getLatLng();
            Log.d(" latlng set", newlatlng.toString());
            places.release();
            Toast.makeText(getApplicationContext(), newlatlng.toString(), Toast.LENGTH_SHORT).show();

            String result = newlatlng.toString();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",result);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    };



}