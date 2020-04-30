package com.example.foodbuddiez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainSplash extends AppCompatActivity implements View.OnClickListener{

    Button loginButton;
    Button signupButton;
    Button bypassBUtton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        loginButton = (this.findViewById(R.id.login_button));
        signupButton = (this.findViewById(R.id.signup_button));
        bypassBUtton = this.findViewById(R.id.bypass_button);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        bypassBUtton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                Intent loginIntent = new Intent(this, LoginPage.class);
                this.startActivity(loginIntent);
                break;
            case R.id.signup_button:
                Intent signupIntent = new Intent(this, SignupPage.class);
                this.startActivity(signupIntent);
                break;
            case R.id.bypass_button:
                Intent bypassIntent = new Intent(this, Homescreen.class);
                this.startActivity(bypassIntent);
                break;
            default:
                break;

        }
    }
}
