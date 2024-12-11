package com.parentsphere.connect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeacherDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Button takeAttendanceButton = findViewById(R.id.takeAttendanceButton);
        Button updatePerformanceButton = findViewById(R.id.updatePerformanceButton);
        Button messageParentsButton = findViewById(R.id.messageParentsButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button generateClassCodeButton = findViewById(R.id.generateClassCodeButton);

        takeAttendanceButton.setOnClickListener(v -> startActivity(new Intent(this, TakeAttendanceActivity.class)));

        updatePerformanceButton.setOnClickListener(v ->
                Toast.makeText(this, "Update Performance feature coming soon!", Toast.LENGTH_SHORT).show());

        messageParentsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("isTeacher", true);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        generateClassCodeButton.setOnClickListener(v -> generateClassroomCode());
    }

    private void generateClassroomCode() {
        String classCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        DatabaseReference classRef = FirebaseDatabase.getInstance().getReference("Classrooms");
        String classId = classRef.push().getKey();

        if (classId != null) {
            Map<String, Object> classData = new HashMap<>();
            classData.put("teacherId", FirebaseAuth.getInstance().getCurrentUser().getUid());
            classData.put("classCode", classCode);

            classRef.child(classId).setValue(classData)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Classroom Code: " + classCode, Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to generate classroom code", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Error generating classroom code", Toast.LENGTH_SHORT).show();
        }
    }
}
