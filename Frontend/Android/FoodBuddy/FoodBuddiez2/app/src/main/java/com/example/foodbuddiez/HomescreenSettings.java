package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomescreenSettings extends AppCompatActivity implements View.OnClickListener {

    EditText emailEdit;
    EditText nameEdit;
    EditText ageEdit;
    EditText heightEdit;
    EditText weightEdit;
    Spinner genderEdit;
    EditText calorieEdit;
    SeekBar activityEdit;
    Button update;
    Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_settings);
       emailEdit = findViewById(R.id.settings_email_edit);
       nameEdit = findViewById(R.id.settings_name_edit);
       ageEdit = findViewById(R.id.settings_age_edit);
       heightEdit = findViewById(R.id.settings_height_edit);
       weightEdit = findViewById(R.id.settings_weight_edit);
       genderEdit = findViewById(R.id.settings_gender_field);
       calorieEdit = findViewById(R.id.settings_calories_edit);
       activityEdit = findViewById(R.id.settings_activity_edit);
       update = findViewById(R.id.settings_update);
       cancel = findViewById(R.id.settings_cancel);

        update.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_update:
                sendUpdate();
                Intent loginIntent = new Intent(this, HomescreenCalendar.class);
                this.startActivity(loginIntent);
                break;
            case R.id.settings_cancel:
                Intent signupIntent = new Intent(this, HomescreenCalendar.class);
                this.startActivity(signupIntent);
                break;
            default:
                break;
        }
    }


    public void sendUpdate() {      //refactoring to have method specifically for sending JSON
        JSONObject obj = new JSONObject();
        String url = ("http://coms-309-hv-3.cs.iastate.edu:8080/user/update");
        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            obj.put("email", emailEdit.getText());    //string
            obj.put("name", nameEdit.getText());    //string
            obj.put("age", Integer.parseInt(ageEdit.getText().toString()));     //int
            obj.put("height", Integer.parseInt(heightEdit.getText().toString()));   //int
            obj.put("weight", Integer.parseInt(weightEdit.getText().toString()));   //int
            obj.put("lifestyle", lifestyleString(activityEdit));      //string
            obj.put("gender", genderEdit.getSelectedItem().toString()); //string

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int responseCode = 1;
                        String responseMessage = "";
                        try {
                             responseCode = response.getInt("response");
                             responseMessage = response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        toastKickoff("User Updated: " + responseCode +  " " + responseMessage);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                        toastKickoff("Internal Error");
                    }
                });

        queue.add(request);

    }

    public String lifestyleString(SeekBar activity) {       //helper method to determine activity level from seekbar
        String lifestyle = "";
        switch (activity.getProgress()) {
            case 1: lifestyle = "Sedentary";
                break;
            case 2: lifestyle = "Somewhat active";
                break;
            case 3: lifestyle = "Active";
                break;
            default: break;
        }
        return lifestyle;
    }

    public void toastKickoff(String message) {      //helper method to start a toast to display messages
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}