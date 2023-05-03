package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;

public class CreateAccountActivity extends AppCompatActivity {

    private String username, password, verification;
    private Button createButton;
    private EditText nameInput;
    private EditText passInput;
    private EditText passVerify;
    private RegisteredUsersDAO database;
    private RegisteredUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getDatabase();
        nameInput = findViewById(R.id.editTextNewName);
        passInput = findViewById(R.id.editTextNewPassword);
        passVerify = findViewById(R.id.editTextVerifyPass);
        createButton = findViewById(R.id.createAccountButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = nameInput.getText().toString();
                password = passInput.getText().toString();
                verification = passVerify.getText().toString();
                if (username.isEmpty() || password.isEmpty() || verification.isEmpty()) {
                    emptyFieldsAlert();
                    return;
                }
                if (!verifyPassword(password, verification)) {
                    return;
                }
                if (!verifyUsername(username)) {
                    return;
                }
                // Add user to database
                newUser = new RegisteredUser(username, password, false);
                database.insert(newUser);
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        Toast.makeText(this, "Account created successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = LoginActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    private void emptyFieldsAlert() {
        Toast.makeText(this, "Please complete all fields to continue.", Toast.LENGTH_SHORT).show();
    }
    private boolean verifyUsername(String username) {
        RegisteredUser temp = database.getUserByUsername(username);
        if (!(temp instanceof RegisteredUser)) {
            return true;
        }
        Toast.makeText(this, "Username already in use. Please select another.", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean verifyPassword(String password, String verification) {
        if (!(password.equals(verification))) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        return intent;
    }

    private void getDatabase() {
        database = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
    }
}

