package com.coldfushion.MainProjectApplication.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.coldfushion.MainProjectApplication.Helpers.JSONParser;
import com.coldfushion.MainProjectApplication.Helpers.getGooglePlacesData;
import com.coldfushion.MainProjectApplication.Helpers.getWebpageContent;
import com.coldfushion.MainProjectApplication.R;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ceesjan on 28-5-2015.
 */
public class DetailUitje extends Activity {
    public String openingstijden = "";
    TextView textViewOpeningHours;
    TextView textViewBeschrijving;
    TextView textViewCategorie;
    TextView textViewWeertype;
    TextView textViewTelefoon;
    TextView textViewStraat;
    TextView textViewPostcode;
    TextView textViewStad;
    TextView textViewName;
    Button buttonTelefoon;

    // Progress Dialog
    private ProgressDialog pDialog;
    private String PhoneNumber;

    ArrayList<HashMap<String, String>> uitjesList;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url waar het PHPscript dat we willen zich bevind
    private String id_detail = "";
    private String url_get_details = "http://coldfusiondata.site90.net/db_get_details.php?id=";

    // We maken hier vars aan voor de JSON Node names
    private static final String TAG_UITJES = "Uitjes";
    private static final String TAG_NAME = "Naam";
    private static final String TAG_WEERTYPE = "WeerType";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BESCHRIJVING = "Beschrijving";
    private static final String TAG_CATEGORIE = "Categorie";
    private static final String TAG_TELEFOON = "Telefoon";
    private static final String TAG_STRAAT = "Straat";
    private static final String TAG_POSTCODE = "PostCode";
    private static final String TAG_STAD = "Stad";


    // Hier maken we de uitjes JSONArray
    JSONArray uitjes = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailuitje_layout);
        Bundle extras = getIntent().getExtras();
        Toast.makeText(getApplicationContext(), extras.get("number").toString(), Toast.LENGTH_SHORT).show();
        id_detail = extras.get("number").toString();
        textViewBeschrijving = (TextView)findViewById(R.id.beschrijving);
        textViewCategorie = (TextView)findViewById(R.id.Categorie);
        textViewWeertype = (TextView)findViewById(R.id.weertype);
        textViewTelefoon = (TextView)findViewById(R.id.telefoon);
        textViewStraat = (TextView)findViewById(R.id.Straat);
        textViewPostcode = (TextView)findViewById(R.id.Postcode);
        textViewStad = (TextView)findViewById(R.id.stad);
        textViewName = (TextView)findViewById(R.id.Naam);
        textViewOpeningHours = (TextView)findViewById(R.id.Uitje_Openingstijden);
        buttonTelefoon = (Button)findViewById(R.id.telefoonbutton);


        getGooglePlacesData getGooglePlacesData = new getGooglePlacesData();
        getGooglePlacesData.id = id_detail;
        getGooglePlacesData.suggestion = false;
        getGooglePlacesData.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String google_places_url = getGooglePlacesData.google_places_url;

                getWebpageContent getWebpageContent = new getWebpageContent();
                getWebpageContent.readWebpage(google_places_url);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openingstijden = getWebpageContent.openingstijden;

                        Log.d("openingstijden ", openingstijden);
                        textViewOpeningHours.setText(openingstijden);
                    }
                }, 3000);
            }
        }, 3000);

        uitjesList = new ArrayList<HashMap<String, String>>();
        new LoadDetailUitje().execute();




    }
    class LoadDetailUitje extends AsyncTask<String, String, String> {

        /**
         * Voordat we de taak starten laten we netjes een "zandloper" zien
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailUitje.this);
            pDialog.setMessage("Details worden opgehaald... even geduld!");
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

            String final_url = url_get_details + id_detail;
            JSONObject json = null;
            try {
            json = jParser.makeHttpRequest(final_url, "GET", params);
            }catch (Exception e){
                e.printStackTrace();
            }
            if (json == null) {
                Log.d("jsonechek", "jsonempty");
            }
            // Check your log cat for JSON reponse
            Log.d("Details: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    uitjes = json.getJSONArray(TAG_UITJES);

                    JSONObject x = uitjes.getJSONObject(0);
                        // Storing each json item in variable
                        String naam = x.getString(TAG_NAME);
                        String categorie = x.getString(TAG_CATEGORIE);
                        String beschrijving = x.getString(TAG_BESCHRIJVING);
                        String stad = x.getString(TAG_STAD);
                        String straat = x.getString(TAG_STRAAT);
                        String postcode = x.getString(TAG_POSTCODE);
                        String telefoon = x.getString(TAG_TELEFOON);
                        String weertype = x.getString(TAG_WEERTYPE);

                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_CATEGORIE, categorie);
                        map.put(TAG_BESCHRIJVING, beschrijving);
                        map.put(TAG_STAD, stad);
                        map.put(TAG_STRAAT, straat);
                        map.put(TAG_POSTCODE, postcode);
                        map.put(TAG_TELEFOON, telefoon);
                        map.put(TAG_WEERTYPE, weertype);
                        // adding HashList to ArrayList
                        uitjesList.add(map);



                } else {
                    // no products found
                    Log.d("Uitjes status", "Details ophalen mislukt");
                    //set the textview to visible

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products

            // updating UI from Background Thread

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textViewBeschrijving.setText(uitjesList.get(0).get(TAG_BESCHRIJVING));
                    textViewCategorie.setText(uitjesList.get(0).get(TAG_CATEGORIE));
                    textViewWeertype.setText(uitjesList.get(0).get(TAG_WEERTYPE));
                    textViewTelefoon.setText(uitjesList.get(0).get(TAG_TELEFOON));
                    textViewStraat.setText(uitjesList.get(0).get(TAG_STRAAT));
                    textViewName.setText(uitjesList.get(0).get(TAG_NAME));
                    textViewPostcode.setText(uitjesList.get(0).get(TAG_POSTCODE));
                    textViewStad.setText(uitjesList.get(0).get(TAG_STAD));
                    pDialog.dismiss();
                }
            },5500);

        }

    }

    public void makeCallToUitje(View view){
        TextView x = (TextView)view;
        PhoneNumber = x.getText().toString();
        AlertBuilderCall();

    }

    public void makeCallToUitjeButton(View view){
        PhoneNumber = textViewTelefoon.getText().toString();
        AlertBuilderCall();
    }

    private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    call(PhoneNumber);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    Toast.makeText(DetailUitje.this, "Geannuleerd.",Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void call(String number) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ number));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("Dialing example", "Call failed", e);
        }
    }

    private void AlertBuilderCall(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Doorgaan met bellen?").setMessage("Weet u het zeker? Er kunnen kosten van uw provider in rekening worden gebracht.").setIcon(17301543)
                .setPositiveButton("Ga door",dialogClickListener ).setNegativeButton("Annuleer", dialogClickListener);
        builder.show();
    }
}