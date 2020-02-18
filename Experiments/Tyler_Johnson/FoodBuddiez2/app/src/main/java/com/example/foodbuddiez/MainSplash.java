package com.example.foodbuddiez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainSplash extends AppCompatActivity implements View.OnClickListener{

    Button loginButton;
    Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        loginButton = (this.findViewById(R.id.login_button));
        signupButton = (this.findViewById(R.id.signup_button));

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                Intent intent = new Intent(this, LoginPage.class);
                this.startActivity(intent);
                break;
            case R.id.signup_button:
                Intent intent2 = new Intent(this, SignupPage.class);
                this.startActivity(intent2);
                break;
        }
    }
}
