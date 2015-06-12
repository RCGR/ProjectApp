package com.example.android.phonecall.app;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //call();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        //Put up the Yes/No message box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Doorgaan met bellen?")
                .setMessage("Weet u het zeker? Er kunnen kosten van uw provider in rekening worden gebracht")
                .setIcon(android.R.drawable.ic_dialog_alert)
                //If user proceeds with the call, the phone changes to the callview and is calling the number.
                .setPositiveButton("Ga door", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes button clicked, do something
                        //Toast.makeText(MainActivity.this, "Yes button pressed",
                        //        Toast.LENGTH_SHORT).show();
                        call();
                    }
                })
                //If user cancels the phone call, it does not call and a toast is showed that the call is canceled.
                .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes button clicked, do something
                        Toast.makeText(MainActivity.this, "Geannuleerd.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void call() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0641382914"));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("Dialing example", "Call failed", e);
        }
    }


}

