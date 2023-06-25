package com.mobile.machinelearning.translationapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mobile.machinelearning.translationapp.model.HistoryItem;

@Database(entities = {HistoryItem.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "history.db";
    private static HistoryDatabase instance;
    public static synchronized HistoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), HistoryDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract HistoryDao historyDao();
}