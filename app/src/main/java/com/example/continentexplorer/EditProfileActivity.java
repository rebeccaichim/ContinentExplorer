package com.example.continentexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.continentexplorer.model.User;
import com.example.continentexplorer.network.ApiService;
import com.example.continentexplorer.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private EditText editName, editEmail, editGender, editAge;
    private Button saveButton;
    private ApiService apiService;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editGender = findViewById(R.id.editGender);
        editAge = findViewById(R.id.editAge);
        saveButton = findViewById(R.id.saveButton);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        userId = getIntent().getLongExtra("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID. Redirecting to login.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserProfile();

        saveButton.setOnClickListener(v -> saveUserProfile());
    }


    private void loadUserProfile() {
        apiService.getUserProfile(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    if (user.getFullName() != null) {
                        editName.setText(user.getFullName());
                    }
                    if (user.getEmail() != null) {
                        editEmail.setText(user.getEmail());
                    }
                    if (user.getGender() != null) {
                        editGender.setText(user.getGender());
                    }
                    if (user.getAge() != 0) {
                        editAge.setText(String.valueOf(user.getAge()));
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error loading profile: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveUserProfile() {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String gender = editGender.getText().toString();
        int age = Integer.parseInt(editAge.getText().toString());

        User updatedUser = new User(userId, name, email, gender, age);

        apiService.updateUserProfile(userId, updatedUser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedName", name);
                    resultIntent.putExtra("updatedEmail", email);
                    resultIntent.putExtra("updatedGender", gender);
                    resultIntent.putExtra("updatedAge", age);
                    setResult(RESULT_OK, resultIntent);

                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
