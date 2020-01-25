package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView goalNumberDisplay;
    TextView numberDisplay;
    int goal_number;
    double current_number;

    Button divideButton;
    Button multiplyButton;
    Button addButton;
    Button subtractButton;
    Button floorButton;
    Button sqrtButton;
    Button resetButton;

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
        resetButton = findViewById(R.id.reset_button);

        divideButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        floorButton.setOnClickListener(this);
        sqrtButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        randomNumber();
    }

    public void randomNumber() {
        Random random = new Random();
        goal_number = random.nextInt(999);
        String goalText = Integer.toString(goal_number);
        goalNumberDisplay.setText(goalText);

        current_number = random.nextInt(goal_number);
        String currentText = Double.toString(current_number);
        numberDisplay.setText(currentText);
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
            case R.id.reset_button:
                randomNumber();
                resetButton.setVisibility(View.INVISIBLE);      //there's a small chance it will randomly set the two numbers equal at the start, but its too small to consider
                break;
            default:
                break;
        }
        String currentText = Double.toString(current_number);
        numberDisplay.setText(currentText);
        if(Double.compare(goal_number, current_number) == 0){
            resetButton.setVisibility(View.VISIBLE);
        }

    }
}
