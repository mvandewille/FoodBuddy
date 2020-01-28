package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView goalNumberDisplay;
    TextView numberDisplay;
    Button divideButton;
    Button multiplyButton;
    Button addButton;
    Button subtractButton;
    Button floorButton;
    Button sqrtButton;
    Button newGameButton;
    Button movePagesButton;

    int goal_number;
    double current_number;
    int moveCount = 0;
    int originalGoalNumber;
    double originalCurrentNumber;
    boolean resetFlag = false;
    boolean newGameFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goalNumberDisplay = findViewById(R.id.goal_number_display);
        numberDisplay = findViewById(R.id.number_display);

        divideButton = findViewById(R.id.button_dividing);
        multiplyButton = findViewById(R.id.button_multiplying);
        addButton = findViewById(R.id.button_adding);
        subtractButton = findViewById(R.id.button_subtracting);
        floorButton = findViewById(R.id.button_floor);
        sqrtButton = findViewById(R.id.button_square_root);
        newGameButton = findViewById(R.id.new_game_button);
        movePagesButton = findViewById(R.id.goto_secondaryScreen);

        divideButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        floorButton.setOnClickListener(this);
        sqrtButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);
        movePagesButton.setOnClickListener(this);


        resetFlag = getIntent().getBooleanExtra("RESET_FLAG", false);
        newGameFlag = getIntent().getBooleanExtra("NEW_GAME_FLAG",true);
        moveCount = getIntent().getIntExtra("MOVE_COUNT", 0);
        goal_number = getIntent().getIntExtra("GOAL_NUM", 0);
        current_number = getIntent().getDoubleExtra("CUR_NUM", 0.0);
        originalGoalNumber = getIntent().getIntExtra("ORIG_GOAL_NUM", 0);
        originalCurrentNumber = getIntent().getDoubleExtra("ORIG_CUR_NUM", 0.0);

        if(resetFlag){
            goal_number = originalGoalNumber;
            current_number = originalCurrentNumber;
            goalNumberDisplay.setText(Integer.toString(goal_number));
            numberDisplay.setText(Double.toString(current_number));
            resetFlag = false;
        }
        else if(newGameFlag) {
            randomNumber();
        }
        else{
            goalNumberDisplay.setText(Integer.toString(goal_number));
            numberDisplay.setText(Double.toString(current_number));
        }


    }

    public void randomNumber() {
        Random random = new Random();
        goal_number = random.nextInt(999);
        originalGoalNumber = goal_number;
        String goalText = Integer.toString(goal_number);
        goalNumberDisplay.setText(goalText);

        current_number = random.nextInt(goal_number);
        originalCurrentNumber = current_number;
        String currentText = Double.toString(current_number);
        numberDisplay.setText(currentText);
        newGameFlag = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_dividing:
                current_number = current_number / 5;
                break;
            case R.id.button_multiplying:
                current_number = current_number * 3;
                break;
            case R.id.button_adding:
                current_number = current_number + 7;
                break;
            case R.id.button_subtracting:
                current_number = current_number - 2;
                break;
            case R.id.button_floor:
                current_number = Math.floor(current_number);
                break;
            case R.id.button_square_root:
                current_number = Math.sqrt(current_number);
                break;
            case R.id.new_game_button:
                moveCount = 0;
                randomNumber();
                newGameButton.setVisibility(View.INVISIBLE);      //there's a small chance it will randomly set the two numbers equal at the start, but its too small to consider
                break;
            case R.id.goto_secondaryScreen:
                Intent intent = new Intent(v.getContext(), SecondaryActivity.class);
                intent.putExtra("MOVE_COUNT", moveCount);
                intent.putExtra("GOAL_NUM", goal_number);
                intent.putExtra("CUR_NUM", current_number);
                intent.putExtra("ORIG_GOAL_NUM", originalGoalNumber);
                intent.putExtra("ORIG_CUR_NUM", originalCurrentNumber);
                startActivityForResult(intent, 0);
                break;

            default:
                break;
        }
        moveCount++;
        String currentText = Double.toString(current_number);
        numberDisplay.setText(currentText);
        if(Double.compare(goal_number, current_number) == 0){
            newGameButton.setVisibility(View.VISIBLE);
        }

    }
}
