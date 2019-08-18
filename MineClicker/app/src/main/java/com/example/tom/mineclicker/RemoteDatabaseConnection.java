package com.example.tom.mineclicker;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDatabaseConnection extends AsyncTask<Void,Void,String> {
    String data;
    @Override
    protected String doInBackground(Void... voids) {
    try{
        URL url = new URL("http://mineclicker.local/getallusers");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while(line != null){
            line = reader.readLine();
        data = data + line;
        }

    }catch (MalformedURLException e){
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


        return data;
    }
}
