package com.example.tom.mineclicker;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighscoreActivity extends FragmentActivity {

    Button mainButton;

    Button navigateToConacts;
    Button navigateToUpgreade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_highscore);

        /*
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        Locale loc = new Locale("",countryCodeValue);
        System.out.println("LOGGING: " + loc.getDisplayCountry() );
        */

        mainButton = (Button) findViewById(R.id.main);


        //navigatie buttons ophalen
        navigateToConacts = (Button) findViewById(R.id.contacts);
        navigateToUpgreade = (Button) findViewById(R.id.upgrades);


        //navigatie
        navigateToConacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     startActivity(new Intent(HighscoreActivity.this, minersActivity.class));
            }
        });
        navigateToUpgreade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(HighscoreActivity.this, upgreadesActivity.class));
            }
        });



        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(HighscoreActivity.this, MainActivity.class));
            }
        });

    }


}
