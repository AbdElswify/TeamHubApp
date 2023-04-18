package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class NavigationActivity extends AppCompatActivity {
    String team_name;

    String email;
    TextView title;

    Button teamList;

    Button messages;

    Button announcements;

    password_db myDatabase;

    Button events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        team_name = getIntent().getStringExtra("team_name");
        email = getIntent().getStringExtra("email");
        title = findViewById(R.id.tv_team_name);
        teamList = findViewById(R.id.btn_team);
        messages = findViewById(R.id.btn_messages);
        announcements = findViewById(R.id.btn_announcements);
        events = findViewById(R.id.btn_events);
        myDatabase = new password_db(this);
        title.setText(team_name +  " - (" + myDatabase.getCode(email,team_name) + ")");

        announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i;
                Log.e("status", myDatabase.getManagerStatus(email,team_name));
                if(!(Integer.parseInt(myDatabase.getManagerStatus(email,team_name)) == 0)) {
                    i = new Intent(NavigationActivity.this,AnnouncementsManagerActivity.class);
                } else {
                    i = new Intent(NavigationActivity.this,AnnouncementsActivity.class);
                }

                i.putExtra("email", email);
                i.putExtra("team_name", team_name);
                startActivity(i);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i;

                if(!(Integer.parseInt(myDatabase.getManagerStatus(email,team_name)) == 0)) {
                    i = new Intent(NavigationActivity.this,EventsManagerActivity.class);
                } else {
                    Log.e("status", Integer.parseInt(myDatabase.getManagerStatus(email,team_name)) + "");
                    i = new Intent(NavigationActivity.this,EventsActivity.class);
                }
                i.putExtra("email", email);
                i.putExtra("team_name", team_name);
                startActivity(i);
            }
        });

        teamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(NavigationActivity.this,TeamActivity.class);
                i.putExtra("email", email);
                i.putExtra("team_name", team_name);
                startActivity(i);
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(NavigationActivity.this,MessagingActivity.class);
                i.putExtra("email", email);
                i.putExtra("team_name", team_name);
                startActivity(i);
            }
        });

    }
}