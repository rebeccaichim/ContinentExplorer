package com.example.continentexplorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.continentexplorer.dto.CombinedScoresResponse;
import com.example.continentexplorer.dto.Score;
import com.example.continentexplorer.model.ScoreCountiesGame;
import com.example.continentexplorer.model.ScoreCountriesGame;
import com.example.continentexplorer.model.User;
import com.example.continentexplorer.network.ApiService;
import com.example.continentexplorer.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName, userGender, userAge, userEmail;
    private RecyclerView scoresRecyclerView, europaScoresRecyclerView;
    private Button logOutButton;
    private ApiService apiService;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inițializează API-ul
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Preia userId din Intent
        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            // Dacă userId nu a fost primit corect, redirecționează utilizatorul către Login
            Log.e("ProfileActivity", "User ID not found. Redirecting to Login.");
            navigateToWelcome();
            return;
        }

        // Inițializează componentele UI
        userName = findViewById(R.id.userName);
        userGender = findViewById(R.id.userGender);
        userAge = findViewById(R.id.userAge);
        userEmail = findViewById(R.id.userEmail);
        scoresRecyclerView = findViewById(R.id.scoresRecyclerView);
        europaScoresRecyclerView = findViewById(R.id.europaScoresRecyclerView); // Inițializare RecyclerView pentru Europa
        logOutButton = findViewById(R.id.logOutButton);

        ImageView backButton = findViewById(R.id.backButton);
        ImageView editProfileButton = findViewById(R.id.editProfileButton);

        backButton.setOnClickListener(v -> onBackPressed());
        editProfileButton.setOnClickListener(v -> navigateToEditProfile());

        logOutButton.setOnClickListener(v -> logOut());

        // Configurează RecyclerView-urile pentru scoruri
        scoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoresRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        europaScoresRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Configurare LayoutManager pentru Europa

        // Încarcă datele utilizatorului
        loadUserProfile();
        loadUserScores();
    }

    private void loadUserProfile() {
        apiService.getUserProfile(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    userName.setText(user.getFullName());
                    userGender.setText(user.getGender());
                    userAge.setText(user.getAge() + " years old");
                    userEmail.setText(user.getEmail());
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load user profile.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ProfileActivity", "Error loading user profile: " + t.getMessage());
            }
        });
    }

    private void loadUserScores() {
        apiService.getAllScores(userId).enqueue(new Callback<CombinedScoresResponse>() {
            @Override
            public void onResponse(Call<CombinedScoresResponse> call, Response<CombinedScoresResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CombinedScoresResponse scores = response.body();

                    // Mapăm scorurile din România și Europa în format generic
                    List<Score> romaniaScores = mapRomaniaScoresToGeneric(scores.getRomaniaScores());
                    List<Score> europaScores = mapEuropaScoresToGeneric(scores.getEuropaScores());

                    // Setăm adapterele
                    ScoresAdapter romaniaAdapter = new ScoresAdapter(romaniaScores);
                    scoresRecyclerView.setAdapter(romaniaAdapter);

                    ScoresAdapter europaAdapter = new ScoresAdapter(europaScores);
                    europaScoresRecyclerView.setAdapter(europaAdapter);
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load scores.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CombinedScoresResponse> call, Throwable t) {
                Log.e("ProfileActivity", "Error loading scores: " + t.getMessage());
            }
        });
    }

    private List<Score> mapRomaniaScoresToGeneric(List<ScoreCountiesGame> romaniaScores) {
        List<Score> genericScores = new ArrayList<>();
        for (ScoreCountiesGame romaniaScore : romaniaScores) {
            Score score = new Score();
            score.setAttemptTime(romaniaScore.getAttemptTime());
            score.setTotalScore(romaniaScore.getTotalScore());
            score.setFromCountiesGame(true); // Este scor din România
            genericScores.add(score);
        }
        return genericScores;
    }

    private List<Score> mapEuropaScoresToGeneric(List<ScoreCountriesGame> europaScores) {
        List<Score> genericScores = new ArrayList<>();
        for (ScoreCountriesGame europaScore : europaScores) {
            Score score = new Score();
            score.setAttemptTime(europaScore.getAttemptTime());
            score.setTotalScore(europaScore.getTotalScore());
            score.setFromCountiesGame(false); // Este scor din Europa
            genericScores.add(score);
        }
        return genericScores;
    }

    private void logOut() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        navigateToWelcome();
    }

    private void navigateToWelcome() {
        Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToEditProfile() {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        intent.putExtra("userId", userId); // Trimite userId către EditProfileActivity
        startActivityForResult(intent, 1); // Așteaptă un rezultat
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Preia datele actualizate
            String updatedName = data.getStringExtra("updatedName");
            String updatedEmail = data.getStringExtra("updatedEmail");
            String updatedGender = data.getStringExtra("updatedGender");
            int updatedAge = data.getIntExtra("updatedAge", -1);

            // Actualizează UI-ul cu noile date
            userName.setText(updatedName);
            userEmail.setText(updatedEmail);
            userGender.setText(updatedGender);
            userAge.setText(updatedAge + " years old");
        }
    }

}
