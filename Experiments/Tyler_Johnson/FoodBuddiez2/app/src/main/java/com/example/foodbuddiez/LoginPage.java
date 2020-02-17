package com.example.foodbuddiez;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    Button loginButton = findViewById(R.id.login_login_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        loginButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        connect_get();
    }


    public void connect_get() {

        String email = ((EditText) findViewById(R.id.login_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();
        String url = "http://coms-309-hv-3.cs.iastate.edu:8080/user/auth";


    }
}
