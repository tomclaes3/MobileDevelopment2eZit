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

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MinerFragment extends Fragment implements ReloadTextFields, DatabaseHelper.Callback {
    Button mainButton;
    Button navigateToHighScores;
    Button navigateToUpgreade;
    Button buy1;
    Button buy2;
    Button buy3;
    Button buy4;
    Button buy5;
    Button buy6;
    EditText price1;
    EditText price2;
    EditText price3;
    EditText price4;
    EditText price5;
    EditText price6;
    EditText gold;
    View view;


    UserModel user = new UserModel();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_miners, container, false);


        buy1 = (Button) view.findViewById(R.id.minerBuy1);
        buy2 = (Button) view.findViewById(R.id.minerBuy2);
        buy3 = (Button) view.findViewById(R.id.minerBuy3);
        buy4 = (Button) view.findViewById(R.id.minerBuy4);
        buy5 = (Button) view.findViewById(R.id.minerBuy5);
        buy6 = (Button) view.findViewById(R.id.minerBuy6);
        price1 = (EditText) view.findViewById(R.id.minerPrice1);
        price2 = (EditText) view.findViewById(R.id.minerPrice2);
        price3 = (EditText) view.findViewById(R.id.minerPrice3);
        price4 = (EditText) view.findViewById(R.id.minerPrice4);
        price5 = (EditText) view.findViewById(R.id.minerPrice5);
        price6 = (EditText) view.findViewById(R.id.minerPrice6);
        gold = (EditText) view.findViewById(R.id.coins);
        loadPrefs();
        //invullen text fields


        buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > 50) {
                    user.setGold(user.getGold() - 50);
                    user.setDps(10);
                    checkOrBoughtUpgraede();
                    gold.setText(String.valueOf(user.getGold()));
                    DatabasePut(user);
                }
            }
        });
        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > 150) {
                    user.setGold(user.getGold() - 150);
                    user.setDps(20);
                    checkOrBoughtUpgraede();
                    gold.setText(String.valueOf(user.getGold()));
                    DatabasePut(user);
                }
            }
        });
        buy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > 300) {
                    user.setGold(user.getGold() - 300);
                    user.setDps(40);
                    checkOrBoughtUpgraede();
                    gold.setText(String.valueOf(user.getGold()));
                    DatabasePut(user);
                }
            }
        });
        buy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > 500) {
                    user.setGold(user.getGold() - 500);
                    user.setDps(80);
                    checkOrBoughtUpgraede();
                    gold.setText(String.valueOf(user.getGold()));
                    DatabasePut(user);
                }
            }
        });
        buy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > 1000) {
                    user.setGold(user.getGold() - 1000);
                    user.setDps(160);
                    checkOrBoughtUpgraede();
                    gold.setText(String.valueOf(user.getGold()));
                    DatabasePut(user);
                }
            }
        });
        buy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGold() > 50) {
                    user.setGold(user.getGold() - 2000);
                    user.setDps(320);
                    checkOrBoughtUpgraede();
                    gold.setText(String.valueOf(user.getGold()));
                    DatabasePut(user);
                }
            }
        });


        //navigatie buttons ophalen
        navigateToHighScores = (Button) view.findViewById(R.id.highscores);
        navigateToUpgreade = (Button) view.findViewById(R.id.upgrades);
        mainButton = (Button) view.findViewById(R.id.main);

        //navigatie

        return view;
    }

    public void checkOrBoughtUpgraede() {
        switch (user.getDps()) {
            case 10:
                buy1.setEnabled(false);
                price1.setText("bought");
                break;
            case 20:
                buy1.setEnabled(false);
                buy2.setEnabled(false);
                price1.setText("bought");
                price2.setText("bought");
                break;
            case 40:
                buy1.setEnabled(false);
                buy2.setEnabled(false);
                buy3.setEnabled(false);
                price1.setText("bought");
                price2.setText("bought");
                price3.setText("bought");
                break;
            case 80:
                buy1.setEnabled(false);
                buy3.setEnabled(false);
                buy4.setEnabled(false);
                buy2.setEnabled(false);
                price1.setText("bought");
                price2.setText("bought");
                price3.setText("bought");
                price4.setText("bought");
                break;
            case 160:
                buy1.setEnabled(false);
                buy2.setEnabled(false);
                buy3.setEnabled(false);
                buy4.setEnabled(false);
                buy5.setEnabled(false);
                price1.setText("bought");
                price3.setText("bought");
                price4.setText("bought");
                price5.setText("bought");
                price2.setText("bought");

                break;
            case 320:
                buy1.setEnabled(false);
                buy2.setEnabled(false);
                buy3.setEnabled(false);
                buy4.setEnabled(false);
                buy5.setEnabled(false);
                buy6.setEnabled(false);
                price1.setText("bought");
                price2.setText("bought");
                price3.setText("bought");
                price4.setText("bought");
                price5.setText("bought");
                price6.setText("bought");
                break;
        }
    }


    private void loadPrefs() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        String name = prefs.getString("username", "No username defined");//"No name defined" is the default value.
        DatabaseGet(name);

    }

    private void loadTextfields() {
        gold.setText(String.valueOf(user.getGold()));
    }

    public void loadData() {
        loadPrefs();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

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
            jsonObject.put("dps", user.getDps());

        } catch (JSONException e) {

        }

        String getURL = "miner/" + user.getUsername() + "/upgrade";
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.PUT, jsonObject, this);

    }

    public void DatabaseGet(String username) {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.GET, jsonObject, this);
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
        //controleer welke upgrades gekocht zijn
        checkOrBoughtUpgraede();
        loadTextfields();

    }

    @Override
    public void onError() {

    }
}
