package com.example.morsecodetranslator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Sprawdź czy użytkownik jest już zalogowany
        new Handler(Looper.getMainLooper()).postDelayed(this::checkLoginState, 1000);

        // Inicjalizacja pól
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        Button googleSignInButton = findViewById(R.id.googleSignInButton);

        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> handleRegistration());
        googleSignInButton.setOnClickListener(v -> startGoogleSignIn());
    }

    private void checkLoginState() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startMainActivity();
        }
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInput(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startMainActivity();
                        } else {
                            String errorMessage = task.getException() != null ?
                                    task.getException().getMessage() :
                                    "Błąd logowania";
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void handleRegistration() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInput(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startMainActivity();
                        } else {
                            String errorMessage = task.getException() != null ?
                                    task.getException().getMessage() :
                                    "Błąd rejestracji";
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void startGoogleSignIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_MorseCodeTranslator)
                .setLogo(R.drawable.ic_launcher_foreground)
                .setIsSmartLockEnabled(false)
                .build();

        signInLauncher.launch(signInIntent);
    }

    private boolean validateInput(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            emailLayout.setError("Email jest wymagany");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Hasło jest wymagane");
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError("Hasło musi mieć co najmniej 6 znaków");
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        return isValid;
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startMainActivity();
            } else {
                Toast.makeText(this, "Błąd logowania: Nie udało się pobrać danych użytkownika", Toast.LENGTH_LONG).show();
            }
        } else {
            if (response != null) {
                String errorMessage = response.getError() != null ?
                        response.getError().getMessage() :
                        "Nieznany błąd logowania";
                Toast.makeText(this, "Błąd logowania: " + errorMessage, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Logowanie anulowane", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
} 