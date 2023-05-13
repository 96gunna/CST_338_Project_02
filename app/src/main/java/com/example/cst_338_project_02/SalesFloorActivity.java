package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button viewCartButton;
    private RegisteredUser currentUser;
    private String username;
    private int userID;
    private String dropdownSelection;
    private SeedsDAO seedsDAO;
    private RegisteredUsersDAO usersDAO;
    private CartDAO cartDAO;
    private List<Seed> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_floor);
        wireUpDisplay();
        getDatabases();
        pullDataFromDatabase();
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ViewCartActivity.intentFactory(getApplicationContext(), currentUser.getUserId());
                startActivity(intent);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admin = Boolean.toString(currentUser.isAdmin());
                Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), username, admin);
                startActivity(intent);
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dropdownSelection != null) {
                    Seed newSeed = seedsDAO.getProductBySciName(dropdownSelection);
                    int count = newSeed.getCurrentCount() - 1;
                    newSeed.setCurrentCount(count);
                    seedsDAO.update(newSeed);
                    Cart cart = new Cart(newSeed.getProductId(), userID);
                    cartDAO.insert(cart);
                    Toast.makeText(SalesFloorActivity.this, dropdownSelection + " was added to the cart.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SalesFloorActivity.this, "Please enter a search query.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean noInteraction = true;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (noInteraction) {
                    noInteraction = false;
                } else {
                    String spinnerText = (String) adapterView.getItemAtPosition(i);
                    if (!spinnerText.equals("-Select-")) {
                        String[] names = spinnerText.split(":");
                        Toast.makeText(SalesFloorActivity.this, names[0], Toast.LENGTH_SHORT).show();
                        dropdownSelection = names[1];
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static Intent intentFactory(Context context, String username) {
        Intent intent = new Intent(context, SalesFloorActivity.class);
        intent.putExtra(SALES_USERNAME, username);
        return intent;
    }

    private void wireUpDisplay() {
        productSpinner = findViewById(R.id.productSpinner);
        homeButton = findViewById(R.id.backButton);
        cartButton = findViewById(R.id.cartButton);
        viewCartButton = findViewById(R.id.viewCartButton);
        mainDisplay = findViewById(R.id.seedsDisplay);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
    }

    private void pullDataFromDatabase() {
        productList = seedsDAO.getAllProducts();
        username = getIntent().getStringExtra(SALES_USERNAME);
        currentUser = usersDAO.getUserByUsername(username);
        userID = currentUser.getUserId();
        List<String> productStrings = new ArrayList<>();
        productStrings.add("-Select-");
        if (!productList.isEmpty()) {
            StringBuilder displayText = new StringBuilder();
            for (Seed seed : productList) {
                displayText.append(seed.toString());
                productStrings.add(seed.getName() + ":" + seed.getScientificName());
            }
            mainDisplay.setText(displayText.toString());
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