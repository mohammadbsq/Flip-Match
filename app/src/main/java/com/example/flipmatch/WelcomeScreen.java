package com.example.flipmatch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {
    ImageButton playButton, highScoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        playButton = findViewById(R.id.play_button);
        highScoreButton = findViewById(R.id.high_score_button);

        playButton.setOnClickListener(v -> {
            Intent i = new Intent(WelcomeScreen.this, PlayScreen.class);
            startActivity(i);
        });

        highScoreButton.setOnClickListener(v -> {
            Intent i = new Intent(WelcomeScreen.this, HighScoreScreen.class);
            startActivity(i);
        });
    }
}