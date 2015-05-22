package com.coldfushion.MainProjectApplication;

import android.app.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyActivity extends Activity implements OnMapReadyCallback{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mMenuItems;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

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

        mDrawerToggle  = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view){
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View view){
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(savedInstanceState == null){
            selectItem(0);
        }




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
        LatLng sydney = new LatLng(-33.867, 151.206);
        Theonemap = map;

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }

    /*
     * Uitjes-zoeken stuff
     */

     public void ZoekNaarUitjes(View v)
     {
         Intent AlleUitjes = new Intent(getApplicationContext(), ResultActivity.class);
         startActivity(AlleUitjes);
     }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    public void selectItem(int position){
        Toast.makeText(getApplicationContext(), mMenuItems[position], Toast.LENGTH_SHORT).show();
        Log.d("test", "doet het");

        if (mMenuItems[position].toLowerCase().equals("datum kiezen")){
            Toast.makeText(getApplicationContext(), "datum wijzigen ", Toast.LENGTH_SHORT).show();
            Intent DateChooseIntent = new Intent(getApplicationContext(), DateChoose.class);
            startActivity(DateChooseIntent);
        }
        else if(mMenuItems[position].toLowerCase().equals("locatie wijzigen")){
            Toast.makeText(getApplicationContext(), "lcaotie wijzigen", Toast.LENGTH_SHORT).show();
            Intent LocationChooseIntent = new Intent(getApplicationContext(), LocationChoose.class);
            startActivity(LocationChooseIntent);
        }
        else if (mMenuItems[position].toLowerCase().equals("suggestie maken")){
            Toast.makeText(getApplicationContext(), "suggestie maken ofzo", Toast.LENGTH_SHORT).show();
            Intent MakeSuggestionIntent = new Intent(getApplicationContext(), MakeSuggestion.class);
            startActivity(MakeSuggestionIntent);
        }
        else if(mMenuItems[position].toLowerCase().equals("uitje beoordelen")){
            Toast.makeText(getApplicationContext(), "uitjes beoordelen", Toast.LENGTH_SHORT).show();
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
}
