package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.CartDAO;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;
import com.example.cst_338_project_02.DB.SeedsDAO;

import java.util.ArrayList;
import java.util.List;

public class SalesFloorActivity extends AppCompatActivity {

    private static final String SALES_USERNAME = "com.example.cst_338_project_02.salesFloorUsername";
    private TextView mainDisplay;
    private Spinner productSpinner;
    private Button cartButton;
    private Button homeButton;
    private RegisteredUser currentUser;
    private String username;
    private int userID;
    private SeedsDAO seedsDAO;
    private RegisteredUsersDAO usersDAO;
    private CartDAO cartDAO;
    private List<Seed> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_floor);
        productSpinner = findViewById(R.id.productSpinner);
        homeButton = findViewById(R.id.backButton);
        cartButton = findViewById(R.id.cartButton);
        mainDisplay = findViewById(R.id.seedsDisplay);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
        getDatabases();
        productList = seedsDAO.getAllProducts();
        username = getIntent().getStringExtra(SALES_USERNAME);
        currentUser = usersDAO.getUserByUsername(username);
        userID = currentUser.getUserId();
        pullDataFromDatabase();
    }

    public static Intent intentFactory(Context context, String username) {
        Intent intent = new Intent(context, SalesFloorActivity.class);
        intent.putExtra(SALES_USERNAME, username);
        return intent;
    }

    public void pullDataFromDatabase() {
        List<String> productStrings = new ArrayList<>();
        if (!productList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Seed s : productList) {
                sb.append(s.toString());
                productStrings.add(s.dropdownOptions());
            }
            mainDisplay.setText(sb.toString());
        } else {
            mainDisplay.setText("All Sold Out!");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, productStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(adapter);
    }

    private void getDatabases() {
        seedsDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.SEEDS_TABLE)
                .allowMainThreadQueries()
                .build()
                .getSeedsDAO();
        usersDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
        cartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.CART_TABLE)
                .allowMainThreadQueries()
                .build()
                .getCartDAO();
    }
}