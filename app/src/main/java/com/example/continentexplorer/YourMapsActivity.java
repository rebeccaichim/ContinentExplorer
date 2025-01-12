package com.example.continentexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class YourMapsActivity extends AppCompatActivity {

    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_maps);

        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Intent intent = new Intent(YourMapsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button startYourMapsEuropeButton = findViewById(R.id.startYourMapsEuropeButton);
        Button startYourMapsRomaniaButton = findViewById(R.id.startYourMapsRomaniaButton);

        startYourMapsEuropeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourMapsActivity.this, YourMapsActivityEuropa.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        startYourMapsRomaniaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourMapsActivity.this, YourMapsActivityRomania.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_maps);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_learning) {
                Intent intent = new Intent(YourMapsActivity.this, LearningActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_maps) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(YourMapsActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
