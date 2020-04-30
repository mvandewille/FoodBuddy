package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserConfig extends AppCompatActivity implements View.OnClickListener {

    Button configSubmit;
    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    Spinner gender;
    SeekBar activityLevel;


    EditText calorieCount;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config);
        email = getIntent().getStringExtra("EMAIL");

        configSubmit = findViewById(R.id.user_config_submit);
        name = findViewById(R.id.user_config_name_field);
        age = findViewById(R.id.user_config_age_field);
        height = findViewById(R.id.user_config_height_field);
        weight = findViewById(R.id.user_config_weight_field);
        gender = findViewById(R.id.user_config_gender_field);
        activityLevel = findViewById(R.id.user_config_activity_field);
        calorieCount = findViewById(R.id.allergy_calorie_count_display);


        configSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //TODO confirm all fields have been entered, then go to main screen

        boolean valid = inputValidation();
        JSONObject obj = new JSONObject();
        String url = ("http://coms-309-hv-3.cs.iastate.edu:8080/user/update");
        RequestQueue queue = Volley.newRequestQueue(this);

        if (valid) {

            try {
                obj.put("email", email);    //string
                obj.put("name", name.getText());    //string
                obj.put("age", Integer.parseInt(age.getText().toString()));     //int
                obj.put("height", Integer.parseInt(height.getText().toString()));   //int
                obj.put("weight", Integer.parseInt(weight.getText().toString()));   //int
                obj.put("lifestyle", lifestyleString(activityLevel));      //string
                obj.put("gender", gender.getSelectedItem().toString()); //string

            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            intentKickoff();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    });

            queue.add(request);
            //send new HTTP request to update information, then go to main page
        }
    }

    //checks if the user entered all the information that is needed for customization
    public boolean inputValidation() {
        if (TextUtils.isEmpty(name.getText())) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(age.getText())) {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(height.getText())) {
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(weight.getText())) {
            Toast.makeText(this, "Please enter your weight (we promise we won't tell)", Toast.LENGTH_LONG).show();
            return false;
        }
        if (gender.getCount() == 0) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;        //if everything that is requires is checked, allow the user to continue
    }


    public String calorieCalculation() {

        String genderString = gender.getSelectedItem().toString();
        int heightValue = Integer.parseInt( height.getText().toString());
        int weightValue = Integer.parseInt(weight.getText().toString());
        int ageValue = Integer.parseInt(age.getText().toString());
        int activityLevelValue = activityLevel.getProgress();       //don't know if we need activity level
        int calories;

        if (genderString.equals("Female")) {
            calories = (int) (9.247 * (weightValue * 0.4535) + 3.098 * (heightValue * 2.54) - 4.330 * (ageValue) + 447.593);
        } else {
            calories = (int) (13.397 * (weightValue * 0.4535) + 4.799 * (heightValue * 2.54) - 5.667 * (ageValue) + 88.362);
        }
        return String.valueOf(calories);
    }

    public void intentKickoff() {
        Intent intent = new Intent(this, AllergyCheck.class);
        intent.putExtra("EMAIL", email);
        intent.putExtra("CALORIES", calorieCalculation());
        this.startActivity(intent);
    }

    public String lifestyleString(SeekBar activity) {
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

}
