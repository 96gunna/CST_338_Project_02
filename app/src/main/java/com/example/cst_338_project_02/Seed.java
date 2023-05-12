package com.example.cst_338_project_02;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst_338_project_02.DB.AppDatabase;

@Entity(tableName = AppDatabase.SEEDS_TABLE)
public class Seed {
    @PrimaryKey(autoGenerate = true)
    private int productId;
    private String name;
    private String scientificName;
    private double price;
    private int currentCount;

    public Seed(String name, String scientificName, double price, int currentCount) {
        this.name = name;
        this.scientificName = scientificName;
        this.price = price;
        this.currentCount = currentCount;
    }

    @Override
    public String toString() {
        return name + " : " + scientificName + "\nPrice: " + String.format("%.2f", price) +"\nCurrent Stock: " + currentCount + "\n-=-=-=-=-=-=-=-=-=-=\n";
    }

    public String nameString() {
        return name + " : " + scientificName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }
}
