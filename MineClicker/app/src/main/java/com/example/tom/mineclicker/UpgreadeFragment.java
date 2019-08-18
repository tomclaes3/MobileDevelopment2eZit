package com.example.tom.mineclicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UpgreadeFragment extends Fragment implements ReloadTextFields, DatabaseHelper.Callback {
    Button mainButton;
    Button navigateToConacts;
    Button navigateToHighScores;
    Button buyButton;
    EditText currentClickDamage;
    EditText nextClickDamage;
    EditText gold;
    EditText price;
    UserModel user = new UserModel();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgreades, container, false);


        mainButton = (Button) view.findViewById(R.id.main);
        gold = (EditText) view.findViewById(R.id.coins);
        price = (EditText) view.findViewById(R.id.price);


        //navigatie buttons ophalen
        navigateToConacts = (Button) view.findViewById(R.id.contacts);
        navigateToHighScores = (Button) view.findViewById(R.id.highscores);
        buyButton = (Button) view.findViewById(R.id.upgreadeButton);
        currentClickDamage = view.findViewById(R.id.clickDamage);
        nextClickDamage = view.findViewById(R.id.clickDamageAfterUpgreade);
        //load data
        loadPrefs();


        //upgreade
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > (user.getClickDamage() * 2)) {
                    user.setGold(user.getGold() - (user.getClickDamage() * 2));
                    double clickdamage = user.getClickDamage() * 1.5;
                    user.setClickDamage((int) clickdamage);
                    gold.setText(String.valueOf(user.getGold()));
                    price.setText((user.getClickDamage() * 2) + "");
                    currentClickDamage.setText("Current click damage: " + user.getClickDamage());
                    nextClickDamage.setText("Next click Damage: " + (user.getClickDamage() * 1.50));
                    DatabasePut(user);
                }
            }
        });


        return view;
    }


    private void loadPrefs() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        String name = prefs.getString("username", "No username defined");//"No name defined" is the default value.
        DatabaseGet(name);

    }

    private void loadTextfields() {
        gold.setText(String.valueOf(user.getGold()));
        price.setText(String.valueOf(user.getClickDamage() * 2));
        int clickdamage = user.getClickDamage();
        String currentDamage = "Current click damage: " + clickdamage;
        System.out.println("LOGGING: " + user.getClickDamage() + "-" + currentDamage);
        currentClickDamage.setText(currentDamage);
        nextClickDamage.setText("Next click Damage: " + String.valueOf(user.getClickDamage() * 1.50));
    }

    public void loadData() {
        loadPrefs();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

    }

    @Override
    public void onSucces(JSONObject response, String URL) {
        try {
            user.setUsername(response.get("userName").toString());
            user.setClickDamage((int) response.get("clickDamage"));
            user.setPassword((String) response.get("password"));
            user.setFloor((int) response.get("floor"));
            user.setGold((int) response.get("gold"));
            user.setCountry(response.get("country").toString());
            user.setDps((int) response.get("dps"));
            user.setClickCount((int) response.get("clickCount"));
        } catch (JSONException e) {
        }
        //text fields invullen
        loadTextfields();

    }

    @Override
    public void onError() {

    }

    public void DatabasePut(UserModel user) {
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
            jsonObject.put("gold", user.getGold());
            jsonObject.put("clickDamage", user.getClickDamage());

        } catch (JSONException e) {

        }

        String getURL = "click/" + user.getUsername() + "/upgrade";
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.PUT, jsonObject, this);

    }

    public void DatabaseGet(String username) {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.GET, jsonObject, this);

    }
}
