package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;
import com.example.cst_338_project_02.DB.SeedsDAO;

import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    private static final String LANDING_USERNAME = "com.example.cst_338_project_02.landingPageUsername";
    private static final String ADMIN_CHECK = "com.example.cst_338_project_02.isAdmin";
    private TextView welcomeMessage;
    private String adminCheck;
    private String username;
    private Button adminActions;
    private Button logOut;
    private Button browseProducts;
    private Button viewCart;
    private SeedsDAO database;
    private RegisteredUsersDAO usersDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        getDatabase();
        welcomeMessage = findViewById(R.id.textViewWelcomeMes);
        username = getIntent().getStringExtra(LANDING_USERNAME);
        adminCheck = getIntent().getStringExtra(ADMIN_CHECK);
        adminActions = findViewById(R.id.adminButton);
        if (adminCheck.equals("true")) {
            adminActions.setVisibility(View.VISIBLE);
            adminActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = AdminHomeActivity.intentFactory(getApplicationContext(), username);
                    startActivity(intent);
                }
            });
        } else {
            adminActions.setVisibility(View.INVISIBLE);
        }
        logOut = findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        viewCart = findViewById(R.id.viewCartButtonLanding);
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisteredUser currentUser = usersDAO.getUserByUsername(username);
                Intent intent = ViewCartActivity.intentFactory(getApplicationContext(), currentUser.getUserId());
                startActivity(intent);
            }
        });
        browseProducts = findViewById(R.id.buttonBrowseProducts);
        browseProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Seed> seedsList = database.getAllProducts();
                if (seedsList.size() == 0) {
                    Seed seed0 = new Seed("California Redwood", "Sequoia Sempervirens", 99.99, 500);
                    Seed seed1 = new Seed("Mountain Paper Birch ", "Betula Cordifolia", 0.50, 5000);
                    Seed seed2 = new Seed("Apple Tree", "Malus Domestica", 1.50, 8907);
                    database.insert(seed0, seed1, seed2);
                }
                Intent intent = SalesFloorActivity.intentFactory(getApplicationContext(), username);
                startActivity(intent);
            }
        });
        setWelcome();
    }

    public static Intent intentFactory(Context context, String username, String admin) {
        Intent intent = new Intent(context, LandingPageActivity.class);
        intent.putExtra(LANDING_USERNAME, username);
        intent.putExtra(ADMIN_CHECK, admin);
        return intent;
    }

    private void setWelcome() {
        String text = "Welcome " + username + "!";
        welcomeMessage.setText(text);
    }

    private void getDatabase() {
        database = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.SEEDS_TABLE)
                .allowMainThreadQueries()
                .build()
                .getSeedsDAO();
        usersDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
    }
}