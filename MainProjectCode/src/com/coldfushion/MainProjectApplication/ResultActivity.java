package com.coldfushion.MainProjectApplication;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


/**
 * Created by Kraaijeveld on 21-5-2015.
 * Deze Activity geeft het resultaat van onze PHP-requests weer.
 * Voor nu, krijgen we alleen alle namen van alle uitjes in onze DB terug.
 */

public class ResultActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> uitjesList;

    // url waar het PHPscript dat we willen zich bevind
    private static String url_all_products = "www.coldfusiondata.site90.net/db_get_all.php";

    // We maken hier vars aan voor de JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_UITJES = "Uitjes";
    private static final String TAG_PID = "uitjesID";
    private static final String TAG_NAME = "Naam";

    // Hier maken we de uitjes JSONArray
    JSONArray uitjes = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultlist);

        //Bij het starten van deze activity maken we een KVP hashmap om de uitjes in te bewaren,
        //En starten we de LoadAllUitjes thread.
        uitjesList = new ArrayList<HashMap<String, String>>();
        new LoadAllUitjes().execute();
        // Get listview
        ListView lv = getListView();
    }

    /**
     * Deze achtergrondthread doet het daadwerkelijke werk:
     * Het callen van ons PHP script en het binnenhalen van de JSON
     * Response dat het PHP script ons teruggeeft.
     * */
    class LoadAllUitjes extends AsyncTask<String, String, String> {

        /**
         * Voordat we de taak starten laten we netjes een "zandloper" zien
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ResultActivity.this);
            pDialog.setMessage("Uitjes worden opgehaald... even geduld!");
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


            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("Uitjes: ", json.toString());

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
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        uitjesList.add(map);
                    }
                } else {
                    // no products found
                    Toast.makeText(getApplicationContext(), "Helaas, er zijn geen uitjes gevonden!", Toast.LENGTH_LONG).show();
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
                    ListAdapter adapter = new SimpleAdapter(
                            ResultActivity.this, uitjesList,
                            R.layout.resultlistitem, new String[] { TAG_PID,
                            TAG_NAME},
                            new int[] { R.id.uitjesID, R.id.Naam });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}