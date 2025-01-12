package com.example.continentexplorer;

import android.annotation.SuppressLint;
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
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullnameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText genderEditText;
    private EditText ageEditText;
    private Button signUpButton;

    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        ImageView backArrow = findViewById(R.id.imageViewBack);
        backArrow.setOnClickListener(view -> {
            // Navighează înapoi
            onBackPressed();
        });

        fullnameEditText = findViewById(R.id.editTextFullName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        genderEditText = findViewById(R.id.editTextGender);
        ageEditText = findViewById(R.id.editTextAge);
        signUpButton = findViewById(R.id.buttonCreateAccount);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        signUpButton.setOnClickListener(v -> {
            String fullname = fullnameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String gender = genderEditText.getText().toString().trim();
            String ageText = ageEditText.getText().toString().trim();

            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    int age = Integer.parseInt(ageText);

                    User user = new User();
                    user.setFullName(fullname);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setGender(gender);
                    user.setAge(age);

                    Log.d("REQUEST_BODY", new Gson().toJson(user));

                    registerUser(user);

                } catch (NumberFormatException e) {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Închide activitatea curentă pentru a evita suprapunerea
        });

    }

    private void registerUser(User user) {
        apiService.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        Log.d("SIGNUP_ERROR", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(SignUpActivity.this, "Failed to create account. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
