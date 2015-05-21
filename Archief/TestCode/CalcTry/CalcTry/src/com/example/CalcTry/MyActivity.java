package com.example.CalcTry;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;




public class MyActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap TheOneMap = null;
    private LocationManager mLocationManager = null;
    private Location MyLoc = null;
    private GoogleApiClient mGoogleApiClient;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();


    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        TheOneMap = map;
        Location StartLocation = getLocation();
        if (StartLocation != null) {

            LatLng StartLatLng = new LatLng(StartLocation.getLatitude(), StartLocation.getLongitude());

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(StartLatLng, 17));

            map.addMarker(new MarkerOptions()
                    .title("Huidige Locatie")
                    .snippet("Hier bevindt u zich momenteel.")
                    .position(StartLatLng));
        }


    }

    public  void test(View v){
        Toast.makeText(v.getContext(), "hoi", Toast.LENGTH_SHORT).show();
        Notification.Builder asdx = new Notification.Builder(this).setContentTitle("New App Notification").setContentText("Ur mobile phone has just crashed").setSmallIcon(R.drawable.house);
        NotificationManager mnotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);




        if (TheOneMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            TheOneMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            LatLng asdasd = new LatLng(MyLoc.getLatitude() + 10, MyLoc.getLongitude()+ 10);

            TheOneMap.moveCamera(CameraUpdateFactory.newLatLngZoom(asdasd, 3));

            Marker asdasdMarker = TheOneMap.addMarker(new MarkerOptions()
                    .title("asdasd")
                    .snippet("testlocation.")
                    .anchor(1.0f, 1.0f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                    .position(asdasd));


        }
        else {


            Location trolxd = getLocation();
            if (trolxd != null) {
                LatLng asd = new LatLng(trolxd.getLatitude(), trolxd.getLongitude());
                TheOneMap.moveCamera(CameraUpdateFactory.newLatLngZoom(asd, 17));
                TheOneMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                TheOneMap.addMarker(new MarkerOptions().title("Huidige Locatie").snippet("Hier bevind u zich nu.").position(asd));
            }

        }
    }
    public void test4 (View v){
        for (int i = Math.round(TheOneMap.getMinZoomLevel()); i <= Math.round(TheOneMap.getMaxZoomLevel()); i++) {
            if (i == Math.round(TheOneMap.getCameraPosition().zoom)) {
                i++;
                if (TheOneMap.getMaxZoomLevel() > (float) i) {
                    TheOneMap.animateCamera(CameraUpdateFactory.zoomTo((float) i));
                }
                else {
                    TheOneMap.animateCamera(CameraUpdateFactory.zoomTo(TheOneMap.getMinZoomLevel()));
                }
                break;
            }
        }
    }

    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            boolean GPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean NetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Location GPSLoc =null;
            Location NetworkLoc = null;


            if(!GPSEnabled && !NetworkEnabled){
                throw new Exception("Geen netwerk beschikbaar");
            }
            else {
                if (GPSEnabled) {
                    GPSLoc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (NetworkEnabled){
                    NetworkLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if (GPSLoc != null && NetworkLoc != null){
                    if(GPSLoc.getAccuracy() >= NetworkLoc.getAccuracy()){
                        MyLoc = GPSLoc;
                    }
                    else {
                        MyLoc = NetworkLoc;
                    }
                }
                else {
                    if (GPSLoc != null){
                        MyLoc = GPSLoc;
                    }
                    else if (NetworkLoc != null){
                        MyLoc = NetworkLoc;
                    }
                }

            }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }

        return  MyLoc;
    }




//    public void calc(View v)
//    {
//        String tekst = "Niet alle waardes ingevuld";
//        Button b = (Button) findViewById(v.getId());
//
//        Object kar = b.getText();
//        //kar.
//
//        EditText bas = (EditText) findViewById(R.id.et_1);
//        EditText sab = (EditText) findViewById(R.id.et_2);
//
//
//
//        if (bas.getText().toString() != null && !bas.getText().toString().isEmpty() ) {
//            if ( sab.getText().toString() != null && !sab.getText().toString().isEmpty()) {
//
//                int x = Integer.parseInt(bas.getText().toString());
//                int y = Integer.parseInt(sab.getText().toString());
//
//
//                switch (v.getId()) {
//                    case R.id.P:
//                        tekst = "" + x + " + " + y + " = " + (x + y) + "";
//                        break;
//                    case R.id.M:
//                        tekst = "" + x + " - " + y + " = " + (x - y) + "";
//                        break;
//                    case R.id.K:
//                        tekst = "" + x + " * " + y + " = " + (x * y) + "";
//                        break;
//                    case R.id.D:
//                        tekst = "" + x + " / " + y + " = " + (x / y) + "";
//                        break;
//                }
//            }
//        }
//        Toast.makeText(v.getContext(), tekst, Toast.LENGTH_SHORT).show();
//
//        Intent ina = new Intent(getApplicationContext(), h.class);
//        startActivity(ina);
//
//    }

}
