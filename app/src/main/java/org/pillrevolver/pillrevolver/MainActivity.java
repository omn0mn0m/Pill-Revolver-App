package org.pillrevolver.pillrevolver;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String url_get = "http://pillrevolver.org:8080/db/5bded79f9be5a93ee1ddab0d";
    String url_post = "http://pillrevolver.org:8080/db/pill";
    JSONObject schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void updateTime(View view) {
        final TextView id = (TextView) findViewById(R.id.txtId);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_post,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);

                        try {
                            schedule = new JSONObject(response);
                        } catch (Exception e) {
                            Log.e("Response", "That didn't work");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id.getText().toString());

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm:ss z");

                params.put("time", format.format(calendar.getTime()));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void updateSchedule(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_get,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO: Handle response
                        Log.i("Response", response);

                        try {
                            schedule = new JSONObject(response);
                        } catch (Exception e) {
                            Log.e("Response", "That didn't work");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle errors
                Log.e("Response", "That didn't work");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
