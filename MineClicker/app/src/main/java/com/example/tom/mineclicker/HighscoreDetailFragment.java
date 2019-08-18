package com.example.tom.mineclicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class HighscoreDetailFragment extends Fragment implements DatabaseHelper.Callback {
    View view;
    TextView rankTextView;
    TextView usernameTextView;
    TextView clicksTextView;
    TextView floorTextView;
    TextView countryTextView;
    UserModel user = new UserModel();
    int posision = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.highscore_detail, container, false);


         rankTextView = view.findViewById(R.id.rank);
         usernameTextView = view.findViewById(R.id.usernameLabel);
         clicksTextView = view.findViewById(R.id.clicks);
         floorTextView = view.findViewById(R.id.floor);
         countryTextView = view.findViewById(R.id.country);


     return view;
    }
    protected void displayUser (UserModel user, int possion){
        this.user = user;
        this.posision = possion;

        DatabaseGet(user.getUsername());

    }
    public void DatabaseGet(String username) {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.GET, jsonObject, this);
    }


    @Override
    public void onSucces(JSONObject response, String URL) {


        rankTextView.setText((posision+1) + "");
        usernameTextView.setText(user.getUsername() + "");
        clicksTextView.setText(user.getClickCount() + "");
        floorTextView.setText(user.getFloor() + "");
        countryTextView.setText(user.getCountry() + "");
    }

    @Override
    public void onError() {

    }
}
