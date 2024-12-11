package com.parentsphere.connect;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TakeAttendanceActivity extends AppCompatActivity {

    private RecyclerView studentRecyclerView;
    private EditText searchStudent;
    private DatePicker datePicker;
    private Button saveAttendanceButton;
    private List<Attendance> attendanceList, filteredList;
    private AttendanceAdapter attendanceAdapter;
    private DatabaseReference attendanceRef;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        // Initialize UI components
        studentRecyclerView = findViewById(R.id.studentRecyclerView);
        searchStudent = findViewById(R.id.searchStudent);
        datePicker = findViewById(R.id.datePicker);
        saveAttendanceButton = findViewById(R.id.saveAttendanceButton);

        // Firebase Database reference
        attendanceRef = FirebaseDatabase.getInstance().getReference("Attendance");

        // Initialize attendance lists
        attendanceList = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Set up RecyclerView
        attendanceAdapter = new AttendanceAdapter(filteredList);
        studentRecyclerView.setAdapter(attendanceAdapter);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load student data
        loadStudentList();

        // Set up search functionality
        searchStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterStudents(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Save attendance
        saveAttendanceButton.setOnClickListener(v -> saveAttendance());
    }

    private void loadStudentList() {
        selectedDate = getDateFromPicker(); // Get initial date
        attendanceList.add(new Attendance("1", "John Doe", selectedDate, false));
        attendanceList.add(new Attendance("2", "Jane Smith", selectedDate, false));

        // Initially, show all students
        filteredList.addAll(attendanceList);
        attendanceAdapter.notifyDataSetChanged();
    }

    private String getDateFromPicker() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
    }

    private void filterStudents(String query) {
        filteredList.clear();
        for (Attendance attendance : attendanceList) {
            if (attendance.getStudentName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(attendance);
            }
        }
        attendanceAdapter.notifyDataSetChanged();
    }

    private void saveAttendance() {
        // Update the selected date for all attendance records
        selectedDate = getDateFromPicker();
        for (Attendance attendance : filteredList) {
            attendance.setDate(selectedDate);
            String id = attendanceRef.push().getKey();
            if (id != null) {
                attendanceRef.child(id).setValue(attendance);
            }
        }

        Toast.makeText(this, "Attendance saved for " + selectedDate, Toast.LENGTH_SHORT).show();
        finish();
    }
}
