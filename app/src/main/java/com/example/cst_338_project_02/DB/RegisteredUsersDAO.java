package com.example.cst_338_project_02.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst_338_project_02.RegisteredUser;

import java.util.List;

@Dao
public interface RegisteredUsersDAO {

    @Insert
    void insert(RegisteredUser...registry);

    @Update
    void update(RegisteredUser...registry);

    @Delete
    void delete(RegisteredUser user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<RegisteredUser> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username= :username")
    RegisteredUser getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userId= :userId")
    RegisteredUser getUserByUserId(String userId);
}
