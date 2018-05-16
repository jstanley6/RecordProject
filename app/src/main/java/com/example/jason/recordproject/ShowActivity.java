package com.example.jason.recordproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

public class ShowActivity extends BaseActivity {

    Record record;
    TextView txtName;
    TextView txtDesc;
    TextView txtPrice;
    TextView txtRating;
    TextView txtImage;
    DecimalFormat precision = new DecimalFormat("0.00");
    int recordID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); //2018-05-07T21:12:27.000Z
        gson = gsonBuilder.create();

        txtName = findViewById(R.id.txtName);
        txtDesc = findViewById(R.id.txtDesc);
        txtPrice = findViewById(R.id.txtPrice);
        txtRating = findViewById(R.id.txtRating);
        txtImage = findViewById(R.id.txtImage);
        intent = getIntent();
        recordID = intent.getIntExtra("recordID", 0);

//        txtName.setText(records[recordID].getName());
//        txtDesc.setText(records[recordID].getDescription());
//        txtPrice.setText(records[recordID].getPrice().toString());
//        txtRating.setText(records[recordID].getRating().toString());

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
                        txtImage.setText(record.getImage());



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

    public void switchToEdit(View v) {

        intent = new Intent(this, EditActivity.class);
        intent.putExtra("recordID", record.getId());
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
                        );

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

            default :
                return super.onOptionsItemSelected(item);
        }

    }
}
