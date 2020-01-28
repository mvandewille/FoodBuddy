package com.example.demo1multipageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGetNameClick(View view) {

        // We have to state that are intention is to open another Activity. We do so
        // by passing a Context and the Activity that we want to open

        Intent getNameScreenIntent = new Intent(this, SecondScreen.class);

        // We ask for the Activity to start and don't expect a result to be sent back
        // startActivity(getNameScreenIntent);

        // We use startActivityForResult when we expect a result to be sent back

        final int result = 1;

        // To send data use putExtra with a String name followed by its value

        getNameScreenIntent.putExtra("callingActivity", "MainActivity");

        startActivityForResult(getNameScreenIntent, result);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Create the TextView so I can put the users name on it
        TextView usersNameMessage = (TextView) findViewById(R.id.users_name_message);

        // Get the users name from the previous Activity
        String nameSentBack = data.getStringExtra("UsersName");

        // Add the users name to the end of the textView
        usersNameMessage.append(" " + nameSentBack);

    }
}
