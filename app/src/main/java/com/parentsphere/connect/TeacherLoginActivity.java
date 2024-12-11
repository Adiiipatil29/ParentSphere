package com.parentsphere.connect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherLoginActivity extends AppCompatActivity {

    private EditText teacherLoginUsername, teacherLoginPassword;
    private Button btnTeacherLogin;
    private TextView forgotPassword, signUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        teacherLoginUsername = findViewById(R.id.teacherLoginUsername);
        teacherLoginPassword = findViewById(R.id.teacherLoginPassword);
        btnTeacherLogin = findViewById(R.id.btnTeacherLogin);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);

        auth = FirebaseAuth.getInstance();

        btnTeacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTeacher();
            }
        });

        forgotPassword.setOnClickListener(v -> {
            String email = teacherLoginUsername.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(TeacherLoginActivity.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(TeacherLoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TeacherLoginActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUp.setOnClickListener(v -> startActivity(new Intent(TeacherLoginActivity.this, TeacherSignupActivity.class)));
    }

    private void loginTeacher() {
        String username = teacherLoginUsername.getText().toString().trim();
        String password = teacherLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Retrieve the teacher's UID (user ID from Firebase Authentication)
                        String teacherId = auth.getCurrentUser().getUid();

                        // Pass the teacherId to the profile activity
                        Intent intent = new Intent(TeacherLoginActivity.this, HomeActivity.class);
                        intent.putExtra("teacherId", teacherId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TeacherLoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
