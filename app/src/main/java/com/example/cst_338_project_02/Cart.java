package com.example.cst_338_project_02;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst_338_project_02.DB.AppDatabase;

@Entity(tableName = AppDatabase.CART_TABLE)
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int cartId;

    private int productId;

    private int userId;

    public Cart(int productId, int userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
