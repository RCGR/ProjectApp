package com.coldfushion.MainProjectApplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by ceesjan on 29-5-2015.
 */
public class RateUitjeItem extends Activity {

    TextView textView_name;
    TextView textView_beschrijving;
    TextView textView_categorie;
    TextView textView_weertype;
    TextView textView_adres;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rateuitjeitem_layout);


        textView_adres = (TextView)findViewById(R.id.Rate_adres);
        textView_beschrijving = (TextView)findViewById(R.id.Rate_Beschrijving);
        textView_categorie = (TextView)findViewById(R.id.Rate_Categorie);
        textView_name = (TextView)findViewById(R.id.Rate_Name);
        textView_weertype = (TextView)findViewById(R.id.Rate_Weertype);

        textView_name.setText("UITJE Nummer 1");
        textView_beschrijving.setText(" een dagje naar dit uitje is onvergetlijk. wie wil dit nou niet. het is echt helmaal te gek kom daarom nu snel naar ons :D " );
        textView_adres.setText("u kunt ons vinden aan: \n wegderwegen 123 \n 0112 MS \n SomewhereCity");
        textView_weertype.setText("Regen");
        textView_categorie.setText("Museum");
    }
}