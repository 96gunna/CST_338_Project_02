package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    private static final String ADMIN_USERNAME = "com.example.cst_338_project_02.adminHomeActivity";
    private Button deleteUsersButton;
    private Button makeAdminButton;
    private Spinner userSpinner;
    private RegisteredUsersDAO usersDAO;
    private String adminUsername;
    private String dropdownSelection = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getDatabase();
        adminUsername = getIntent().getStringExtra(ADMIN_USERNAME);
        deleteUsersButton = findViewById(R.id.removeUsersButton);
        deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RemoveUsersActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        userSpinner = findViewById(R.id.userSpinner);
        fillSpinner();
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean noInteraction = true;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (noInteraction) {
                    noInteraction = false;
                } else {
                    String spinnerText = (String) adapterView.getItemAtPosition(i);
                    if (!spinnerText.equals("-Select-")) {
                        dropdownSelection = spinnerText;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        makeAdminButton = findViewById(R.id.makeAdminButton);
        makeAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dropdownSelection.isEmpty() && !dropdownSelection.equals("-Select-")) {
                    RegisteredUser promotedUser = usersDAO.getUserByUsername(dropdownSelection);
                    promotedUser.setAdmin(true);
                    usersDAO.update(promotedUser);
                    Toast.makeText(AdminHomeActivity.this, promotedUser.getUsername() + " promoted to Admin.", Toast.LENGTH_SHORT).show();
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) userSpinner.getAdapter();
                    adapter.remove(dropdownSelection);
                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(AdminHomeActivity.this, "Please make a selection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static Intent intentFactory(Context context, String username) {
        Intent intent = new Intent(context, AdminHomeActivity.class);
        intent.putExtra(ADMIN_USERNAME, username);
        return intent;
    }

    private void fillSpinner() {
        List<RegisteredUser> currentUsers = usersDAO.getAllUsers();
        List<String> spinnerValues = new ArrayList<>();
        spinnerValues.add("-Select-");
        for (RegisteredUser user: currentUsers) {
            if (user.getUsername().equals(adminUsername) || user.isAdmin()) {
                continue;
            }
            spinnerValues.add(user.getUsername());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
    }

    private void getDatabase() {
        usersDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
    }

}