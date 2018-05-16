package com.example.jason.recordproject;

import android.app.DownloadManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

public class EditActivity extends BaseActivity {


    EditText edtName;
    EditText edtDesc;
    EditText edtPrice;
    EditText edtRating;
    EditText edtImage;
    Record record;
    int recordID;


    DecimalFormat precision = new DecimalFormat("0.00");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtRating = findViewById(R.id.edtRating);
        edtImage = findViewById(R.id.edtImage);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); //2018-05-07T21:12:27.000Z
        gson = gsonBuilder.create();

        if( getIntent().getExtras() != null ) {
          recordID = getIntent().getExtras().getInt( "recordID" );
       }
//
//
//        edtName.setText(records[recordID].getName());
//        edtDesc.setText(records[recordID].getDescription());
//        edtPrice.setText(records[recordID].getPrice().toString());
//        edtRating.setText(records[recordID].getRating().toString());

        toastIt("recordid is " + recordID);
        String url = "https://apirecord.azurewebsites.net/records/" + recordID;
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                // Call backs
                new Response.Listener<String>() {
                    @Override
                    public void onResponse( String response ) {
                        // Do something with the returned data
                        Log.d( "INTERNET", response );
                        record = gson.fromJson( response, Record.class );

                        // Take data to display on the edit activity

                        edtName.setText(record.getName());
                        edtDesc.setText(record.getDescription());
                        edtPrice.setText(Double.toString(record.getPrice()));
                        edtRating.setText(Integer.toString(record.getRating()));
                        edtImage.setText(record.getImage());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        // Do something with the error
                        Log.d( "INTERNET", error.toString() );
                        toastIt( "Internet Failure: " + error.toString() );
                    }
                } );

        requestQueue.add( request );

    }

    public void editButtonOnClick(View v) {

        String url = "https://apirecord.azurewebsites.net/records/" + recordID;

        final String recordDescription = edtDesc.getText().toString();
        final String recordName = edtName.getText().toString();
        final int recordRating = Integer.parseInt(edtRating.getText().toString());
        final double recordPrice = Double.parseDouble(edtPrice.getText().toString());
        final String recordImage = edtImage.getText().toString();

        // HASHMAP  = HASH  Key=>Value
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", recordName);
        params.put("description", recordDescription);
        params.put("rating", String.valueOf(recordRating));
        params.put("price", String.valueOf(recordPrice));
        params.put("image", recordImage);

        // POST that JSON object to the server using VOLLEY
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, url, new JSONObject( params ),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse( JSONObject response ) {
                        Log.d( "RECORD", response.toString() );
                        intent = new Intent(getApplicationContext(), ShowActivity.class);
                        intent.putExtra("recordID", record.getId());
                        toastIt("Record ID IS " + record.getId());
                        EditActivity.this.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        Log.d( "RECORD", error.toString() );
                    }
                }
        );

        requestQueue.add( request );




  }


}
