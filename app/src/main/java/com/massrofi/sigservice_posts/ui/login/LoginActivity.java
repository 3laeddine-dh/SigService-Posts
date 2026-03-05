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

        binding.btnConnect.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String login = binding.etLogin.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Instruction Logic: If fields are not empty -> connect
        if (!login.isEmpty() && !password.isEmpty()) {
            // Save status in SharedPreferences
            sessionManager.setLogin(true);

            // Redirect to Main (which hosts the Post List)
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Instruction requirement: remove login from backstack
        } else {
            // Basic UI feedback
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            if (login.isEmpty()) binding.loginLayout.setError("Required");
            if (password.isEmpty()) binding.passwordLayout.setError("Required");
        }
    }
}