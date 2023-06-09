package com.example.cst_338_project_02.DB;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cst_338_project_02.Cart;
import com.example.cst_338_project_02.RegisteredUser;
import com.example.cst_338_project_02.Seed;

@Database(entities = {RegisteredUser.class, Seed.class, Cart.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String SEEDS_TABLE = "SEEDS_TABLE";
    public static final String CART_TABLE = "CART_TABLE";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();
    public abstract RegisteredUsersDAO getRegisteredUsersDAO();
    public abstract SeedsDAO getSeedsDAO();
    public abstract CartDAO getCartDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, USER_TABLE).build();
                }
            }
        }
        return instance;
    }
}
