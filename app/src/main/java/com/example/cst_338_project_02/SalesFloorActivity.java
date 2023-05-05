package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SalesFloorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_floor);
    }

    public static Intent intentFactory(Context context, String username) {
        Intent intent = new Intent(context, SalesFloorActivity.class);
        return intent;
    }
}