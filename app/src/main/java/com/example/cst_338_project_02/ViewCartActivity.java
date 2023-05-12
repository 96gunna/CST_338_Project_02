package com.example.cst_338_project_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst_338_project_02.DB.AppDatabase;
import com.example.cst_338_project_02.DB.CartDAO;
import com.example.cst_338_project_02.DB.RegisteredUsersDAO;
import com.example.cst_338_project_02.DB.SeedsDAO;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    private static final String USER_ID = "com.example.cst_338_project_02.viewCartUserId";
    private TextView mainDisplay;
    private TextView lowerDisplay;
    private Button returnToBrowse;
    private Button checkOutButton;
    private SeedsDAO seedsDAO;
    private RegisteredUsersDAO usersDAO;
    private RegisteredUser currentUser;
    private CartDAO cartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        getDatabases();
        wireUpDisplay();
        pullDataFromDatabase();
    }

    public void wireUpDisplay() {
        int userId = getIntent().getIntExtra(USER_ID, -1);
        currentUser = usersDAO.getUserByUserId(userId);
        mainDisplay = findViewById(R.id.cartProductDisplay);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
        lowerDisplay = findViewById(R.id.cartInfoTextView);
        returnToBrowse = findViewById(R.id.backToBrowseButton);
        returnToBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SalesFloorActivity.intentFactory(getApplicationContext(), currentUser.getUsername());
                startActivity(intent);
            }
        });
        checkOutButton = findViewById(R.id.checkOutButton);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Cart> userCart = cartDAO.getCartsByUserId(userId);
                if (userCart.size() != 0) {
                    for (Cart cart : userCart) {
                        cartDAO.delete(cart);
                    }
                    mainDisplay.setText("Click Browse to view products.");
                    lowerDisplay.setText("Nothing in cart.");
                } else {
                    Toast.makeText(ViewCartActivity.this, "Add items to your cart to proceed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void pullDataFromDatabase() {
        int userId = getIntent().getIntExtra(USER_ID, -1);
        // All carts associated with the current user
        List<Cart> userCart = cartDAO.getCartsByUserId(userId);
        if (!userCart.isEmpty()) {
            double totalPrice = 0.0;
            StringBuilder mainText = new StringBuilder();
            StringBuilder lowerText = new StringBuilder();
            HashMap<String, Integer> cartCount = new HashMap<>();
            Seed seed;
            for (Cart cart : userCart) {
                seed = seedsDAO.getProductById(cart.getProductId());
                if (!cartCount.containsKey(seed.getScientificName())) {
                    cartCount.put(seed.getScientificName(), 1);
                } else {
                    Integer count = cartCount.get(seed.getScientificName()) + 1;
                    cartCount.put(seed.getScientificName(), count);
                }
                totalPrice += seed.getPrice();
                mainText.append(seed.getName() + ":" + seed.getScientificName() + "\n");
                mainText.append("Price: $" + String.format("%.2f", seed.getPrice()) + "\n");
                mainText.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
            }
            for (String product : cartCount.keySet()) {
                lowerText.append(cartCount.get(product) + " " + product + " seeds\n");
            }
            lowerText.append("Total Price: " + String.format("%.2f", totalPrice));
            mainDisplay.setText(mainText.toString());
            lowerDisplay.setText(lowerText.toString());
        }
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

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ViewCartActivity.class);
        intent.putExtra(USER_ID, userId);
        return intent;
    }
}