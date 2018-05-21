package com.example.jason.recordproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {
    EditText edtUserName;
    EditText edtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);

        hideKeyboard(LoginActivity.this);


    }

    public void onSubmit(View v) {

        username = edtUserName.getText().toString();
        password = edtPassword.getText().toString();


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


                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        // Do something with the error
                        Log.d( "INTERNET", error.toString() );
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                LoginActivity.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Bad Internet Connection or Bad Login ");

                        // Setting Dialog Message
                        alertDialog.setMessage("Please make sure you are entering the right credentials or check internet connection and try again.");


                        // Setting Try Again Button
                        alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        LoginActivity.this.startActivity(intent);

                            }
                        });

                        alertDialog.show();

                        toastIt( "Internet Failure: " + error.toString() );
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String credentials = username + ":" + password;
                Log.d("AUTH", "Login Info: " + credentials);
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };

        requestQueue.add( request );



    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
