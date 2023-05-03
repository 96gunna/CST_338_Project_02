package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;
import com.example.cst_338_project_02.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button logButton;
    private Button createButton;
    private ActivityMainBinding binding;
    private RegisteredUsersDAO usersDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDatabase();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        logButton = binding.loginButton;
        createButton = binding.createAccButton;
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RegisteredUser> users = usersDAO.getAllUsers();
                System.out.println(users.size());
                if (users.size() <= 0) {
                    RegisteredUser testuser1 = new RegisteredUser("testuser1", "testuser1", false);
                    RegisteredUser admin2 = new RegisteredUser("admin2", "admin2", true);
                    usersDAO.insert(testuser1, admin2);
                }
                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private void getDatabase() {
        usersDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}