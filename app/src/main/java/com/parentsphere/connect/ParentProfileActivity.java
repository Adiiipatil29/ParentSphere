package com.parentsphere.connect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentProfileActivity extends AppCompatActivity {

    private TextView parentName, parentEmail, parentPhone, studentName, studentClass, studentYear;
    private Button btnUpdateInfo, btnLogout;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        // Initialize UI components
        parentName = findViewById(R.id.parentName);
        parentEmail = findViewById(R.id.parentEmail);
        parentPhone = findViewById(R.id.parentPhone);
        studentName = findViewById(R.id.studentName);
        studentClass = findViewById(R.id.studentClass);
        studentYear = findViewById(R.id.studentYear);
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
        btnLogout = findViewById(R.id.btnLogout);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Parents");

        // Load parent data
        loadParentProfile();

        // Logout functionality
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(ParentProfileActivity.this, ParentLoginActivity.class));
            finish();
        });

        // Update info functionality
        btnUpdateInfo.setOnClickListener(v -> Toast.makeText(this, "Update functionality not implemented yet", Toast.LENGTH_SHORT).show());
    }

    private void loadParentProfile() {
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DataSnapshot snapshot = task.getResult();
                String name = "Parent of " + snapshot.child("studentName").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String phone = snapshot.child("parentPhone").getValue(String.class);
                String student = snapshot.child("studentName").getValue(String.class);
                String studentClassValue = snapshot.child("studentClass").getValue(String.class);
                String year = snapshot.child("year").getValue(String.class);

                // Update UI
                parentName.setText(name);
                parentEmail.setText(email != null ? email : "Not Provided");
                parentPhone.setText(phone != null ? phone : "Not Provided");
                studentName.setText(student != null ? student : "Not Provided");
                studentClass.setText(studentClassValue != null ? studentClassValue : "Not Provided");
                studentYear.setText(year != null ? year : "Not Provided");
            } else {
                Toast.makeText(this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
