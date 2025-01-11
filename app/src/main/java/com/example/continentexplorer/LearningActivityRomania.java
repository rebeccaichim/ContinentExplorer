package com.example.continentexplorer;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.continentexplorer.dto.ScoreRequestRomania;
import com.example.continentexplorer.model.County;
import com.example.continentexplorer.network.ApiService;
import com.example.continentexplorer.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearningActivityRomania extends AppCompatActivity {
    private TextView guessTextView, attemptsTextView, pointsTextView;
    private WebView webView;
    private ApiService apiService;
    private County currentCounty;
    private int attemptsLeft = 3;
    private double totalScore = 0.0;
    private Long userId; // User ID va fi setat dinamic
    private Long gameId = 1L;
    private List<County> remainingCounties = new ArrayList<>();
    private Set<String> guessedCounties = new HashSet<>();
    private int attemptNumber = 1;

    private static final String TAG = "LearningActivityRomania";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_romania);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        guessTextView = findViewById(R.id.guessTextView);
        attemptsTextView = findViewById(R.id.attemptsTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        webView = findViewById(R.id.webview_romania_map);

        // Setează userId dintr-o sursă globală (ex: SharedPreferences, Intent extras, sau un manager centralizat)
        userId = getIntent().getLongExtra("userId", -1); // Exemplu: preluare din Intent
        if (userId == -1) {
            Toast.makeText(this, "User ID not found. Please login.", Toast.LENGTH_SHORT).show();
            finish(); // Închide activitatea dacă userId nu este setat
            return;
        }

        Log.d("LearningActivity", "User ID received: " + userId);


        ImageView backArrow = findViewById(R.id.backButton);
        backArrow.setOnClickListener(view -> {
            // Navighează înapoi
            onBackPressed();
        });

        loadMapInWebView();
        loadAllCounties();
    }

    private void loadMapInWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void onCountySelected(String countyId) {
                runOnUiThread(() -> handleCountySelection(countyId));
            }

            @JavascriptInterface
            public void log(String message) {
                Log.d(TAG, "JavaScript log: " + message);
            }
        }, "Android");

        webView.loadUrl("file:///android_res/raw/romania.svg");
    }

    private void loadAllCounties() {
        apiService.getAllCounties().enqueue(new Callback<List<County>>() {
            @Override
            public void onResponse(Call<List<County>> call, Response<List<County>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    remainingCounties.addAll(response.body());
                    loadRandomCounty();
                } else {
                    Toast.makeText(LearningActivityRomania.this, "Failed to load counties", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<County>> call, Throwable t) {
                Toast.makeText(LearningActivityRomania.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRandomCounty() {
        if (remainingCounties.isEmpty()) {
            Toast.makeText(this, "Game over! You've guessed all counties!", Toast.LENGTH_LONG).show();
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(remainingCounties.size());
        currentCounty = remainingCounties.get(randomIndex);
        remainingCounties.remove(randomIndex);

        guessTextView.setText("Guess: " + currentCounty.getCountyName());
        attemptsLeft = 3;
        attemptNumber = 1;
        attemptsTextView.setText("Attempts left: " + attemptsLeft);
        Log.d(TAG, "Random county loaded: " + currentCounty.getCountyName());
    }


    private void handleCountySelection(String countyId) {
        if (guessedCounties.contains(countyId)) return;

        boolean isCorrectGuess = countyId.equalsIgnoreCase("RO-" + currentCounty.getCountyAbbreviation());
        double pointsAwarded = 0.0;

        if (isCorrectGuess) {
            pointsAwarded = attemptsLeft == 3 ? 1.0 : (attemptsLeft == 2 ? 0.66 : 0.33);
            totalScore += pointsAwarded;
            pointsTextView.setText("Your Points: " + String.format("%.2f", totalScore) + "/42");

            guessedCounties.add(countyId);
            webView.evaluateJavascript("setColor('" + countyId + "', '#66cc33')", null);
            Toast.makeText(this, "Correct guess!", Toast.LENGTH_SHORT).show();

            saveScoreToDatabase(true, pointsAwarded);

            loadRandomCounty();
        } else {
            attemptsLeft--;
            attemptsTextView.setText("Attempts left: " + attemptsLeft);
            webView.evaluateJavascript("flashRed('" + countyId + "')", null);

            if (attemptsLeft <= 0) {
                guessedCounties.add(currentCounty.getCountyAbbreviation());
                webView.evaluateJavascript("setColor('RO-" + currentCounty.getCountyAbbreviation() + "', 'red')", null);
                saveScoreToDatabase(false, 0);
                Toast.makeText(this, "Out of attempts! Moving to next county.", Toast.LENGTH_LONG).show();

                loadRandomCounty();
            } else {
                Toast.makeText(this, "Incorrect guess. Try again.", Toast.LENGTH_SHORT).show();
                saveScoreToDatabase(false, 0);

                if (attemptNumber < 3) {
                    attemptNumber++;
                }
            }
        }
    }


    private void saveScoreToDatabase(boolean isCorrect, double pointsAwarded) {
        boolean isFinalAttempt = remainingCounties.isEmpty();
        double updatedTotalScore = totalScore;
        long attemptTime = System.currentTimeMillis();

        ScoreRequestRomania scoreRequest = new ScoreRequestRomania(
                userId, gameId, currentCounty.getCountyId(), attemptNumber, pointsAwarded, updatedTotalScore, isFinalAttempt, attemptTime
        );

        Log.d(TAG, "Saving score for userId " + userId + ": " + scoreRequest.toString());

        apiService.saveRomaniaScore(scoreRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Score saved successfully for userId " + userId);

                    if (isCorrect || attemptsLeft == 0) {
                        attemptNumber = 1;
                    }
                } else {
                    Log.e(TAG, "Failed to save score for userId " + userId + ". Code: " + response.code() + ", Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Failed to connect to server for saving score for userId " + userId, t);
            }
        });
    }


}
