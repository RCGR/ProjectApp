package com.coldfushion.MainProjectApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;
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

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> uitjesList;

    // url waar het PHPscript dat we willen zich bevind
    private String id_detail = "";
    private String url_get_details = "http://coldfusiondata.site90.net/db_get_details.php?id="+ id_detail;


    // We maken hier vars aan voor de JSON Node names
    private static final String TAG_UITJES = "Uitjes";
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
        setContentView(R.layout.detailuitje_layout);
        Bundle extras = getIntent().getExtras();
        Toast.makeText(getApplicationContext(), extras.get("number").toString(), Toast.LENGTH_SHORT).show();
        id_detail = extras.get("number").toString();

    }
    class LoadAllUitjes extends AsyncTask<String, String, String> {

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

                    // looping through All Products
                    for (int i = 0; i < uitjes.length(); i++) {
                        JSONObject c = uitjes.getJSONObject(i);

                        // Storing each json item in variable
                        String categorie = c.getString(TAG_CATEGORIE);
                        String beschrijving = c.getString(TAG_BESCHRIJVING);
                        String stad = c.getString(TAG_STAD);
                        String straat = c.getString(TAG_STRAAT);
                        String postcode = c.getString(TAG_POSTCODE);
                        String email = c.getString(TAG_EMAIL);
                        String weertype = c.getString(TAG_WEERTYPE);


                        // creating new HashMap
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

                    }
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
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */

                    //code als die klaar is
                }
            });

        }

    }
}