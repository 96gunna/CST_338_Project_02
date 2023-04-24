package com.example.cst_338_project_02;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst_338_project_02.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class RegisteredUser {

    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String username;
    private String password;

    private boolean isAdmin;

    public RegisteredUser(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }
}
