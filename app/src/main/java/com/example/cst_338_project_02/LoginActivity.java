package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText passInput;
    private Button button;
    private RegisteredUsersDAO database;
    private String nameInputString, passInputString;
    private RegisteredUser user;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getDatabase();
        nameInput = findViewById(R.id.editTextUsername);
        passInput = findViewById(R.id.editTextPassword);
        button = findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if (checkForUserInDatabase()) {
                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), nameInputString, isAdmin);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void getDatabase() {
        database = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
    }

    private void getValuesFromDisplay() {
        nameInputString = nameInput.getText().toString();
        passInputString = passInput.getText().toString();
        user = database.getUserByUsername(nameInputString);
        isAdmin = user.isAdmin();
    }

    private boolean checkForUserInDatabase() {
        user = database.getUserByUsername(nameInputString);
        if (user == null) {
            Toast.makeText(this, "no " + nameInputString + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        return user.getPassword().equals(passInputString);
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}