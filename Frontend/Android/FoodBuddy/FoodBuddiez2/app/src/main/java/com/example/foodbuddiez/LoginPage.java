package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    EditText userName;
    EditText password;
    String responseResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        loginButton = findViewById(R.id.login_login_button);
        userName = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        connect_get();
    }


    public void connect_get() {

        final String emailValue = userName.getText().toString();
        final String passwordValue = password.getText().toString();

        String hashedPass = hashSHA(passwordValue);   //unused but able to hash password in standard SHA-512 format

        String url = ("http://coms-309-hv-3.cs.iastate.edu:8080/user/auth?email=" + emailValue + "&password=" + hashedPass);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //parse response JSON, check response and handle accordingly
                        int responseCode = 0;
                        String responseMessage = "Internal Error, please try again";    //default so if we don't get an error message for some reason
                        try {
                            responseCode = response.getInt("response");
                            responseMessage = response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (responseCode == 1) {        //if we are authenticated, go to home screen
                            intentKickoff(emailValue);
                        } else {        //if we aren't authenticated, pop up the error
                            toastPopup(responseMessage);
                        }
                    }
                },
                new Response.ErrorListener() {      //if we have other problems, catch them here and log it
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                });

        queue.add(request);
    }

    public String hashSHA(String passwordPlain) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(passwordPlain.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public void intentKickoff(String email) {
        Intent intent = new Intent(this, HomescreenCalendar.class);
        intent.putExtra("EMAIL", email);
        this.startActivity(intent);
    }

    public void toastPopup(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
