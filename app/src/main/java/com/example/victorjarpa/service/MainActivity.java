package com.example.victorjarpa.service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MyService.class));
        setContentView(R.layout.activity_main);

        final TextView mTextView = (TextView) findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.genesys.cl:329/IOS/CI/index.php/gps/set";

        StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jObject = null;
                    try {
                        jObject = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mTextView.setText("Response is: "+ jObject);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mTextView.setText("That didn't work!");
                }
            }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lat", "-36.8175136");
                params.put("lon", "-73.0606134");
                params.put("provider", "gps");
                params.put("acc", "2288");
                params.put("et", "+23m56s736ms");
                return params;
            }
        };

        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
    }
}
