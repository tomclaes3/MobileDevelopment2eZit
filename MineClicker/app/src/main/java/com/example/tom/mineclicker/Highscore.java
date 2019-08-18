package com.example.tom.mineclicker;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Highscore extends AsyncTask<Void, Void, String> {
    ArrayAdapter<String> adapter;


    protected void onPreExecution(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        ArrayList<HighscoreModel> highscoresModel= null;

        String highscoreJsonStr = null;

        try {
            URL url = new URL("http://5bc77c0dcc83760013c1cd15.mockapi.io/highscores");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int lengthOfFile = urlConnection.getContentLength();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            highscoreJsonStr = buffer.toString();
            Log.e("Json1", highscoreJsonStr);

            JSONArray jsonArray = new JSONArray(highscoreJsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                /*HighscoreModel highscoreModel = new HighscoreModel();
                highscoreModel.setRank(Integer.parseInt(jsonObject.getString("rank")));
                highscoreModel.setUsername(jsonObject.getString("name"));
                highscoreModel.setFloor(Integer.parseInt(jsonObject.getString("floor")));
                highscoreModel.setClicks(Integer.parseInt(jsonObject.getString("clicks")));
                highscoreModel.setCountry(jsonObject.getString("country"));
                highscoresModel.add(highscoreModel);*/
            }
            return highscoreJsonStr;
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return highscoreJsonStr;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        adapter.notifyDataSetChanged();
    }
}
