package com.parentsphere.connect;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {

    private RecyclerView attendanceRecyclerView;
    private List<Attendance> attendanceList;
    private AttendanceAdapter attendanceAdapter;
    private DatabaseReference attendanceRef;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerView);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        attendanceRef = FirebaseDatabase.getInstance().getReference("Attendance");
        studentId = getIntent().getStringExtra("studentId");

        attendanceList = new ArrayList<>();
        attendanceAdapter = new AttendanceAdapter(attendanceList);
        attendanceRecyclerView.setAdapter(attendanceAdapter);

        loadAttendance();
    }

    private void loadAttendance() {
        attendanceRef.orderByChild("studentId").equalTo(studentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attendanceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Attendance attendance = dataSnapshot.getValue(Attendance.class);
                    if (attendance != null) {
                        attendanceList.add(attendance);
                    }
                }
                attendanceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAttendanceActivity.this, "Failed to load attendance: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
