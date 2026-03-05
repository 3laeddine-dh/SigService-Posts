package com.massrofi.sigservice_posts.ui.login;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.massrofi.sigservice_posts.R;
import com.massrofi.sigservice_posts.databinding.ActivityLoginBinding;
import com.massrofi.sigservice_posts.ui.main.MainActivity;
import com.massrofi.sigservice_posts.utils.SessionManager;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        // Initialize UI states
        setupTextWatchers();

        binding.btnConnect.setOnClickListener(v -> handleLogin());
    }

    private void setupTextWatchers() {
        TextWatcher loginWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String login = binding.etLogin.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();

                // 1. Enable/Disable button based on input
                boolean isEnabled = !login.isEmpty() && !password.isEmpty();
                binding.btnConnect.setEnabled(isEnabled);

                // 2. Adjust Alpha for visual feedback
                binding.btnConnect.setAlpha(isEnabled ? 1.0f : 0.5f);

                // 3. Clear errors as soon as user starts typing
                if (!login.isEmpty()) binding.loginLayout.setError(null);
                if (!password.isEmpty()) binding.passwordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        binding.etLogin.addTextChangedListener(loginWatcher);
        binding.etPassword.addTextChangedListener(loginWatcher);
    }

    private void handleLogin() {
        String login = binding.etLogin.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Extra validation (Double-check)
        if (login.isEmpty() || password.isEmpty()) {
            if (login.isEmpty()) binding.loginLayout.setError("Login required");
            if (password.isEmpty()) binding.passwordLayout.setError("Password required");
            return;
        }

        // Show a simple loading state (Optional Pro touch)
        binding.btnConnect.setText("Connecting...");
        binding.btnConnect.setEnabled(false);

        // Save status in SharedPreferences
        sessionManager.setLogin(true);

        // Redirect to Main
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        // Remove from backstack so user can't "Go Back" to login
        finish();
    }
}