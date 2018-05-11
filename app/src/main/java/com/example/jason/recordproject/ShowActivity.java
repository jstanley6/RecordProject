package com.example.jason.recordproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ShowActivity extends BaseActivity {

    Long result;
    LiveData<Record> record;
    TextView txtName;
    TextView txtDesc;
    TextView txtPrice;
    TextView txtRating;
    EditText edtName;
    Record recordTemp;
    DecimalFormat precision = new DecimalFormat("0.00");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);

//        items = recordDatabase.recordDao().getAll();

        txtName = findViewById(R.id.txtName);
        txtDesc = findViewById(R.id.txtDesc);
        txtPrice = findViewById(R.id.txtPrice);
        txtRating = findViewById(R.id.txtRating);
        Intent intent = getIntent();
        result = intent.getLongExtra("KEY", 0);
//        record = recordDatabase.recordDao().findByRecordNum(result);

        record.observe(this, new Observer<Record>() {
            @Override
            public void onChanged(@Nullable Record record) {
                if(record != null) {
                    recordTemp = record;
                    txtName.setText(record.getName());
                    txtDesc.setText(record.getDescription());
                    txtPrice.setText("$" + precision.format(record.getPrice()));
                    txtRating.setText(Integer.toString(record.getRating()));
                }

            }
        });

    }

    public void switchToEdit(View v) {

        intent = new Intent(this, EditActivity.class);
//        intent.putExtra("KEY", record.getValue().getRecordID());
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
//                        new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                recordDatabase.recordDao().deleteRecord(recordTemp);
//                                intent = new Intent(ShowActivity.this, MainActivity.class);
//                                ShowActivity.this.startActivity(intent);
//                            }
//                        }).start();

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
