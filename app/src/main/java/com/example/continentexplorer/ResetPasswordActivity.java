package com.example.continentexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.continentexplorer.dto.ResetPasswordRequest;
import com.example.continentexplorer.network.ApiService;
import com.example.continentexplorer.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText;
    private Button resetPasswordButton;
    private ApiService apiService;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPasswordEditText = findViewById(R.id.editTextNewPassword);
        resetPasswordButton = findViewById(R.id.buttonResetPassword);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        email = getIntent().getStringExtra("EMAIL");

        resetPasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString().trim();

            if (newPassword.isEmpty()) {
                Toast.makeText(ResetPasswordActivity.this, "Please enter a new password", Toast.LENGTH_SHORT).show();
                return;
            }

            resetPassword(email, newPassword);
        });
    }

    private void resetPassword(String email, String newPassword) {
        ResetPasswordRequest request = new ResetPasswordRequest(email, newPassword);

        apiService.resetPassword(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();

                    // Redirecționează către LoginActivity
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    finish(); // Închide activitatea curentă
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
