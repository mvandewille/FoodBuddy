package com.example.foodbuddiez;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Homescreen extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView navigationViewBar;
    private Button btnCapture;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;
    ImageButton manualEntryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        navigationViewBar = findViewById(R.id.homescreen_navigation_bar);
        navigationViewBar.setSelectedItemId(R.id.bottom_navigation_item_camera);
        manualEntryButton = findViewById(R.id.homescreen_manual_entry);
        manualEntryButton.setOnClickListener(this);
        btnCapture =(Button)findViewById(R.id.btnTakePicture);
        imgCapture = (ImageView) findViewById(R.id.capturedImage);
        btnCapture.setOnClickListener(this);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //method for obtaining camera image
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTakePicture:
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
                break;
            case R.id.homescreen_manual_entry:
                Intent signupIntent = new Intent(this, ManualEntry.class);
                this.startActivity(signupIntent);
                break;
            default:
                break;
        }
    }

}
