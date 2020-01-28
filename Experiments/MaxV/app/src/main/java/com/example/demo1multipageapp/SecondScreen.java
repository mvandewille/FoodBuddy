package com.example.demo1multipageapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondScreen extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the layout we created
        setContentView(R.layout.layout_too);

        // Get the Intent that called for this Activity to open

        Intent activityThatCalled = getIntent();

        // Get the data that was sent

        String previousActivity = activityThatCalled.getExtras().getString("callingActivity");

        TextView callingActivityMessage = (TextView)
                findViewById(R.id.calling_activity_info_text_view);

        callingActivityMessage.append(" " + previousActivity);
    }

    public void onSendUsersName(View view) {

        // Get the users name from the EditText
        EditText usersNameET = (EditText) findViewById(R.id.users_name_edit_text);

        // Get the name typed into the EditText
        String usersName = String.valueOf(usersNameET.getText());

        // Define our intention to go back to ActivityMain
        Intent goingBack = new Intent();

        // Define the String name and the value to assign to it
        goingBack.putExtra("UsersName", usersName);

        // Sends data back to the parent and can use RESULT_CANCELED, RESULT_OK, or any
        // custom values starting at RESULT_FIRST_USER. RESULT_CANCELED is sent if
        // this Activity crashes
        setResult(RESULT_OK, goingBack);

        // Close this Activity
        finish();

    }
}