package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer3); // Ensure this matches your layout file name

        // Initialize buttons
        Button profileButton = findViewById(R.id.profile_button);
        Button customButton = findViewById(R.id.custom_button);
        Button sellButton = findViewById(R.id.sell_button);
        Button myOrderButton = findViewById(R.id.my_order_button);

        // Set click listeners for navigation
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity3.this, ProfileActivity2.class);
                startActivity(intent);
            }
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Custom Activity (if you have one)
                Intent intent = new Intent(CustomerActivity3.this, CustomerActivity3.class);
                startActivity(intent);
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity3.this, SellerActivity3.class);
                startActivity(intent);
            }
        });

        myOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity3.this, MyoderActivity4.class);
                startActivity(intent);
            }
        });
    }
}