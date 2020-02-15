package com.example.foodbuddiez;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

    }


    @Override
    public void onClick(View v) {
        connect_post();
    }


    public void connect_post() {

    EditText username = findViewById(R.id.login_username);
    EditText password = findViewById(R.id.login_password);

    HttpURLConnection connection = null;
    try{
        URL url = new URL("coms-309-hv-3.cs.iastate.edu:8080/user/add");
        String usernameValue = URLEncoder.encode(username.getText().toString());
        String passwordValue = URLEncoder.encode(password.getText().toString());
        connection = (HttpURLConnection) url.openConnection();
        InputStream input = connection.getInputStream();
        InputStreamReader inputReader = new InputStreamReader(input);


    }
    catch (Exception ex){

    }
    finally {
        if (connection != null){
            connection.disconnect();
        }
    }


    }
}
