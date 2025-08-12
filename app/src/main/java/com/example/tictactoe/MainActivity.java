package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0; // 0: First Player, 1: Second Player
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 2: Unplayed

    int[][] winningPositions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };
    boolean gameActive = true;

    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        TextView status = findViewById(R.id.status);

        // Reset the game if it's inactive
        if (!gameActive) {
            gameReset(view);
            return;
        }

        int tappedImage = Integer.parseInt(img.getTag().toString()) - 1;

        // Check if the cell is already occupied
        if (gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer;

            img.setTranslationY(-1000f);
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.cross);
                activePlayer = 1;
                status.setText("2nd Player's turn - Tap on a grid");
            } else {
                img.setImageResource(R.drawable.oval);
                activePlayer = 0;
                status.setText("1st Player's turn - Tap on a grid");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }

        // Check for a winner
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {

                String winnerStr;
                gameActive = false;
                if (gameState[winningPosition[0]] == 0) {
                    winnerStr = "Congratulations! 1st Player Won";
                } else {
                    winnerStr = "Congratulations! 2nd Player Won";
                }
                status.setText(winnerStr);
                status.setTextColor(getResources().getColor(R.color.red));
                return;
            }
        }

        // Check for a draw
        boolean draw = true;
        for (int state : gameState) {
            if (state == 2) {
                draw = false;
                break;
            }
        }

        if (draw) {
            status.setText("It's a draw! Click on restart.");
            status.setTextColor(getResources().getColor(R.color.black)); // Optional: Add a different color for draw
        }
    }

    public void gameReset(View view) {
        TextView status = findViewById(R.id.status);
        status.setTextColor(getResources().getColor(R.color.blue));
        status.setText("New Game Started!");
        gameActive = true;
        activePlayer = 0;
        Arrays.fill(gameState, 2);

        // Reset all the image views
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView9)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView10)).setImageResource(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
