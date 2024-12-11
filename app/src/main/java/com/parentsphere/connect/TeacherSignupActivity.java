package com.parentsphere.connect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherSignupActivity extends AppCompatActivity {

    private EditText teacherUsername, teacherPassword, teacherName, courseName, department;
    private Button btnTeacherSignup;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signup);

        teacherUsername = findViewById(R.id.teacherUsername);
        teacherPassword = findViewById(R.id.teacherPassword);
        teacherName = findViewById(R.id.teacherName);
        courseName = findViewById(R.id.courseName);
        department = findViewById(R.id.department);
        btnTeacherSignup = findViewById(R.id.btnTeacherSignup);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");

        btnTeacherSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTeacher();
            }
        });
    }

    private void registerTeacher() {
        String username = teacherUsername.getText().toString().trim();
        String password = teacherPassword.getText().toString().trim();
        String name = teacherName.getText().toString().trim();
        String course = courseName.getText().toString().trim();
        String dept = department.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(name) || TextUtils.isEmpty(course) || TextUtils.isEmpty(dept)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Teacher teacher = new Teacher(name, course, dept);
                        databaseReference.child(auth.getCurrentUser().getUid()).setValue(teacher)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(TeacherSignupActivity.this, "Teacher Registered Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(TeacherSignupActivity.this, "Failed to save data: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(TeacherSignupActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
