package com.example.jason.recordproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    Intent intent;
    private ListView lstViewRecords;
    List<String> tempItems = new ArrayList<String>();
    ArrayAdapter adapter;
    Gson gson;

    LiveData<Record> record;

    List<String> recordNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstViewRecords = findViewById(R.id.listViewRecords);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); //2018-05-07T21:12:27.000Z
        gson = gsonBuilder.create();

        //Create list view array for adapter to use
//        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, tempItems);
//        lstViewRecords.setAdapter(adapter);

//        tempItems.add("Computer");
//        tempItems.add("Keyboard");
//        tempItems.add("Mouse");

//        items = recordDatabase.recordDao().getAll();

//        items.observe(this, new Observer<List<Record>>() {
//            @Override
//            public void onChanged(@Nullable final List<Record> records) {
//
//
//                for (Record r :records) {
//                    recordNames.add(r.getName() + "\n" + r.getDescription().substring(0, 1)
//                    + "...");
////                    recordDesc.add(r.getDescription().trim());
////                    recordDesc.add(r.getDescription());
//
//                }
//
//                lstViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                        intent = new Intent(MainActivity.this, ShowActivity.class);
////                        intent.putExtra("KEY", records.get(position).getRecordID());
//                        startActivity(intent);
//
//                    }
//
//                });

//                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, recordNames);
//                lstViewRecords.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                lstViewRecords.invalidateViews();
//                lstViewRecords.refreshDrawableState();
//            }
//        });



    }

    public void internetOnClick(View v) {

        String url = "https://apirecord.azurewebsites.net/records/1";
        StringRequest request = new StringRequest(Request.Method.GET, url,
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
//                //Call backs
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Do something with the returned data
                        Log.d("INTERNET", response);

                        Record records = gson.fromJson(response, Record.class);
                        //Take data to display on the view

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Do something with the error
                        Log.d("INTERNET", error.toString());
                        toastIt("Internet Failure " + error.toString());

                    }
                });

        requestQueue.add(request);

    }

    public void addRecordActivityOnClick(View v) {
        intent = new Intent(this, AddActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
