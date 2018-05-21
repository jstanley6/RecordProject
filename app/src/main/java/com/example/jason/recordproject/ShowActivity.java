package com.example.jason.recordproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends BaseActivity {

    Record record;
    Record singleRecord;
    TextView txtName;
    TextView txtDesc;
    TextView txtPrice;
    TextView txtRating;
    TextView txtImage;
    TextView txtCreatedDate;
    TextView txtModifiedDate;
    DecimalFormat precision = new DecimalFormat("0.00");
    int recordID;
    int idRecord;
    ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);

        txtName = findViewById(R.id.txtName);
        txtDesc = findViewById(R.id.txtDesc);
        txtPrice = findViewById(R.id.txtPrice);
        txtRating = findViewById(R.id.txtRating);
        imageView = findViewById(R.id.imgView);
        txtCreatedDate = findViewById(R.id.txtCreatedDate);
        txtModifiedDate = findViewById(R.id.txtModifiedDate);

//        txtImage = findViewById(R.id.txtImage);
        intent = getIntent();
        recordID = intent.getIntExtra("recordID", 0);



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

                        // Take data to display on the view

                        txtName.setText(record.getName());
                        txtDesc.setText(record.getDescription());
                        txtPrice.setText("$" + precision.format(record.getPrice()));
                        txtRating.setText(Integer.toString(record.getRating()));

                       txtCreatedDate.setText(record.getCreatedAt().toString());
                       txtModifiedDate.setText(record.getUpdatedAt().toString());
                        Picasso.with(getApplicationContext()).load(record.getImage()).into(imageView);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        // Do something with the error
                        Log.d( "INTERNET", error.toString() );
                        toastIt( "Internet Failure: " + error.toString() );
                    }
                } )
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


    }

    public void switchToEdit(View v) {

        intent = new Intent(this, EditActivity.class);
        intent.putExtra("recordID", recordID);
        ShowActivity.this.startActivity(intent);
    }

    public void deleteOnClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to delete this record?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Perform something when they say yes
                        String url = "https://apirecord.azurewebsites.net/records/" + recordID;

                        // POST that JSON object to the server using VOLLEY
                        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse( String response ) {
                                        Log.d( "RECORD", response );
                                        intent = new Intent(getApplicationContext(), MainActivity.class);

                                        ShowActivity.this.startActivity(intent);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse( VolleyError error ) {
                                        Log.d( "RECORD", error.toString() );
                                    }
                                }
                        ) {
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
                        toastIt("Record Deleted");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //Perform something when they say No
                        dialog.cancel();

                        toastIt("Record NOT deleted.");
                    }
                })
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate Menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all :
                // switch to Main activity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_add :
                //switch to the add record activity
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);

                return true;

            case R.id.menu_logout :
                username = "";
                password = "";
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

            default :
                return super.onOptionsItemSelected(item);
        }

    }
}
