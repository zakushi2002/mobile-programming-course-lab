package com.example.roomlibrarydatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomlibrarydatabase.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);
    @Query("select * from user")
    List<User> findAll();
}
