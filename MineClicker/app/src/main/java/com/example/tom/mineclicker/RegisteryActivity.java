package com.example.tom.mineclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisteryActivity extends AppCompatActivity implements DatabaseHelper.Callback {

    Button navigatieNaarLogin;
    Button bevestigRegisratieEnNavigeerNaarMain;
    UserModel user = new UserModel();
    String password;
    String rePassword;
    String username;
    String locale;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery);





        user.setUsername("null");

        //navigatie buttons ophalen
        navigatieNaarLogin = (Button) findViewById(R.id.BackButton);
        bevestigRegisratieEnNavigeerNaarMain = (Button) findViewById(R.id.register_Button);

        //navigatie
        navigatieNaarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisteryActivity.this, LoginActivity.class));
            }
        });
        bevestigRegisratieEnNavigeerNaarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = ((EditText) findViewById(R.id.Password)).getText().toString();
                rePassword = ((EditText) findViewById(R.id.RepeatPassword)).getText().toString();
                username = ((EditText) findViewById(R.id.UserName)).getText().toString();
                locale = getResources().getConfiguration().locale.getCountry();
                SharedPreferences.Editor editor = getSharedPreferences("ACCOUNT", MODE_PRIVATE).edit();

                if (password.equals(rePassword)) {
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    DatabaseGet();



                } else {
                    ((EditText) findViewById(R.id.errortextregistery)).setText("passwoorden komen niet overeen!");
                    findViewById(R.id.errortextregistery).setVisibility(View.VISIBLE);
                }
            }


        });


    }


    @Override
    public void onSucces(JSONObject response, String URL) {

        if (URL.contains("getuser")) {
            try {
                user.setUsername(response.get("userName").toString());
                if (user.getUsername() != "null") {
                    ((EditText) findViewById(R.id.errortextregistery)).setText("Deze user bestaat al!");
                    findViewById(R.id.errortextregistery).setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                System.out.print(e.getMessage());
            }

        } else {
            try {
                user.setUsername(response.get("userName").toString());
                user.setClickDamage((int)response.get("clickDamage"));
                user.setFloor((int)response.get("floor"));
                user.setGold((int)response.get("gold"));
                user.setCountry(response.get("country").toString());
                user.setDps((int)response.get("dps"));
                user.setClickCount((int)response.get("clickCount"));
            } catch (JSONException e) {
                System.out.print(e.getMessage());
            }
            startActivity(new Intent(RegisteryActivity.this, MainActivity.class));
        }

    }

    @Override
    public void onError() {
        DatabasePost();
    }

    public void DatabaseGet() {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(this, getURL, StringRequest.Method.GET, jsonObject, this);

    }

    public void DatabasePost() {
        /**
         *    "id": 2,
         *     "userName": "tom",
         *     "password": "test",
         *     "floor": 1,
         *     "gold": 0,
         *     "country": "België",
         *     "dps": 0,
         *     "clickDamage": 1,
         *     "clickCount": 0
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", username);
            jsonObject.put("password", password);
            jsonObject.put("country", locale);
            jsonObject.put("clickDamage", 10);

        } catch (JSONException e) {

        }

        String getURL = "register";
        DatabaseHelper.callToDatabase(this, getURL, StringRequest.Method.POST, jsonObject, this);

    }
}
