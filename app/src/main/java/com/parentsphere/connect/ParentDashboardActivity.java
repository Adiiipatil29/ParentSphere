package com.parentsphere.connect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ParentDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        Button viewAttendanceButton = findViewById(R.id.viewAttendanceButton);
        Button viewPerformanceButton = findViewById(R.id.viewPerformanceButton);
        Button messageTeachersButton = findViewById(R.id.messageTeachersButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        // View Attendance
        viewAttendanceButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewAttendanceActivity.class);
            startActivity(intent);
        });

        // View Performance
        viewPerformanceButton.setOnClickListener(v -> {
            Toast.makeText(this, "View Performance selected", Toast.LENGTH_SHORT).show();
            // Add functionality
        });

        // Message Teachers
        messageTeachersButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("isTeacher", false);
            startActivity(intent);
        });

        // Logout
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
