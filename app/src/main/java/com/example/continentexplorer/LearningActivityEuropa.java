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

import com.example.continentexplorer.dto.ScoreRequestEuropa;
import com.example.continentexplorer.model.Country;
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

public class LearningActivityEuropa extends AppCompatActivity {
    private TextView guessTextView, attemptsTextView, pointsTextView;
    private WebView webView;
    private ApiService apiService;
    private Country currentCountry;
    private int attemptsLeft = 3;
    private double totalScore = 0.0;
    private Long userId;
    private Long gameId = 2L;
    private List<Country> remainingCountries = new ArrayList<>();
    private Set<String> guessedCountries = new HashSet<>();
    private int attemptNumber = 1;

    private static final String TAG = "LearningActivityEuropa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_europa);

        userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            finish();
            return;
        }

        ImageView backArrow = findViewById(R.id.backButton);
        backArrow.setOnClickListener(view -> {
            onBackPressed();
        });


        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        guessTextView = findViewById(R.id.guessTextView);
        attemptsTextView = findViewById(R.id.attemptsTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        webView = findViewById(R.id.webview_europe_map);

        loadMapInWebView();
        loadAllCountries();
    }

    private void loadMapInWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void onCountrySelected(String countryId) {
                runOnUiThread(() -> handleCountrySelection(countryId));
            }

            @JavascriptInterface
            public void log(String message) {
                Log.d(TAG, "JavaScript log: " + message);
            }
        }, "Android");

        webView.loadUrl("file:///android_res/raw/europe.svg");
    }

    private void loadAllCountries() {
        apiService.getAllCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    remainingCountries.addAll(response.body());
                    loadRandomCountry();
                } else {
                    Toast.makeText(LearningActivityEuropa.this, "Failed to load countries", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(LearningActivityEuropa.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRandomCountry() {
        if (remainingCountries.isEmpty()) {
            saveScoreToDatabase(true, 0.0);
            Toast.makeText(this, "Game over! You've guessed all countries!", Toast.LENGTH_LONG).show();
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(remainingCountries.size());
        currentCountry = remainingCountries.get(randomIndex);
        remainingCountries.remove(randomIndex);

        guessTextView.setText("Guess: " + currentCountry.getCountryName());
        attemptsLeft = 3;
        attemptNumber = 1;
        attemptsTextView.setText("Attempts left: " + attemptsLeft);
    }

    private void handleCountrySelection(String countryId) {
        if (guessedCountries.contains(countryId)) return;

        boolean isCorrectGuess = countryId.equalsIgnoreCase("EU-" + currentCountry.getCountryAbbreviation());
        double pointsAwarded = 0.0;

        if (isCorrectGuess) {
            pointsAwarded = attemptsLeft == 3 ? 1.0 : (attemptsLeft == 2 ? 0.66 : 0.33);
            totalScore += pointsAwarded;
            pointsTextView.setText("Your Points: " + String.format("%.2f", totalScore) + "/51");

            guessedCountries.add(countryId);
            webView.evaluateJavascript("setColor('" + countryId + "', '#66cc33')", null);
            Toast.makeText(this, "Correct guess!", Toast.LENGTH_SHORT).show();

            saveScoreToDatabase(true, pointsAwarded);

            loadRandomCountry();
        } else {
            attemptsLeft--;
            attemptsTextView.setText("Attempts left: " + attemptsLeft);
            webView.evaluateJavascript("flashRed('" + countryId + "')", null);

            if (attemptsLeft <= 0) {
                guessedCountries.add(currentCountry.getCountryAbbreviation());
                webView.evaluateJavascript("setColor('EU-" + currentCountry.getCountryAbbreviation() + "', 'red')", null);
                saveScoreToDatabase(false, 0);
                Toast.makeText(this, "Out of attempts! Moving to next country.", Toast.LENGTH_LONG).show();

                loadRandomCountry();
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
        boolean isFinalAttempt = remainingCountries.isEmpty();
        double updatedTotalScore = totalScore;

        long attemptTime = System.currentTimeMillis();

        ScoreRequestEuropa scoreRequest = new ScoreRequestEuropa(
                userId, gameId, currentCountry.getCountryId(), attemptNumber, pointsAwarded, updatedTotalScore, isFinalAttempt, attemptTime
        );

        apiService.saveEuropaScore(scoreRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if (isCorrect || attemptsLeft == 0) {
                        attemptNumber = 1;
                    }

                } else {
                    Log.e(TAG, "Failed to save score. Code: " + response.code() +
                            ", Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Failed to connect to server for saving score", t);
            }
        });
    }
}
