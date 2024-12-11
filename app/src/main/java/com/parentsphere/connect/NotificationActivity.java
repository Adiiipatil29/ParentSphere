package com.parentsphere.connect;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        LinearLayout notificationList = findViewById(R.id.notificationList);

        List<Notification> notifications = NotificationManager.getInstance().getNotifications();

        // Dynamically populate the notification list
        for (Notification notification : notifications) {
            TextView notificationView = new TextView(this);
            notificationView.setText(notification.getText() + " (" + notification.getTimestamp() + ")");
            notificationView.setPadding(16, 16, 16, 16);
            notificationView.setBackgroundResource(android.R.color.white);
            notificationView.setTextSize(16);
            notificationView.setTextColor(getResources().getColor(android.R.color.black));
            notificationView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            notificationView.setElevation(4);

            notificationList.addView(notificationView);
        }
    }
}
