package com.example.tom.mineclicker;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AdventureFragment extends Fragment implements ReloadTextFields,DatabaseHelper.Callback{
    public static String TAG = "AdventureFragment";
    private ImageView rockImage;
    private ProgressBar hpBar;
    private EditText hpBarText;
    private EditText coins;
    private EditText previousFloor;
    private EditText currentFloor;
    private EditText nextFloor;
    private CountDownTimer countDownTimer;
    private  UserDatabaseHelper userDatabaseHelper;
    private long timeLeftInMiliseconds = 60000;
    private NotificationManagerCompat manager;


    UserModel user = new UserModel();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adventure, container , false);

        manager = NotificationManagerCompat.from(getActivity());



        //start timer
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StartTimer();
            }
        });



        hpBar = (ProgressBar) view.findViewById(R.id.hpBar);
        rockImage = (ImageView) view.findViewById(R.id.rock);
        coins = (EditText) view.findViewById(R.id.coins);
        previousFloor = (EditText) view.findViewById(R.id.previousfloor);
        currentFloor = (EditText) view.findViewById(R.id.currentFloor);
        nextFloor = (EditText) view.findViewById(R.id.nextfloor);
        hpBarText = (EditText) view.findViewById(R.id.hpBarText);
        hpBarText.setText(hpBar.getProgress() + "/" + hpBar.getMax());
        //navigatie buttons ophalen
        final Context mContext = getActivity();


        System.out.println("melding: test");

        SharedPreferences prefs = this.getActivity().getSharedPreferences("ACCOUNT",Context.MODE_PRIVATE);


        String username = prefs.getString("username", "No username defined");//"No name defined" is the default value.
        DatabaseGet(username);
        //set floor


        rockImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hpBar.setProgress(hpBar.getProgress() - user.getClickDamage());
                rockImage.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shakeanimation));
                hpBarText.setText(hpBar.getProgress() + "/" + hpBar.getMax());
                user.setClickCount(user.getClickCount() + 1);
                if (hpBar.getProgress() <= 0) {
                    rockDefeated();
                    final String  CHANNEL_1_iD = "RockDefeated";
                    //give notification
                    Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_1_iD)
                            .setSmallIcon(R.drawable.ic_happy)
                            .setContentTitle("Mined!")
                            .setContentText("You mineed a rock!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();
                    manager.notify(1,notification);
                } else {

                }
            }
        });
        //navigatie to fragment


        return view;
    }
    public void StartTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliseconds = millisUntilFinished;
                hpBar.setProgress(hpBar.getProgress() - user.getDps());
                hpBarText.setText(hpBar.getProgress() + "/" + hpBar.getMax());
                if (hpBar.getProgress() <= 0) {
                    rockDefeated();
                } else {
                    //todo critically damage on the stone in a flashy epic way
                }
            }

            @Override
            public void onFinish() {
                StartTimer();
            }
        }.start();
    }
    public void rockDefeated(){

        //coins gained
        user.setGold(user.getGold() + ((int) (user.getFloor() * 1.735)) );
        coins.setText(user.getGold() + "");
        //move to next floor
        user.setFloor( user.getFloor()+1);

        previousFloor.setText(String.valueOf(user.getFloor() - 1));
        currentFloor.setText(String.valueOf(user.getFloor()));
        nextFloor.setText(String.valueOf(user.getFloor() + 1));
        //calculate hp bar
        hpBar.setMax( (int)((user.getFloor() * 1.321)* 100));

        //set hpbar to full
        hpBar.setProgress(hpBar.getMax());
        DatabasePut(user);
    }

    @Override
    public void onResume() {
        super.onResume();
        coins.setText(user.getGold() + "");
        previousFloor.setText(String.valueOf(user.getFloor() - 1));
        currentFloor.setText(String.valueOf(user.getFloor()));
        nextFloor.setText(String.valueOf(user.getFloor() + 1));
    }
    public void DatabaseGet(String username) {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.GET, jsonObject, this);

    }
    public void DatabasePut(UserModel user){
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
                jsonObject.put("floor", user.getFloor());
                jsonObject.put("clickCount", user.getClickCount());



            } catch (JSONException e) {

            }

            String getURL = "rock/" + user.getUsername() + "/defeated";
            DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.PUT, jsonObject, this);

        }



    @Override
    public void onError() {

    }
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
            loadTextFields();
        }catch (JSONException e){

        }





    }
    public void loadTextFields(){
        previousFloor.setText(String.valueOf(user.getFloor() - 1));
        currentFloor.setText(String.valueOf(user.getFloor()));
        nextFloor.setText(String.valueOf(user.getFloor() + 1));
        //calculate hp bar
        hpBar.setMax( (int)((user.getFloor() * 1.321)* 100));

        //set hpbar to full
        hpBar.setProgress(hpBar.getMax());
        //setgold
        coins.setText(user.getGold() + "");
    }
    @Override
    public void loadData() {
        loadPrefs();
    }
    private void loadPrefs() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        String name = prefs.getString("username", "No username defined");//"No name defined" is the default value.
        DatabaseGet(name);

    }

}
