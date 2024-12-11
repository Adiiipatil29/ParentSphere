package com.parentsphere.connect;

public class Notification {
    private String text;
    private String timestamp;

    public Notification(String text, String timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
