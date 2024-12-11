package com.parentsphere.connect;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static NotificationManager instance;
    private final List<Notification> notifications;

    private NotificationManager() {
        notifications = new ArrayList<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void addNotification(String text, String timestamp) {
        notifications.add(new Notification(text, timestamp));
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
