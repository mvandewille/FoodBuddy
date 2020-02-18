package com.example.foodbuddiez;

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

        String url = ("http://coms-309-hv-3.cs.iastate.edu:8080/user/auth?email="+ emailValue + "&password=" + hashedPass);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        responseContainer(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                });

        queue.add(sr);
    }

    public void responseContainer(String r) {
        responseResult = r;
        Toast.makeText(this, "response is: " + responseResult, Toast.LENGTH_LONG).show();
    }

    public String hashSHA(String passwordPlain) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        }catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        byte[] digest = md.digest(passwordPlain.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
