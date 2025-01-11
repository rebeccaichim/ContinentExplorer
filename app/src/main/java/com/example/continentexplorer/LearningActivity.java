package com.example.continentexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LearningActivity extends AppCompatActivity {

    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        // Preia userId din Intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            // Dacă userId nu a fost primit corect, redirecționează către LoginActivity
            Log.e("LearningActivity", "User ID not found. Redirecting to Login.");
            Intent intent = new Intent(LearningActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Închide activitatea curentă
            return;
        }

        Log.d("LearningActivity", "User ID received: " + userId);

        // Configurare buton de back
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Configurare butoane pentru jocuri
        Button startEuropeButton = findViewById(R.id.startEuropeButton);
        Button startRomaniaButton = findViewById(R.id.startRomaniaButton);

        // Butonul pentru Europa
        startEuropeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, LearningActivityEuropa.class);
                intent.putExtra("userId", userId); // Transmite userId către LearningActivityEuropa
                startActivity(intent);
            }
        });

        // Butonul pentru România
        startRomaniaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, LearningActivityRomania.class);
                intent.putExtra("userId", userId); // Transmite userId către LearningActivityRomania
                startActivity(intent);
            }
        });

        // Configurare navigație de jos (Bottom Navigation)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Setează elementul curent activ
        bottomNavigationView.setSelectedItemId(R.id.nav_learning);

        // Ascultător pentru schimbarea paginilor din navigație
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_learning) {
                return true; // Rămâi pe pagina curentă
            } else if (itemId == R.id.nav_maps) {
                Intent intent = new Intent(LearningActivity.this, YourMapsActivity.class);
                intent.putExtra("userId", userId); // Transmite userId către YourMapsActivity
                startActivity(intent);
                overridePendingTransition(0, 0); // Dezactivează animația de tranziție
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(LearningActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId); // Transmite userId către ProfileActivity
                startActivity(intent);
                overridePendingTransition(0, 0); // Dezactivează animația de tranziție
                return true;
            }

            return false;
        });
    }
}
