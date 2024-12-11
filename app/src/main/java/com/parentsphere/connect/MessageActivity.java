package com.parentsphere.connect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageActivity extends AppCompatActivity {

    private EditText messageInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();

            if (!message.isEmpty()) {
                String timestamp = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                NotificationManager.getInstance().addNotification(message, timestamp);

                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
                messageInput.setText("");
            } else {
                Toast.makeText(this, "Message cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
