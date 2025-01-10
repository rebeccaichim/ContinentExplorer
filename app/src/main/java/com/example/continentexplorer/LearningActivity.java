package com.example.continentexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LearningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button startEuropeButton = findViewById(R.id.startEuropeButton);
        Button startRomaniaButton = findViewById(R.id.startRomaniaButton);

        startEuropeButton.setOnClickListener(v -> {
        });

        // Butonul de start pentru Europa
        startEuropeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, LearningActivityEuropa.class);
                startActivity(intent);
            }
        });

        startRomaniaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, LearningActivityRomania.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_learning) {
                return true;
            } else if (itemId == R.id.nav_maps) {
                Intent intent = new Intent(LearningActivity.this, MapsActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(LearningActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

    }
}
