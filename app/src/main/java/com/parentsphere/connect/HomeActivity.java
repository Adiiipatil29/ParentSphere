package com.parentsphere.connect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class HomeActivity extends AppCompatActivity {

    private Button dashboardButton, profileButton, notificationsButton;
    private FirebaseAuth auth;
    private boolean isTeacher = false;
    private DatabaseReference databaseRef;

    private static final String PARENTS_PATH = "Parents";
    private static final String TEACHERS_PATH = "Teachers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dashboardButton = findViewById(R.id.dashboardButton);
        profileButton = findViewById(R.id.profileButton);
        notificationsButton = findViewById(R.id.notificationButton);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        determineUserRole(userId);

        dashboardButton.setOnClickListener(v -> openDashboard());
        profileButton.setOnClickListener(v -> openProfile());
        notificationsButton.setOnClickListener(v -> openNotifications());
    }

    private void determineUserRole(String userId) {
        databaseRef.child(PARENTS_PATH).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isTeacher = false;
                    Toast.makeText(HomeActivity.this, "Welcome, Parent!", Toast.LENGTH_SHORT).show();
                } else {
                    databaseRef.child(TEACHERS_PATH).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                isTeacher = true;
                                Toast.makeText(HomeActivity.this, "Welcome, Teacher!", Toast.LENGTH_SHORT).show();
                            } else {
                                showErrorAndLogout("User role not recognized. Logging out...");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            showErrorAndLogout("Database error: " + error.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                showErrorAndLogout("Database error: " + error.getMessage());
            }
        });
    }

    private void showErrorAndLogout(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
        auth.signOut();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

    private void openDashboard() {
        Intent intent;
        if (isTeacher) {
            intent = new Intent(HomeActivity.this, TeacherDashboardActivity.class);
        } else {
            intent = new Intent(HomeActivity.this, ParentDashboardActivity.class);
        }
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent;
        if (isTeacher) {
            intent = new Intent(HomeActivity.this, TeacherProfileActivity.class);
        } else {
            intent = new Intent(HomeActivity.this, ParentProfileActivity.class);
        }
        startActivity(intent);
    }

    private void openNotifications() {
        startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
    }
}
