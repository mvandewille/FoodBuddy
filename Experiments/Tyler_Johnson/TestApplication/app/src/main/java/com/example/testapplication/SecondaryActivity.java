package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity implements View.OnClickListener {

    TextView countDisplay;
    Button resetButton;
    Button mainPageButton;

    int moveCount;
    int goalNum;
    double curNum;
    int origGoalNum;
    double origCurNum;

    boolean resetFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_layout);
        countDisplay = findViewById(R.id.moves_counter);
        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        mainPageButton = findViewById(R.id.goto_main_page);
        mainPageButton.setOnClickListener(this);

        moveCount = getIntent().getIntExtra("MOVE_COUNT", 0);
        goalNum = getIntent().getIntExtra("GOAL_NUM", 0);
        curNum = getIntent().getDoubleExtra("CUR_NUM", 0.0);
        origGoalNum = getIntent().getIntExtra("ORIG_GOAL_NUM", 0);
        origCurNum = getIntent().getDoubleExtra("ORIG_CUR_NUM", 0.0);

        countDisplay.setText(Integer.toString(moveCount));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_button:
                resetFlag = true;
                moveCount = 0;
                countDisplay.setText(Integer.toString(moveCount));
                break;
            case R.id.goto_main_page:
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("RESET_FLAG", resetFlag);
                intent.putExtra("NEW_GAME_FLAG", false);
                intent.putExtra("MOVE_COUNT", moveCount);
                intent.putExtra("GOAL_NUM", goalNum);
                intent.putExtra("CUR_NUM", curNum);
                intent.putExtra("ORIG_GOAL_NUM", origGoalNum);
                intent.putExtra("ORIG_CUR_NUM", origCurNum);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }
}
