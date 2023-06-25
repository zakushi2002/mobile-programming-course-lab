package com.mobile.machinelearning.translationapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mobile.machinelearning.translationapp.model.HistoryItem;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insertHistory(HistoryItem historyItem);
    @Query("select * from history")
    List<HistoryItem> findAll();
}