package com.example.foodbuddiez;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class HomescreenFriends extends AppCompatActivity implements View.OnClickListener{
    //private static final int NORMAL_CLOSURE_STATUS = 1000;
    BottomNavigationView navigationViewBar;
    private Button sendButton;
    private EditText textField;
    private TextView output;
    private OkHttpClient client;

    WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_friends);
        navigationViewBar = findViewById(R.id.homescreen_friends_navigation_bar_);
        navigationViewBar.setSelectedItemId(R.id.bottom_navigation_item_friends);
        sendButton = findViewById(R.id.homescreen_friends_send_button);
        textField = findViewById(R.id.homescreen_friends_message_entry);
        sendButton.setOnClickListener(this);
        navigationViewBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_item_calendar:
                        startActivity(new Intent(getBaseContext(), HomescreenCalendar.class)); //TODO setup calendar
                        break;
                    case R.id.bottom_navigation_item_camera:
                        startActivity(new Intent(getBaseContext(), Homescreen.class));
                        break;
                    case R.id.bottom_navigation_item_settings:
                        startActivity(new Intent(getBaseContext(), HomescreenSettings.class));
                        break;
                }
                return true;
            }
        });


        output = (TextView) findViewById(R.id.output);
        client = new OkHttpClient();
        start();

    }

    @Override
    public void onClick(View v) {
        String message= textField.getText().toString();
        webSocket.send(message);
        output(message);
        textField.setText("");
    }

    public final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;    @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Hello");
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output(text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output(bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Failure : " + t.getMessage());
        }


    }
                            //wss://echo.websocket.org
    private void start() {
        String username = HomescreenCalendar.getEmail();

        Request request = new Request.Builder().url("ws://coms-309-hv-3.cs.iastate.edu:8080/chat/" + username).build();        //TODO remove static field for name
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        webSocket = ws;
        ws.send("This is a test");               //initial send to log in
       // client.dispatcher().executorService().shutdown();
    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(output.getText().toString() + "\n\n" + txt);
            }

            //  name;username;
        });
    }






}