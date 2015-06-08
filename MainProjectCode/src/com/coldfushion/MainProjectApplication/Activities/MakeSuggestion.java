package com.coldfushion.MainProjectApplication.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.coldfushion.MainProjectApplication.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ceesjan on 22-5-2015.
 */
public class MakeSuggestion extends Activity {
    //start of drawer code
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mMenuItems;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    //end of drawer code

    Button submitButton;
    Button getLocationButton;

    EditText editText_naam;
    EditText editText_beschrijving;

    Spinner spinner_weer;
    Spinner spinner_categorie;



    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.makesuggestion_layout);

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
                selectItem(3);
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

        //end code for the drawer'
        submitButton = (Button)findViewById(R.id.button_Suggestion_submit);
        getLocationButton = (Button)findViewById(R.id.button_Suggestion_getlocation);

        editText_naam = (EditText)findViewById(R.id.EditText_Suggestion_Name);
        editText_beschrijving = (EditText)findViewById(R.id.EditText_Suggestion_Beschrijving);

        spinner_categorie = (Spinner)findViewById(R.id.spinner_Categorie);
        spinner_weer = (Spinner)findViewById(R.id.spinner_weertype);

        String[] weeritems = new String[]{"Sunny", "Cloudy", "Rainy"};
        ArrayAdapter<String> weer_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weeritems);
        spinner_weer.setAdapter(weer_adapter);

        String[] categorieen = new String[]{"Pretpark", "Restaurant", "Museum"};
        ArrayAdapter<String> categorie_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorieen);
        spinner_categorie.setAdapter(categorie_adapter);
    }

    //Deze methode wordt gecalled als de submitbutton geklikt is.
    public void AddToDatabase()
    {
        //add location added check
        if(editText_naam.getText().equals("") ||  editText_beschrijving.getText().equals("") || spinner_weer.getSelectedItem().toString().equals("") || spinner_categorie.getSelectedItem().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "U hebt een van de velden niet ingevoerd!", Toast.LENGTH_SHORT);
        }
        else
        {


            String newNaam = editText_naam.getText().replace(" ", "+");
            String newBeschrijving = editText_beschrijving.getText().replace(" ", "+");
            String newCategorie = spinner_categorie.getSelectedItem().toString().replace(" ", "+");
            String newWeerType = spinner_weer.getSelectedItem().toString().replace(" ", "+");
            //String newemail = email.replace(" ", "+");
            //String newStraat = straat.replace(" ", "+");
            //String newPostcode = postcode.replace(" ", "+");
            //String newStad = stad.replace(" ", "+");
            //String parameters_url =

                    "NaamVar=" + newNaam + "&WeerTypeVar=" + newWeerType + "&BeschrijvingVar=" + newBeschrijving +
                            "&CategorieVar=" + newCategorie + "&EmailVar=" + newemail +
                            "&StraatVar=" +  + "&PostCodeVar=" + newPostcode +
                            "&StadVar=" + newStad + "&CoordinaatVar=" + ;

            final String insert_url = "http://coldfusiondata.site90.net/db_insert_suggestion.php?" + parameters_url + "";
            Log.d("String url", insert_url);
        }
    }

    //Deze methode word gecalled als de getlocation button geklikt is
    public void getLocation()
    {
        Intent i = new Intent(this, SimpleLocationChoose.class);
        startActivityForResult(i, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                String x = result.substring(10, result.length()-1);
                double latitude = Double.parseDouble(x.substring(0, x.indexOf(",")));
                double longitude = Double.parseDouble(x.substring(x.indexOf(",") + 1));

                LatLng NewLocation = new LatLng(latitude, longitude);

            }
            if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Geen nieuwe locatie gevonden", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        selectItem(3);
    }

    //start of drawer code
    @Override
    protected void onResume() {
        super.onResume();
        selectItem(3);
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
        else if(mMenuItems[position].toLowerCase().equals("locatie wijzigen")){
            this.finish();
            Intent LocationChooseIntent = new Intent(getApplicationContext(), LocationChoose.class);
            startActivity(LocationChooseIntent);
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
    public void Suggestion_GetLocation(View view){
        Toast.makeText(getApplicationContext(), "Kies een locatie", Toast.LENGTH_SHORT).show();
    }
}