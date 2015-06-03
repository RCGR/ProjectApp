package com.coldfushion.MainProjectApplication;

import android.app.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MyActivity extends Activity implements OnMapReadyCallback{

    //start of drawer code
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mMenuItems;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    //end of drawer code


    private LocationManager mLocationManager;
    private Location MyLoc;

    GoogleMap Theonemap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //set the map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //for the menu drawer
        mTitle = mDrawerTitle = getTitle();
        mMenuItems = getResources().getStringArray(R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMenuItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
                selectItem(0);
            }

            public void onDrawerOpened(View view) {
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //set the standard selected item on  0 --> the first item (kaart)
        selectItem(0);
        //end of menu drawer

    }

    @Override
    protected void onResume() {
        super.onResume();
        selectItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        //set the map also in theonemap so that we can edit it later on.
        Theonemap = map;
        //get the location of the device to startlocation
        Location StartLocation = getLocation();



        //check if the startlocation is filled
        if (StartLocation != null) {
            //set the map to location of the device
            LatLng StartLatLng = new LatLng(StartLocation.getLatitude(), StartLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(StartLatLng, 13));

            map.addMarker(new MarkerOptions()
                    .title("Huidige Locatie")
                    .snippet("Hier bevindt u zich momenteel.")
                    .position(StartLatLng));
        }
        else {
            //need to set location to standard position if the location of the device is not known
            LatLng sydney = new LatLng(51.92, 4.48);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
            map.addMarker(new MarkerOptions()
                    .title("Sydney")
                    .snippet("The most populous city in Australia.")
                    .position(sydney));
        }

        //ADD CODE FOR ADDING MARKERS TO THE MAP FOR EVERY ACTIVITY
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                String x = result.substring(10, result.length()-1);

                double latitude = Double.parseDouble(x.substring(0, x.indexOf(",")));
                double longitude = Double.parseDouble(x.substring(x.indexOf(",") + 1));

                LatLng NewLocation = new LatLng(latitude, longitude);

                Theonemap.moveCamera(CameraUpdateFactory.newLatLngZoom(NewLocation, 13));
                Theonemap.addMarker(new MarkerOptions().snippet( "nieuwe locatie ").position(NewLocation).title("Location"));

            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Geen nieuwe locatie gevonden", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    /*a
     * Uitjes-zoeken stuff
     */

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    public void selectItem(int position){
        if (mMenuItems[position].toLowerCase().equals("datum kiezen")){
            Intent DateChooseIntent = new Intent(getApplicationContext(), DateChoose.class);
            startActivity(DateChooseIntent);
        }
        else if(mMenuItems[position].toLowerCase().equals("locatie wijzigen")){
            Intent LocationChooseIntent = new Intent(getApplicationContext(), LocationChoose.class);
            startActivityForResult(LocationChooseIntent, 1);

        }
        else if (mMenuItems[position].toLowerCase().equals("suggestie maken")){
            Intent MakeSuggestionIntent = new Intent(getApplicationContext(), MakeSuggestion.class);
            startActivity(MakeSuggestionIntent);
        }
        else if(mMenuItems[position].toLowerCase().equals("uitje beoordelen")){
            Intent RateActivityIntent = new Intent(getApplicationContext(), RateActivities.class);
            startActivity(RateActivityIntent);
        }
        else if (mMenuItems[position].toLowerCase().equals("alle uitjes")) {
            Intent AlleUitjes = new Intent(getApplicationContext(), ResultActivity.class);
            startActivity(AlleUitjes);
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    // get location of the device
    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            //check if gps or network is on.
            boolean GPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean NetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //define the locations for gps and network
            Location GPSLoc =null;
            Location NetworkLoc = null;

            //throw exception if none of the services is available
            if(!GPSEnabled && !NetworkEnabled){
                throw new Exception("Geen netwerk beschikbaar");
            }
            else {
                if (GPSEnabled) {
                    //get the location with gps
                    GPSLoc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (NetworkEnabled){
                    //get the location with network
                    NetworkLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                //check if the locations are both not null
                if (GPSLoc != null && NetworkLoc != null){
                    //check for the location with the highest accuracy and give it as result
                    if(GPSLoc.getAccuracy() >= NetworkLoc.getAccuracy()){
                        MyLoc = GPSLoc;
                    }
                    else {
                        MyLoc = NetworkLoc;
                    }
                }
                else {
                    //return the location of gps or network depending on who isnt null
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
        //return location
        return  MyLoc;
    }
}
