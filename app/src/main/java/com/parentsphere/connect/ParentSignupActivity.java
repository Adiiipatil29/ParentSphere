package com.parentsphere.connect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentSignupActivity extends AppCompatActivity {

    private EditText parentEmail, parentPassword, studentName, studentClass, yearText, parentPhone;
    private Button btnParentSignup;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_signup);

        // Initialize EditTexts
        parentEmail = findViewById(R.id.parentUsername);
        parentPassword = findViewById(R.id.parentPassword);
        studentName = findViewById(R.id.studentName);
        studentClass = findViewById(R.id.studentClass);
        yearText = findViewById(R.id.yearText);
        parentPhone = findViewById(R.id.parentPhone); // New phone number field
        btnParentSignup = findViewById(R.id.btnParentSignup);

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Parents");

        // Set up button click listener
        btnParentSignup.setOnClickListener(v -> registerParent());
    }

    private void registerParent() {
        // Fetch values from input fields
        String email = parentEmail.getText().toString().trim();
        String password = parentPassword.getText().toString().trim();
        String student = studentName.getText().toString().trim();
        String studentClassInput = studentClass.getText().toString().trim();
        String year = yearText.getText().toString().trim();
        String phone = parentPhone.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(student) ||
                TextUtils.isEmpty(studentClassInput) || TextUtils.isEmpty(year) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && auth.getCurrentUser() != null) {
                        saveUserData(student, studentClassInput, year, email, phone);
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserData(String student, String studentClassInput, String year, String email, String phone) {
        String userId = auth.getCurrentUser().getUid();
        Parent parent = new Parent("Parent of " + student, email, phone, student, studentClassInput, year);

        // Save user data in the Firebase Realtime Database
        databaseReference.child(userId).setValue(parent)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ParentSignupActivity.this, "Parent Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ParentSignupActivity.this, ParentLoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
