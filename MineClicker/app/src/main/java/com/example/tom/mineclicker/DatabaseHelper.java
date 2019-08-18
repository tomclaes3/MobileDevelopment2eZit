package com.example.tom.mineclicker;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class DatabaseHelper implements Serializable {

    public static void callToDatabase(Context contect, final String route, int method, final JSONObject jsonBody, final Callback callback) {
        try {
            RequestQueue queue = Volley.newRequestQueue(contect);
            StringRequest request = new StringRequest(
                    method,
                    "http://swag-servers.ddns.net/" + route,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject object;
                           try {
                               object = new JSONObject(response);
                           }catch (JSONException e){
                               object = new JSONObject();
                           }
                            callback.onSucces(object, route);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse.statusCode == 500) {
                                callback.onError();
                            }
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("X-Requested-With", "XMLHttpRequest");

                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return jsonBody.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

            };

            queue.add(request);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }
    public interface Callback {
        void onSucces(JSONObject response, String URL);
        void onError();
    }
}

