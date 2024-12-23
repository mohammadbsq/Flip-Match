package com.example.flipmatch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flipmatch.model.GameModel;
import com.example.flipmatch.play.RoundOne;

public class PlayScreen extends AppCompatActivity {

    GameModel gameModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);
        gameModel = new GameModel();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundOne(gameModel)).commit();
    }
}