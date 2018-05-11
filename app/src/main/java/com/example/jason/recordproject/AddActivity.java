package com.example.jason.recordproject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Date;

public class AddActivity extends BaseActivity {

    public EditText edtName;
    public EditText edtDescription;
    public EditText edtRating;
    public EditText edtPrice;
    Intent intent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDesc);
        edtRating = findViewById(R.id.edtRating);
        edtPrice = findViewById(R.id.edtPrice);
        edtName.setFocusable(true);


    }

    public void addRecordOnClick(View v) {

        try {
//            if(TextUtils.isEmpty(edtName.getText()))  {
//                edtName.setError("First Name is Required!!!");
//            } else if (Integer.parseInt(edtRating.getText().toString()) > 5  && (edtRating.getText().toString().equals("")) )  {
//                edtRating.setError("Rating must be a value from 1 through 5!");
//            }else if (TextUtils.isEmpty(edtDescription.getText())) {
//                edtDescription.setError("You must enter a description!");
//            } else if (TextUtils.isEmpty(edtPrice.getText())) {
//
//                edtPrice.setError("Must add a price!");
//            }
        } catch (NumberFormatException e) {

        }

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
        } else {
            final int recordRating = Integer.parseInt(edtRating.getText().toString());
            final String recordDescription = edtDescription.getText().toString();
            final String recordName = edtName.getText().toString();

            final double recordPrice = Double.parseDouble(edtPrice.getText().toString());
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//
//
//                    Record record = new Record();
//                    record.setName(recordName);
//                    record.setDescription(recordDescription);
//                    record.setRating(recordRating);
//                    record.setPrice(recordPrice);
//                    record.setDateCreated(new Date());
//
//                    recordDatabase.recordDao().addRecord(record);
//                }
//
//            }).start();


            intent = new Intent(this, MainActivity.class);
            AddActivity.this.startActivity(intent);
        }


            }

    public void toastIt(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
