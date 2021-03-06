package com.example.jason.recordproject;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {

    private ListView lstViewRecords;
    ArrayAdapter adapter;
    TextView txtAllRecords;
    Record record;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstViewRecords = findViewById(R.id.listViewRecords);
        txtAllRecords = findViewById(R.id.txtAllRecords);



        String url = "https://apirecord.azurewebsites.net/records";
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                // Call backs
                new Response.Listener<String>() {
                    @Override
                    public void onResponse( String response ) {
                        // Do something with the returned data
                        Log.d( "INTERNET", response );
                        records = gson.fromJson( response, Record[].class );
                        adapter = new ArrayAdapter<Record>( getApplicationContext(), R.layout.activity_listview, records );
                        lstViewRecords.setAdapter( adapter );

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        // Do something with the error
                        Log.d( "INTERNET", error.toString() );

                        toastIt( "Internet Failure: " + error.toString() );
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String credentials = username + ":" + password;
                Log.d( "AUTH", "Login Info: " + credentials );
                String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
                headers.put( "Authorization", auth );
                return headers;
            }

        };

        requestQueue.add( request );

        lstViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("recordID", records[position].getId());

                startActivity(intent);

            }


//
        });



    }

    public void addRecordActivityOnClick(View v) {
        intent = new Intent(this, AddActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
