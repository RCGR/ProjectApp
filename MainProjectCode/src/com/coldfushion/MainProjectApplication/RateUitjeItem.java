package com.coldfushion.MainProjectApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ceesjan on 29-5-2015.
 */
public class RateUitjeItem extends Activity {

    TextView textViewBeschrijving;
    TextView textViewCategorie;
    TextView textViewWeertype;
    TextView textViewEmail;
    TextView textViewStraat;
    TextView textViewPostcode;
    TextView textViewStad;
    TextView textViewName;

    // Progress Dialog
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> uitjesList;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url waar het PHPscript dat we willen zich bevind
    private String id_detail = "";
    private String url_get_details = "http://coldfusiondata.site90.net/db_get_details_suggestion.php?id=";

    // We maken hier vars aan voor de JSON Node names
    private static final String TAG_UITJES = "Uitjes";
    private static final String TAG_NAME = "Naam";
    private static final String TAG_WEERTYPE = "WeerType";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BESCHRIJVING = "Beschrijving";
    private static final String TAG_CATEGORIE = "Categorie";
    private static final String TAG_EMAIL = "Email";
    private static final String TAG_STRAAT = "Straat";
    private static final String TAG_POSTCODE = "PostCode";
    private static final String TAG_STAD = "Stad";


    // Hier maken we de uitjes JSONArray
    JSONArray uitjes = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rateuitjeitem_layout);

        Bundle extras = getIntent().getExtras();
        id_detail = extras.get("number").toString();

        textViewStraat = (TextView) findViewById(R.id.Rate_Straat);
        textViewPostcode = (TextView) findViewById(R.id.Rate_Postcode);
        textViewStad = (TextView) findViewById(R.id.Rate_Plaats);
        textViewEmail = (TextView) findViewById(R.id.Rate_Email);
        textViewBeschrijving = (TextView) findViewById(R.id.Rate_Beschrijving);
        textViewCategorie = (TextView) findViewById(R.id.Rate_Categorie);
        textViewName = (TextView) findViewById(R.id.Rate_Name);
        textViewWeertype = (TextView) findViewById(R.id.Rate_Weertype);

        uitjesList = new ArrayList<HashMap<String, String>>();
        new LoadDetailsSuggestedUitjes().execute();
    }

    class LoadDetailsSuggestedUitjes extends AsyncTask<String, String, String> {

        /*
                * Voordat we de taak starten laten we netjes een "zandloper" zien
        */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RateUitjeItem.this);
            pDialog.setMessage("Details worden opgehaald... even geduld!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        /*
                * getting All from url
        */

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL

            String final_url = url_get_details + id_detail;

            JSONObject json = jParser.makeHttpRequest(final_url, "GET", params);
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
                    String email = x.getString(TAG_EMAIL);
                    String weertype = x.getString(TAG_WEERTYPE);

                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_CATEGORIE, categorie);
                    map.put(TAG_BESCHRIJVING, beschrijving);
                    map.put(TAG_STAD, stad);
                    map.put(TAG_STRAAT, straat);
                    map.put(TAG_POSTCODE, postcode);
                    map.put(TAG_EMAIL, email);
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

        /*
                * After completing background task Dismiss the progress dialog
        * */

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /*
                    * Updating parsed JSON data into textviews
                    */


                    textViewBeschrijving.setText(uitjesList.get(0).get(TAG_BESCHRIJVING));
                    textViewCategorie.setText(uitjesList.get(0).get(TAG_CATEGORIE));
                    textViewWeertype.setText(uitjesList.get(0).get(TAG_WEERTYPE));
                    textViewEmail.setText(uitjesList.get(0).get(TAG_EMAIL));
                    textViewStraat.setText(uitjesList.get(0).get(TAG_STRAAT));
                    textViewName.setText(uitjesList.get(0).get(TAG_NAME));
                    textViewPostcode.setText(uitjesList.get(0).get(TAG_POSTCODE));
                    textViewStad.setText(uitjesList.get(0).get(TAG_STAD));
                }
            });

        }
    }
}


