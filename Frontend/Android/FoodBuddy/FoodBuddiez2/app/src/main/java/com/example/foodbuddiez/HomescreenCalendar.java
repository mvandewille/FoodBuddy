package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomescreenCalendar extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    BottomNavigationView navigationViewBar;
    TextView calendarValue;
    CalendarView calendar;
    public static String email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_calendar);
        setEmail(getIntent().getStringExtra("EMAIL"));
        navigationViewBar = findViewById(R.id.homescreen_calendar_navigation_bar);
        navigationViewBar.setSelectedItemId(R.id.bottom_navigation_item_calendar);
        calendarValue = findViewById(R.id.homescreen_calendar_value);
        calendar = findViewById(R.id.homescreen_calendar_display);
        navigationViewBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_item_camera:
                        startActivity(new Intent(getBaseContext(), Homescreen.class)); //TODO setup calendar
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
        calendar.setOnDateChangeListener(this);
    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        calendarValue.setText(String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth) + "-" + String.valueOf(year));
    }



    public static String getEmail(){
        return email;
    }

    public void setEmail(String emailSetter){
        email = emailSetter;
    }
}
