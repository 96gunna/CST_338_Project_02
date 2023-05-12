package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;

import java.util.List;

public class RemoveUsersActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static List<RegisteredUser> users;
    private RegisteredUsersDAO usersDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_users);
        getDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.usersRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        users = usersDAO.getAllUsers();
        System.out.println(users);
        adapter = new recyclerAdapter(users);
        recyclerView.setAdapter(adapter);

    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, RemoveUsersActivity.class);
        return intent;
    }

    private void getDatabase() {
        usersDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getRegisteredUsersDAO();
    }
}