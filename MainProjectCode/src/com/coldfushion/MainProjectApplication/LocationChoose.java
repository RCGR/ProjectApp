package com.coldfushion.MainProjectApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.*;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ceesjan on 22-5-2015.
 */
public class LocationChoose extends Activity {
    //start of drawer code
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mMenuItems;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    //end of drawer code

    /*

    JSON STUFF

     */

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> locationList;

    // url waar het PHPscript dat we willen zich bevind
    private static String url_location = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&key=AIzaSyCT-InirSIGccbPP_94ry1iuwKR3xgxopY";

    // We maken hier vars aan voor de JSON Node names
    private static final String TAG_RESULT = "result";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LONG = "lng";

    // Hier maken we de JSONArray
    JSONArray location = null;

    class LoadAllUitjes extends AsyncTask<String, String, String> {

        /**
         * Voordat we de taak starten laten we netjes een "zandloper" zien
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LocationChoose.this);
            pDialog.setMessage("... even geduld!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        /**
         * getting All from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL

            JSONObject json = jParser.makeHttpRequest(url_location, "GET", params);
            if (json == null) {
                Log.d("jsonechek", "jsonempty");
            }
            // Check your log cat for JSON reponse
            Log.d("Location: ", json.toString());

                try
                {
                    // products found
                    // Getting Array of Products
                    location = json.getJSONArray(TAG_RESULT);

                    // looping through All Products
                    for (int i = 0; i < location.length(); i++)
                    {
                        JSONObject c = location.getJSONObject(i);

                        // Storing each json item in variable
                        String lat = c.getString(TAG_LAT);
                        String lng = c.getString(TAG_LONG);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_LAT, lat);
                        map.put(TAG_LONG, lng);

                        // adding HashList to ArrayList
                        locationList.add(map);

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
        return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url)
        {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

            /*
            *
            * TO DO: IETS DOEN MET DE TERUGGEGEVEN JSON!
            *
             */
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationchoose_layout);

        //code for the drawer
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
                selectItem(1);
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

        //end code for the drawer
    }

    @Override
    protected void onStart() {
        super.onStart();
        selectItem(1);
    }

    //start of drawer code
    @Override
    protected void onResume() {
        super.onResume();
        selectItem(1);
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
    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    public void selectItem(int position){
        if (mMenuItems[position].toLowerCase().equals("datum kiezen")){
            this.finish();
            Intent DateChooseIntent = new Intent(getApplicationContext(), DateChoose.class);
            startActivity(DateChooseIntent);
        }
        else if (mMenuItems[position].toLowerCase().equals("bekijk uitjes op kaart")){
            this.finish();
        }
        else if (mMenuItems[position].toLowerCase().equals("alle uitjes")) {
            this.finish();
            Intent AlleUitjes = new Intent(getApplicationContext(), ResultActivity.class);
            startActivity(AlleUitjes);
        }

        else if (mMenuItems[position].toLowerCase().equals("suggestie maken")){
            this.finish();
            Intent MakeSuggestionIntent = new Intent(getApplicationContext(), MakeSuggestion.class);
            startActivity(MakeSuggestionIntent);
        }
        else if(mMenuItems[position].toLowerCase().equals("uitje beoordelen")){
            this.finish();
            Intent RateActivityIntent = new Intent(getApplicationContext(), RateActivities.class);
            startActivity(RateActivityIntent);
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
    //end of drawer code
}