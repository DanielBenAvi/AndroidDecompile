package com.example.androiddecompile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_STATE = "EXTRA_STATE";
    private ImageButton[] arrows;
    int currentLevel = 0;
    int[] steps = {1, 1, 1, 2, 2, 2, 3, 3, 3};
    private boolean goodToGo = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String id = getIntent().getStringExtra(EXTRA_ID);
        if (id.length() == this.steps.length) {
            int i = 0;
            while (true) {
                int[] iArr = this.steps;
                if (i >= iArr.length) {
                    break;
                }
                iArr[i] = Integer.valueOf(String.valueOf(id.charAt(i))).intValue() % 4;
                i++;


            }
        }
        findViews();
        initViews();
    }

    public void arrowClicked(int direction) {
        Log.d("INFO", "direction: " + direction);
        Log.d("INFO", "currentLevel: " + currentLevel);
        Log.d("INFO", "steps[this.currentLevel]: " + steps[this.currentLevel]);

        // print steps
        for (int i = 0; i < steps.length; i++) {
            Log.d("INFO", "steps[" + i + "]: " + steps[i]);
        }

        if (this.goodToGo && direction != this.steps[this.currentLevel]) {
            Log.d("INFO", "error: wrong direction");
            this.goodToGo = false;
        }

//        Log.d("INFO", "arrowClicked: " + this.currentLevel);

        int i = this.currentLevel + 1;
        this.currentLevel = i;
        if (i >= this.steps.length) {
            finishGame();
        }
    }

    private void finishGame() {
        String state = getIntent().getStringExtra(EXTRA_STATE);
        if (this.goodToGo) {
            android.widget.Toast.makeText(this, "Survived in " + state, android.widget.Toast.LENGTH_LONG).show();
        } else {
            android.widget.Toast.makeText(this, "You Failed ", android.widget.Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private void initViews() {
        int i = 0;
        while (true) {
            ImageButton[] imageButtonArr = this.arrows;
            if (i < imageButtonArr.length) {
                final int finalI = i;
                imageButtonArr[i].setOnClickListener(v -> {
//                    Log.d("INFO", "clicked: " + finalI);
                    GameActivity.this.arrowClicked(finalI);
                });
                i++;
            } else {
                return;
            }
        }
    }

    private void findViews() {
        this.arrows = new ImageButton[]{(ImageButton) findViewById(R.id.game_BTN_left), (ImageButton) findViewById(R.id.game_BTN_right), (ImageButton) findViewById(R.id.game_BTN_up), (ImageButton) findViewById(R.id.game_BTN_down)};
    }
}