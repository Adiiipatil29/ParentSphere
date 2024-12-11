package com.parentsphere.connect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ParentLoginActivity extends AppCompatActivity {

    private EditText parentLoginUsername, parentLoginPassword;
    private Button btnParentLogin;
    private TextView forgotPassword, signUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        parentLoginUsername = findViewById(R.id.parentLoginUsername);
        parentLoginPassword = findViewById(R.id.parentLoginPassword);
        btnParentLogin = findViewById(R.id.btnParentLogin);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);

        auth = FirebaseAuth.getInstance();

        btnParentLogin.setOnClickListener(v -> loginParent());

        forgotPassword.setOnClickListener(v -> {
            String email = parentLoginUsername.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(ParentLoginActivity.this, "Enter your registered email to reset password", Toast.LENGTH_SHORT).show();
                return;
            }
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ParentLoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ParentLoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        signUp.setOnClickListener(v -> startActivity(new Intent(ParentLoginActivity.this, ParentSignupActivity.class)));
    }

    private void loginParent() {
        String username = parentLoginUsername.getText().toString().trim();
        String password = parentLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(ParentLoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(ParentLoginActivity.this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
