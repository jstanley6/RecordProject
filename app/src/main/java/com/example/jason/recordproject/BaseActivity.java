package com.example.jason.recordproject;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public static LiveData<List<Record>> items;
    Intent intent;

//    public AppDatabase recordDatabase;
    public RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { //, @Nullable PersistableBundle persistentState
        super.onCreate(savedInstanceState); //, persistentState
        setContentView(R.layout.activity_main);

//
//        if (recordDatabase == null) {
//            recordDatabase = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, "records.db")
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
        //Volley Library
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    public void toastIt(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
