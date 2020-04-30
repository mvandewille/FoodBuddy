package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Homescreen extends AppCompatActivity {

    BottomNavigationView navigationViewBar;
    Camera viewCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        //navigationMenu = (Menu) findViewById(R.id.bottom_navigation_item_calendar).getParent();
        navigationViewBar = findViewById(R.id.homescreen_navigation_bar);
        navigationViewBar.setSelectedItemId(R.id.bottom_navigation_item_camera);

        navigationViewBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_item_calendar:
                        startActivity(new Intent(getBaseContext(), HomescreenCalendar.class)); //TODO setup calendar
                        break;
                    case R.id.bottom_navigation_item_friends:
                        startActivity(new Intent(getBaseContext(), HomescreenFriends.class));
                        break;
                    case R.id.bottom_navigation_item_settings:
                        startActivity(new Intent(getBaseContext(), HomescreenSettings.class));
                        break;
                }
                return true;
            }
        });


        viewCamera = findViewById(R.id.view_camera);


    }
}
