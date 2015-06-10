package com.coldfushion.MainProjectApplication.Helpers;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ceesjan on 10-6-2015.
 */
public class getWebpageContent {
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //textView.setText(Html.fromHtml(result));
            Log.d("resultaat: ",result);
            String Result = result.replace("  ", " ");
            int x = Result.indexOf("weekday_text");
            if (x > 0) {
                String partfromweekday = Result.substring(x);
                String newx = partfromweekday.replace("      ", " ");
                int asd = newx.indexOf("]");
                String asdasd = newx.substring(0, asd);
                Log.d("partfrom later", asdasd);
                String woow = asdasd.replace("\", \"", "\n");
                int asdasdasdasd = asdasd.indexOf(": [ \"");
                String qwertyu = woow.substring(asdasdasdasd + 4);
                String dfghjk = qwertyu.replace("\"", "\n");
                Log.d("resutlatat", dfghjk);

            }
            else {
                Log.d("Openingstijden", "Geen tijden bekend");
            }
        }
    }

    public void readWebpage(String url) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] { url });

    }

}
