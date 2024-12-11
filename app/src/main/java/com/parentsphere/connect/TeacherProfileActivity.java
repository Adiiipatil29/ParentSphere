package com.parentsphere.connect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherProfileActivity extends AppCompatActivity {

    private TextView teacherName, teacherEmail, teacherCourse, teacherDepartment;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        teacherName = findViewById(R.id.teacherName);
        teacherEmail = findViewById(R.id.teacherEmail);
        teacherCourse = findViewById(R.id.teacherCourse);
        teacherDepartment = findViewById(R.id.teacherDepartment);
        Button backButton = findViewById(R.id.btnBack);

        String teacherId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("Teachers").child(teacherId);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    teacherName.setText(dataSnapshot.child("name").getValue(String.class));
                    teacherEmail.setText(dataSnapshot.child("email").getValue(String.class));
                    teacherCourse.setText(dataSnapshot.child("course").getValue(String.class));
                    teacherDepartment.setText(dataSnapshot.child("department").getValue(String.class));
                } else {
                    Toast.makeText(TeacherProfileActivity.this, "Profile data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TeacherProfileActivity.this, "Error loading profile", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
