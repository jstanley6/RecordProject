package com.example.jason.recordproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Date;

public class EditActivity extends BaseActivity {

    Long result;
    EditText edtName;
    EditText edtDesc;
    EditText edtPrice;
    EditText edtRating;
    LiveData<Record> record;
    DecimalFormat precision = new DecimalFormat("0.00");
    Record recordTemp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        items = recordDatabase.recordDao().getAll();
        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtRating = findViewById(R.id.edtRating);



        Intent intent = getIntent();

        result = intent.getLongExtra("KEY", 0);
//        record = recordDatabase.recordDao().findByRecordNum(result);

        toastIt("recordid is " + result);
        record.observe(this, new Observer<Record>() {
            @Override
            public void onChanged(@Nullable Record record) {
                recordTemp = record;

                edtName.setText(record.getName());
                edtDesc.setText(record.getDescription());
                edtPrice.setText(Double.toString(record.getPrice()));
                edtRating.setText(Integer.toString(record.getRating()));

            }
        });

    }

    public void editButtonOnClick(View v) {


        final String recordDescription = edtDesc.getText().toString();
        final String recordName = edtName.getText().toString();
        final int recordRating = Integer.parseInt(edtRating.getText().toString());
        final double recordPrice = Double.parseDouble(edtPrice.getText().toString());

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//
//                recordTemp.setName(recordName);
//                recordTemp.setDescription(recordDescription);
//                recordTemp.setRating(recordRating);
//                recordTemp.setPrice(recordPrice);
////                recordTemp.setDateModified(new Date());
//
//
//                recordDatabase.recordDao().updateRecord(recordTemp);
//            }
//        }).start();
//                intent = new Intent(this, ShowActivity.class);
//               intent.putExtra("KEY", record.getValue().getRecordID());
//                EditActivity.this.startActivity(intent);
//
  }


}
