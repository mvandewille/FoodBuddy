package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllergyCheck extends AppCompatActivity implements View.OnClickListener {

    Button submitButton;
    CheckBox milkCB;
    CheckBox eggCB;
    CheckBox peanutCB;
    CheckBox treeNutCB;
    CheckBox soyCB;
    CheckBox wheatCB;
    CheckBox fishCB;
    CheckBox shellFishCB;
    CheckBox cornCB;
    CheckBox meatCB;
    CheckBox allergyCB;
    ScrollView allergyList;
    String email;
    EditText calorieCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allergy_check_page);
        email = getIntent().getStringExtra("EMAIL");


        milkCB = findViewById(R.id.allergy_milk_checkbox);
        eggCB = findViewById(R.id.allergy_egg_checkbox);
        peanutCB = findViewById(R.id.allergy_peanut_checkbox);
        treeNutCB = findViewById(R.id.allergy_tree_nut_checkbox);
        soyCB = findViewById(R.id.allergy_soy_checkbox);
        wheatCB = findViewById(R.id.allergy_wheat_checkbox);
        fishCB = findViewById(R.id.allergy_fish_checkbox);
        shellFishCB = findViewById(R.id.allergy_shellfish_checkbox);
        cornCB = findViewById(R.id.allergy_corn_checkbox);
        meatCB = findViewById(R.id.allergy_meat_checkbox);
        allergyList = findViewById(R.id.allergy_list);

        allergyCB = findViewById(R.id.allergy_allergy_checkbox);
        allergyCB.setOnClickListener(this);

        submitButton = findViewById(R.id.allergy_submit);
        submitButton.setOnClickListener(this);

        calorieCount = findViewById(R.id.allergy_calorie_count_display);
        calorieCount.setText(getIntent().getStringExtra("CALORIES"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allergy_allergy_checkbox:
                //change visibility
                if (allergyCB.isChecked()) {
                    allergyList.setVisibility(View.VISIBLE);
                } else {
                    allergyList.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.allergy_submit:

                //TODO update user with allergan information, if there
                JSONObject obj = allergyListCheck();    //get object of the arrays checked off

                jsonPost(obj);      //send the update request
                intentKickoff();    //kick off intent to home screen
                break;
            default:
                break;

        }

    }

    public JSONObject allergyListCheck() {
        ArrayList<String> allergenString = new ArrayList<String>();
        JSONArray allergyObj = new JSONArray();
        JSONObject emailField = new JSONObject();


            if (milkCB.isChecked()) {
                allergyObj.put("Milk");
            }
            if (eggCB.isChecked()) {
                allergyObj.put("Egg");
            }
            if (peanutCB.isChecked()) {
                allergyObj.put("Peanut");
            }
            if (treeNutCB.isChecked()) {
                allergyObj.put("Tree nut");
            }
            if (soyCB.isChecked()) {
                allergyObj.put("Soy");
            }
            if (wheatCB.isChecked()) {
                allergyObj.put("Wheat");
            }
            if (fishCB.isChecked()) {
                allergyObj.put("Fish");
            }
            if (shellFishCB.isChecked()) {
                allergyObj.put("Shellfish");
            }
            if (cornCB.isChecked()) {
                allergyObj.put("Corn");
            }
            if (meatCB.isChecked()) {
                allergyObj.put("Meat");
            }

        try {
            emailField.put("email", email);
            emailField.put("calorieLimit", calorieCount.getText());
            emailField.put("allergens", allergyObj);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return emailField;
    }

    public void jsonPost(JSONObject json) {
        String url = "http://coms-309-hv-3.cs.iastate.edu:8080/user/update";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                int responseCode = 0;
                String responseMessage = "Internal Error, please try again";
                try {
                    responseCode = response.getInt("response");
                    responseMessage = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (responseCode == 1) {
                    intentKickoff();
                } else {
                    toastPopup(responseMessage);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                toastPopup("Error connecting to server, please try again later");
            }
        });
        queue.add(request);
    }


    public void toastPopup(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void intentKickoff() {
        Intent intent = new Intent(this, HomescreenCalendar.class);
        intent.putExtra("EMAIL", email);
        this.startActivity(intent);
    }
}
