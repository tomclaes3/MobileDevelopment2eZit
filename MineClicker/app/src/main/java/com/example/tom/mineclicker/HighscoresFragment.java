package com.example.tom.mineclicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.tom.mineclicker.AdventureFragment.TAG;

public class HighscoresFragment extends Fragment implements RycilcerViewAdapter.OnItemClickListener, DatabaseHelper.Callback {

    private ArrayList<String> ranks;
    private ArrayList<String> usernames;
    private View view;
    SendUser sendUser;
    UserModel user = new UserModel();
    int possision;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_highscores, container, false);
        DatabaseGetAll();

        return view;

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RycilcerViewAdapter adapter = new RycilcerViewAdapter(ranks, usernames, this.getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter.setOnClickLisener(HighscoresFragment.this);

    }

    @Override
    public void onItemClick(int possision, String username) {
        ((MainActivity) getActivity()).setViewPagerContent(4);
        DatabaseGet(username);
        this.possision = possision;
    }

    @Override
    public void onSucces(JSONObject response, String URL) {
        if(URL.contains("getallusers")){
            try {
                ranks = new ArrayList<>();
                usernames = new ArrayList<>();
                JSONArray array = response.getJSONArray("users");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = (JSONObject) array.get(i);

                    ranks.add((i + 1) + "");
                    usernames.add(object.get("userName").toString());


                }
            } catch (JSONException e) {

            }

            initRecyclerView();
        }else{
            try {
                user.setUsername(response.get("userName").toString());
                user.setClickDamage((int) response.get("clickDamage"));
                user.setPassword((String) response.get("password"));
                user.setFloor((int) response.get("floor"));
                user.setGold((int) response.get("gold"));
                user.setCountry(response.get("country").toString());
                user.setDps((int) response.get("dps"));
                user.setClickCount((int) response.get("clickCount"));
                sendUser.sendData(user, possision);
            }catch (JSONException e){

            }
        }

    }

    @Override
    public void onError() {

    }

    interface SendUser {
        void sendData(UserModel user, int possision);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            sendUser = (SendUser) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving User data, please try again.");
        }
    }

    public void DatabaseGetAll() {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getallusers";
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.GET, jsonObject, this);

    }

    public void DatabaseGet(String username) {
        JSONObject jsonObject = new JSONObject();
        String getURL = "getuser/" + username;
        DatabaseHelper.callToDatabase(getContext(), getURL, StringRequest.Method.GET, jsonObject, this);

    }
}

