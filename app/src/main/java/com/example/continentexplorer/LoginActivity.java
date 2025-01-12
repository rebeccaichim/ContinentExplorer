package com.example.continentexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.continentexplorer.model.User;
import com.example.continentexplorer.network.ApiService;
import com.example.continentexplorer.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView backArrow = findViewById(R.id.imageViewBack);
        backArrow.setOnClickListener(view -> {
            // Navighează înapoi
            onBackPressed();
        });

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });

        TextView textViewLogin = findViewById(R.id.textViewSignUp);
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish(); // Închide activitatea curentă pentru a evita suprapunerea
        });

        TextView forgotPasswordTextView = findViewById(R.id.textViewForgotPassword);
        forgotPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

    }

    private void loginUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        apiService.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extrage userId din răspuns
                    Long userId = response.body().getId();
                    Log.d("LOGIN_RESPONSE", "Logged in userId: " + userId);

                    // Creează un Intent către LearningActivity
                    Intent intent = new Intent(LoginActivity.this, LearningActivity.class);
                    intent.putExtra("userId", userId); // Trimite userId
                    startActivity(intent);
                    finish(); // Închide LoginActivity
                } else {
                    Log.d("LOGIN_ERROR", "Error: " + response.errorBody().toString());
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}