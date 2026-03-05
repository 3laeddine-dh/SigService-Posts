package com.massrofi.sigservice_posts.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.massrofi.sigservice_posts.R;
import com.massrofi.sigservice_posts.databinding.ActivityMainBinding;
import com.massrofi.sigservice_posts.ui.list.PostListFragment;
import com.massrofi.sigservice_posts.ui.login.LoginActivity;
import com.massrofi.sigservice_posts.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Initialize SessionManager and Check Status
        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            // Redirect if not connected
            navigateToLogin();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 3. Initialize the first Fragment (Post List)
        if (savedInstanceState == null) {
            loadFragment(new PostListFragment(), false);
        }
    }

    /**
     * Helper to swap fragments
     * @param fragment The fragment to show
     * @param addToBackStack True if want to allow the "Back" button to return
     */
    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        var transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Prevent returning to MainActivity via Back button
    }

    // Handle Logout from any Fragment
    public void performLogout() {
        sessionManager.logout(); // Clear SharedPreferences
        navigateToLogin();
    }
}