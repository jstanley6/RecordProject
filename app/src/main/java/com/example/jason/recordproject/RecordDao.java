package com.example.jason.recordproject;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jason.recordproject.Record;

import java.util.List;

@Dao
public interface RecordDao {

    @Query( "SELECT * FROM records" )
    LiveData<List<Record>> getAll();

    @Query("SELECT name from records")
    LiveData<List<String>> getAllRecordNames();

    @Query("SELECT * FROM records WHERE name= :record_name LIMIT 1")
    Record findByName(String record_name);

    @Query("SELECT * FROM records WHERE recordID = :recordID")
    LiveData<Record> findByRecordNum(long recordID);

    @Insert
    void addRecord(Record record);

    @Update
    void updateRecord(Record record);

    @Delete
    void deleteRecord(Record record);
}