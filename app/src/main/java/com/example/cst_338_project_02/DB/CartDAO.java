package com.example.cst_338_project_02.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst_338_project_02.Cart;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insert(Cart...carts);

    @Update
    void update(Cart...carts);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE)
    List<Cart> getAllCarts();

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE userId= :userId")
    Cart getCartByUsername(String userId);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE cartId= :cartId")
    Cart getCartByCartId(int cartId);
}
