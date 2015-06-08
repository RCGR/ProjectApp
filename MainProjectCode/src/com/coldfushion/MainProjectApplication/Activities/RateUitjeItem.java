package com.coldfushion.MainProjectApplication.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coldfushion.MainProjectApplication.Helpers.JSONParser;
import com.coldfushion.MainProjectApplication.R;
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
    TextView textViewUpVote;
    TextView textViewDownVote;

    private int upVotes;
    private int downVotes;
    private int totalVotes;

    String naam = "";
    String categorie = "";
    String beschrijving = "";
    String stad = "";
    String straat = "";
    String postcode = "";
    String email = "";
    String weertype = "";
    String coordinaat = "";

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
    private static final String TAG_COORDINAAT = "Coordinaat";
    private static final String TAG_UPVOTECOUNT = "upVoteCount";
    private static final String TAG_DOWNVOTECOUNT = "downVoteCount";

    public boolean hasVoted;

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
        textViewUpVote = (TextView) findViewById(R.id.Rate_upvotes);
        textViewDownVote = (TextView) findViewById(R.id.Rate_downvotes);

        uitjesList = new ArrayList<HashMap<String, String>>();
        new LoadDetailsSuggestedUitjes().execute();
    }

    //onclick methods for xml
    public void giveDownVote(View view)
    {
        new giveDownVoteThread().execute();
    }

    public void giveUpVote(View view)
    {
        new giveUpVoteThread().execute();
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

            JSONObject json = null;
            try {
                json =jParser.makeHttpRequest(final_url, "GET", params);

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
                    naam = x.getString(TAG_NAME);
                    categorie = x.getString(TAG_CATEGORIE);
                    beschrijving = x.getString(TAG_BESCHRIJVING);
                    stad = x.getString(TAG_STAD);
                    straat = x.getString(TAG_STRAAT);
                    postcode = x.getString(TAG_POSTCODE);
                    email = x.getString(TAG_EMAIL);
                    weertype = x.getString(TAG_WEERTYPE);
                    coordinaat = x.getString(TAG_COORDINAAT);
                    upVotes = x.getInt(TAG_UPVOTECOUNT);
                    downVotes = x.getInt(TAG_DOWNVOTECOUNT);



//                    //VoteCounter stuff
//                    //Call checkmethod when loading
//                    //Call addVoteMethod when buttonpress
//                    if(upVotes > 10)
//                    {
//
//                    }
//                    else if(downVotes > 5)
//                    {
//                        final String delete_url = "http://coldfusiondata.site90.net/db_remove_suggestion.php?NaamVar=" + naam + "";
//                        jParser.makeHttpRequest(delete_url, "POST", params);
//                        //remove uitje from suggestion
//                    }
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_NAME, naam);
                    map.put(TAG_CATEGORIE, categorie);
                    map.put(TAG_BESCHRIJVING, beschrijving);
                    map.put(TAG_STAD, stad);
                    map.put(TAG_STRAAT, straat);
                    map.put(TAG_POSTCODE, postcode);
                    map.put(TAG_EMAIL, email);
                    map.put(TAG_WEERTYPE, weertype);
                    map.put(TAG_UPVOTECOUNT, upVotes+"");
                    map.put(TAG_DOWNVOTECOUNT, downVotes+"");
                    map.put(TAG_COORDINAAT, coordinaat);
                    // adding HashList to ArrayList
                    uitjesList.add(map);


                    totalVotes = upVotes + downVotes;
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

        protected void onPostExecute(String file_url)
        {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

                    /*
                    * Updating parsed JSON data into textviews
                    */
                    Log.d("TEST", uitjesList.size()+"");
                    textViewBeschrijving.setText(uitjesList.get(0).get(TAG_BESCHRIJVING));
                    textViewCategorie.setText(uitjesList.get(0).get(TAG_CATEGORIE));
                    textViewWeertype.setText(uitjesList.get(0).get(TAG_WEERTYPE));
                    textViewEmail.setText(uitjesList.get(0).get(TAG_EMAIL));
                    textViewStraat.setText(uitjesList.get(0).get(TAG_STRAAT));
                    textViewName.setText(uitjesList.get(0).get(TAG_NAME));
                    textViewPostcode.setText(uitjesList.get(0).get(TAG_POSTCODE));
                    textViewStad.setText(uitjesList.get(0).get(TAG_STAD));
                    textViewUpVote.setText(uitjesList.get(0).get(TAG_UPVOTECOUNT));
                    textViewDownVote.setText(uitjesList.get(0).get(TAG_DOWNVOTECOUNT));
        }
    }


    // to do : call appropriate methods of this class when button is clicked

    //
    //UPVOTE
    //

    class giveUpVoteThread extends AsyncTask<String, String, String>
    {
        /**
         * Voordat we de taak starten laten we netjes een "zandloper" zien
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RateUitjeItem.this);
            pDialog.setMessage("Uw stem wordt verwerkt...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        /**
         * getting All from url
         */
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            upVotes++;
            totalVotes++;
            Log.d("totalvotes", totalVotes+ "");
            Log.d("upvotes", upVotes+ "");
            double x = upVotes / totalVotes * 100.0;
            Log.d("x", x + "");

            if(hasVoted == false)
            {
                if (totalVotes >= 10 && x > 75)
                {
                    String newNaam = naam.replace(" ", "+");
                    String newBeschrijving = beschrijving.replace(" ", "+");
                    String newCategorie = categorie.replace(" ", "+");
                    String newemail = email.replace(" ", "+");
                    String newStraat = straat.replace(" ", "+");
                    String newPostcode = postcode.replace(" ", "+");
                    String newStad = stad.replace(" ", "+");
                    String newWeerType = weertype;
                    String parameters_url =

                            "NaamVar=" + newNaam + "&WeerTypeVar=" + newWeerType + "&BeschrijvingVar=" + newBeschrijving +
                                    "&CategorieVar=" + newCategorie + "&EmailVar=" + newemail +
                                    "&StraatVar=" + newStraat + "&PostCodeVar=" + newPostcode +
                                    "&StadVar=" + newStad + "&CoordinaatVar=" + coordinaat;

                    final String insert_url = "http://coldfusiondata.site90.net/db_insert.php?" + parameters_url + "";
                    Log.d("String url", insert_url);
                    try
                    {
                        //ERRORS
                        jParser.simpleGetJSONfromURL(insert_url);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //Eerst zetten we de suggestie in de uitjesdatabase,
                    //Daarna wordt de suggestie uit de suggestieDB verwijderd

                    final String delete_url = "http://coldfusiondata.site90.net/db_remove_suggestion.php?id=" + id_detail + "";
                    try
                    {
                        jParser.simpleGetJSONfromURL(delete_url);
                        //jParser.makeHttpRequestNoReturn(delete_url, "POST", params);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    }
                    else
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        final String upvote_url = "http://coldfusiondata.site90.net/db_insert_upvote.php?id=" + id_detail + "";

                        jParser.simpleGetJSONfromURL(upvote_url);
                        //jParser.makeHttpRequestNoReturn(upvote_url, "POST", params);
                     }

            hasVoted = true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "U heeft al gestemd op dit uitje!", Toast.LENGTH_SHORT);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }
    }

    //
    //DOWNVOTE
    //

    class giveDownVoteThread extends AsyncTask<String, String, String> {

        /**
         * Voordat we de taak starten laten we netjes een "zandloper" zien
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RateUitjeItem.this);
            pDialog.setMessage("Uw stem wordt verwerkt...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        /**
         * getting All from url
         */

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            downVotes++;
            totalVotes++;
            Log.d("totalvotes", totalVotes+ "");
            Log.d("downvotes", downVotes+ "");
            double x = (downVotes / totalVotes) * 100.0;
            Log.d("downvotes", downVotes + "");
            Log.d("x", x + "");

            if(hasVoted == false)
            {
                if (totalVotes >= 10 && x > 50) {
                    //delete
                    final String delete_url = "http://coldfusiondata.site90.net/db_remove_suggestion.php?id=" + id_detail + "";

                    jParser.simpleGetJSONfromURL(delete_url);
                    //jParser.makeHttpRequestNoReturn(delete_url, "POST", params);

                }
                else
                {
                    final String downvote_url = "http://coldfusiondata.site90.net/db_insert_downvote.php?id=" + id_detail + "";

                    jParser.simpleGetJSONfromURL(downvote_url);
                    //jParser.makeHttpRequestNoReturn(delete_url, "POST", params);

                }
                hasVoted = true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "U heeft al gestemd op dit uitje!", Toast.LENGTH_SHORT);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }
    }
}







