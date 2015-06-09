package com.coldfushion.MainProjectApplication.Helpers;

/**
 * Created by Robert on 9-6-2015.
 */

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.coldfushion.MainProjectApplication.Activities.ResultActivity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Deze achtergrondthread doet het daadwerkelijke werk:
 * Het callen van ons PHP script en het binnenhalen van de JSON
 * Response dat het PHP script ons teruggeeft.
 * */


 public class getGooglePlacesData extends AsyncTask<String, String, String> {
    // Progress Dialog
    private ProgressDialog pDialog;

    public String id = "";

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    List<HashMap<String, String>> uitjesList = new ArrayList<HashMap<String, String>>();

    // url waar het PHPscript dat we willen zich bevind
    //ID MOET NOG HIERIN
    private static String url_all_suggestions = "http://coldfusiondata.site90.net/db_get_details_suggestion.php?id=";


    // We maken hier vars aan voor de JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_UITJES = "Uitjes";
    private static final String TAG_PLACEID = "PlaceID";

    private static final String TAG_RESULT = "result";
    private static final String TAG_OPENINGHOURS = "opening_hours";

    // Hier maken we de uitjes JSONArray
    JSONArray uitjes = null;

    /**
     * Voordat we de taak starten laten we netjes een "zandloper" zien
     */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //ADD PROGRESSDIALOG
    }

    /**
     * getting All from url
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL

        JSONObject json = null;
        try {
            json = jParser.makeHttpRequest(url_all_suggestions + id, "GET", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) {
            Log.d("jsonechek", "jsonempty");
        }
        // Check your log cat for JSON reponse
        Log.d("Uitjes: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                uitjes = json.getJSONArray(TAG_UITJES);
                Log.d("uitjes", uitjes.toString());
                // looping through All Products
                for (int i = 0; i < uitjes.length(); i++)
                {
                    JSONObject c = uitjes.getJSONObject(i);
                    Log.d("c", c.toString());
                    // Storing each json item in variable
                    String placeid = c.getString(TAG_PLACEID);
                    Log.d("placeid", placeid);
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_PLACEID, placeid);

                    map.values();

                    // adding HashList to ArrayList
                    uitjesList.add(map);
                }

                /*
                * OPENINGSTIJDENREQUEST
                */

                //Hier maken we de url voor de volgende request, die naar de Google places api voor de openingstijden
                String google_places_url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" +
                uitjesList.get(0).get(TAG_PLACEID) + "&key=AIzaSyDIISR3XY3XX2ts-ZsufAH6SiiEONQm7vE";

                JSONObject json2 = null;
                JSONArray results = null;

                Log.d("url google places", google_places_url);
                try
                {
                    json2 = jParser.makeHttpRequest(google_places_url, "GET", params);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (json2 == null)
                {
                    Log.d("jsonechek", "jsonempty");
                }
                // Check your log cat for JSON reponse
                Log.d("Openingstijden: ", json2.toString());
                try
                {
                    // Checking for SUCCESS TAG
                    String googlesucces = json2.getString("status");
                    if (googlesucces.equals("OK"))
                    {
                        // products found
                        // Getting Array of Products
                        results = json2.getJSONArray(TAG_RESULT);

                        // looping through All Products
                        for (int i = 0; i < results.length(); i++)
                        {
                            JSONObject c = results.getJSONObject(i);
                            // Storing each json item in variable
                            String placeid = c.getString(TAG_OPENINGHOURS);
                            Log.d("openintsijdden", placeid);
                        }

                    }
                    else
                    {
                        // no products found
                        Log.d("Uitjes status", "Geen uitjes");
                    }
                }
                catch(JSONException e)
                {
                    Log.d("Exception", "" + e + "");
                }
                return null;
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
     * *
     */

    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
    }

}




