package com.coldfushion.MainProjectApplication.Helpers;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;

import com.coldfushion.MainProjectApplication.Activities.MyActivity;

/**
 * Created by Kraaijeveld on 29-5-2015.
 */
public class getCurrentWeather extends Activity
{
    //hier vullen we straks de stad waar de gebruiker zich in bevind in:
    String weatherURLplace = "";
    JSONParser JSONParser = new JSONParser();
    ListAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Execute DownloadJSON AsyncTask
        new DownloadJSON().execute();
    }

    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getCurrentWeather.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Weer ophalen");
            // Set progressdialog message
            mProgressDialog.setMessage("Even geduld...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            // Create the array
            arraylist = new ArrayList<HashMap<String, String>>();

            //Deze url voert een YQL query uit op de yahoo weather api, met als locatie de huidige locatie v.d. gebruiker.
            String url = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition.text%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + //insert citymethod here  +  "%2C%20tx%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

            try
            {
                // We gaan door alle JSONObjects heen tot we bij de array Item(s) aankomen
                JSONObject json_data = JSONParser.getJSONfromURL(url);
                JSONObject json_query = json_data.getJSONObject("query");
                JSONObject json_results = json_query.getJSONObject("results");
                JSONObject json_json_result = json_results.getJSONObject("channel");
                JSONArray json_result = json_json_result.getJSONArray("item");

                //Door die array loopen we heen totdat we bij ons element "text" aankomen
                //De inhoud van dat element stoppen we in onze map
                for (int i = 0; i < json_result.length(); i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject c = json_result.getJSONObject(i);
                    JSONObject vo = c.getJSONObject("text");
                    map.put("text", vo.optString("text"));
                    arraylist.add(map);
                }

            }
            catch (JSONException e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args)
        {
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    //Deze methode filtert de JSONoutput op onze 3 keywords: Sunny, cloudy en rainy.
    public String filterWeatherText()
    {
        //We halen eerst de arraylist met daarin de map met daarin het huidige weer op.
        String weathertext =  this.arraylist.get(0).get("text");
        //Daarna koken we het huidige weer terug tot 1 v.d. 3 keywords, die we later gebruiken.
        if(weathertext.contains(".*Sunny.*"))
        {
            weathertext = "sunny";
        }
        else if(weathertext.contains(".*Cloud.*"))
        {
            weathertext = "Cloudy";
        }
        else if(weathertext.contains(".*Rain.*"))
        {
            weathertext = "Rainy";
        }
        else
        {
            weathertext = "Cloudy";
        }
        return weathertext;
    }
}

