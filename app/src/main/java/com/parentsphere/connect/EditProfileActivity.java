package com.parentsphere.connect;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve the teacherId from intent
        String teacherId = getIntent().getStringExtra("teacherId");

        // References to EditText fields
        EditText editName = findViewById(R.id.editTeacherName);
        EditText editEmail = findViewById(R.id.editTeacherEmail);
        EditText editCourse = findViewById(R.id.editTeacherCourse);
        EditText editDepartment = findViewById(R.id.editTeacherDepartment);

        // Load existing details
        DatabaseReference teacherRef = FirebaseDatabase.getInstance().getReference("Teachers").child(teacherId);
        teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    editName.setText(dataSnapshot.child("name").getValue(String.class));
                    editEmail.setText(dataSnapshot.child("email").getValue(String.class));
                    editCourse.setText(dataSnapshot.child("course").getValue(String.class));
                    editDepartment.setText(dataSnapshot.child("department").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
            }
        });

        // Save updated details
        Button btnSave = findViewById(R.id.btnSaveProfile);
        btnSave.setOnClickListener(v -> {
            String newName = editName.getText().toString();
            String newEmail = editEmail.getText().toString();
            String newCourse = editCourse.getText().toString();
            String newDepartment = editDepartment.getText().toString();

            teacherRef.child("name").setValue(newName);
            teacherRef.child("email").setValue(newEmail);
            teacherRef.child("course").setValue(newCourse);
            teacherRef.child("department").setValue(newDepartment)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to the profile screen
                    })
                    .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show());
        });
    }
}
