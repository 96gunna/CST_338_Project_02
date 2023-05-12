package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {

    private Button deleteUsersButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        deleteUsersButton = findViewById(R.id.removeUsersButton);
        deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RemoveUsersActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }
    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AdminHomeActivity.class);
        return intent;
    }

}