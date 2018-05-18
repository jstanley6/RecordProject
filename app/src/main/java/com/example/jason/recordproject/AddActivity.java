package com.example.jason.recordproject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends BaseActivity {

    public EditText edtName;
    public EditText edtDescription;
    public EditText edtRating;
    public EditText edtPrice;
    public EditText edtImage;
    Intent intent;
    Record record;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); //2018-05-07T21:12:27.000Z
        gson = gsonBuilder.create();

        setContentView(R.layout.activity_add);

        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDesc);
        edtRating = findViewById(R.id.edtRating);
        edtPrice = findViewById(R.id.edtPrice);
        edtImage = findViewById(R.id.edtImage);
        edtName.setFocusable(true);



    }

    public void addRecordOnClick(View v) {

        String url = "https://apirecord.azurewebsites.net/records";


                        if(TextUtils.isEmpty(edtName.getText()))  {
                            edtName.setError("First Name is Required!!!");
                        } else if (TextUtils.isEmpty(edtDescription.getText())) {
                            edtDescription.setError("You must enter a description!");
                        }
                        else if (TextUtils.isEmpty(edtPrice.getText())) {
                            edtPrice.setError("Must add a price!");
                        }
                        else if  (TextUtils.isEmpty(edtRating.getText()) || (Integer.parseInt(edtRating.getText().toString()) > 5 )) {
                            edtRating.setError("Rating must be a value from 1 through 5!");
                        } else if (TextUtils.isEmpty(edtPrice.getText())) {

                            edtPrice.setError("Must add a price!");
                        } else if (TextUtils.isEmpty(edtImage.getText())) {
                            edtImage.setError("Please Enter Image URL");
                        } else {
                            final String recordRating = edtRating.getText().toString();
                            final String recordDescription = edtDescription.getText().toString();
                            final String recordName = edtName.getText().toString();
                            final String recordPrice = edtPrice.getText().toString();
                            final String recordImage = edtImage.getText().toString();


                           // HASHMAP  = HASH  Key=>Value
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("name", recordName);
                            params.put("description", recordDescription);
                            params.put("rating", recordRating);
                            params.put("price", recordPrice);
                            params.put("image", recordImage);

                            // POST that JSON object to the server using VOLLEY
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject( params ),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse( JSONObject response ) {
                                            Log.d( "RECORD", response.toString() );
                                            intent = new Intent(getApplicationContext(), MainActivity.class);
                                            AddActivity.this.startActivity(intent);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse( VolleyError error ) {
                                            Log.d( "RECORD", error.toString() );
                                        }
                                    }
                            )
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
                            intent = new Intent(AddActivity.this, MainActivity.class);
                            AddActivity.this.startActivity(intent);

                        }


            }

    public void toastIt(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
