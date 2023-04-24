package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPageActivity extends AppCompatActivity {

    private static final String LANDING_USERNAME = "com.example.cst_338_project_02.landingPageUsername";
    private static final String ADMIN_CHECK = "com.example.cst_338_project_02.isAdmin";
    private TextView welcomeMessage;
    private boolean adminCheck;
    private String username;
    private Button adminActions;
    private Button logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        welcomeMessage = findViewById(R.id.textViewWelcomeMes);
        username = getIntent().getStringExtra(LANDING_USERNAME);
        adminCheck = getIntent().getBooleanExtra(ADMIN_CHECK, false);
        adminActions = findViewById(R.id.adminButton);
        if (adminCheck) {
            adminActions.setVisibility(View.VISIBLE);
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
        setWelcome();

    }

    public static Intent intentFactory(Context context, String username, boolean admin) {
        Intent intent = new Intent(context, LandingPageActivity.class);
        intent.putExtra(LANDING_USERNAME,username);
        intent.putExtra(ADMIN_CHECK, admin);
        return intent;
    }

    private void setWelcome() {
        welcomeMessage.setText("Welcome " + username + "!");
    }
}