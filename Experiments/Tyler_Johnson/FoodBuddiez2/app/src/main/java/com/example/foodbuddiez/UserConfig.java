package com.example.foodbuddiez;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserConfig extends AppCompatActivity implements View.OnClickListener {

    Button configSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config);
        configSubmit = findViewById(R.id.user_config_submit);

    }

    @Override
    public void onClick(View v) {
        //TODO confirm all fields have been entered, then go to main screen
    }
}
