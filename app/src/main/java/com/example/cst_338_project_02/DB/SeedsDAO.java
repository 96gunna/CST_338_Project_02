package com.example.cst_338_project_02.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst_338_project_02.Seed;

import java.util.List;

@Dao
public interface SeedsDAO {

    @Insert
    void insert(Seed...seeds);

    @Update
    void update(Seed...seeds);

    @Delete
    void delete(Seed seed);

    @Query("SELECT * FROM " + AppDatabase.SEEDS_TABLE)
    List<Seed> getAllProducts();

    @Query("SELECT * FROM " + AppDatabase.SEEDS_TABLE + " WHERE name LIKE:name")
    Seed getProductByName(String name);

    @Query("SELECT * FROM " + AppDatabase.SEEDS_TABLE + " WHERE scientificName LIKE:scientificName")
    Seed getProductBySciName(String scientificName);

    @Query("SELECT * FROM " + AppDatabase.SEEDS_TABLE + " WHERE productId= :productId")
    Seed getProductById(String productId);
}
