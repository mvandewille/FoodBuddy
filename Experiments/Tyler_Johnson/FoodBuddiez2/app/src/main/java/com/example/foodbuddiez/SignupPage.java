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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class SignupPage extends AppCompatActivity implements View.OnClickListener {

    Button signupButton;
    EditText email;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        signupButton = findViewById(R.id.signup_signup_button);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_password);
        signupButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        String confirmPasswordValue = confirmPassword.getText().toString();
        String url = "http://coms-309-hv-3.cs.iastate.edu:8080/user/add";

         String hashedPassword = hashSHA(passwordValue);

        RequestQueue queue = Volley.newRequestQueue(this);





        if (!(passwordValue.equals(confirmPasswordValue))) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            password.setText("");
            confirmPassword.setText("");
        }
        else{

            //TODO check for email validity, check if email already exists in DB

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", emailValue);
            params.put("password", passwordValue);

            JSONObject obj = new JSONObject();
            try {
                obj.put("email", emailValue);
                obj.put("password", hashedPassword);
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }

            final String JSONbody = obj.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    intentKickoff();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return JSONbody == null ? null : JSONbody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", JSONbody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            queue.add(stringRequest);
        }
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


    public void intentKickoff() {
        Intent intent = new Intent(this, UserConfig.class);
        this.startActivity(intent);
    }
}