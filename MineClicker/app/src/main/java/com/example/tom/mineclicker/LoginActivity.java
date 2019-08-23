package com.example.tom.mineclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity implements DatabaseHelper.Callback {


    Button navigatieToMain;
    Button navigatieToRegister;
    Button loginbutton;
    UserModel user = new UserModel();
    String username;
    //maak een methode die chekt of de user al bestaad zo ja komt hij van de login pagina zo nee komt hij van de register pagina (plaats on resum in on create)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //navigatie buttons ophalen
        navigatieToMain = (Button) findViewById(R.id.LoginButton);
        navigatieToRegister = (Button) findViewById(R.id.RegisterButton);
        loginbutton = (Button) findViewById(R.id.LoginButton);

        //navigatie
        navigatieToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        navigatieToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisteryActivity.class));
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = ((EditText) findViewById(R.id.loginUsernameText)).getText().toString();
                DatabaseGet();



            }


        });
    }

    public void DatabaseGet() {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(this, getURL, StringRequest.Method.GET, jsonObject, this);

    }

    @Override
    public void onSucces(JSONObject response, String URL) {
        try {
            user.setUsername(response.get("userName").toString());
            user.setClickDamage((int) response.get("clickDamage"));
            user.setPassword((String)response.get("password"));
            user.setFloor((int) response.get("floor"));
            user.setGold((int) response.get("gold"));
            user.setCountry(response.get("country").toString());
            user.setDps((int) response.get("dps"));
            user.setClickCount((int) response.get("clickCount"));
        }catch (JSONException e){

        }

                String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();
                if (password.equals(user.getPassword())) {
                    //password correct, stuur door naar main
                    SharedPreferences.Editor editor = getSharedPreferences("ACCOUNT", MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    ((EditText) findViewById(R.id.errortext)).setText("De username of password is incorrect!");
                }


    }

    @Override
    public void onError() {
        ((EditText) findViewById(R.id.errortext)).setText("De username kan niet gevonden worden, probeer nog eens");

    }


}
