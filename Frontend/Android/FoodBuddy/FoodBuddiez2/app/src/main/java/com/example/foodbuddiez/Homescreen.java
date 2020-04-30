package com.example.foodbuddiez;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Homescreen extends AppCompatActivity {

    BottomNavigationView navigationViewBar;
    private Button btnCapture;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;


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


        btnCapture =(Button)findViewById(R.id.btnTakePicture);
        imgCapture = (ImageView) findViewById(R.id.capturedImage);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

}
