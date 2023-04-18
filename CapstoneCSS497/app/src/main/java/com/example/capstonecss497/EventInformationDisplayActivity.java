package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EventInformationDisplayActivity extends AppCompatActivity {

    TextView time;
    TextView location;

    TextView title;

    password_db myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information_display);
        String heading = getIntent().getStringExtra("heading");
        String teamName = getIntent().getStringExtra("team_name");
        myDatabase = new password_db(this);

        time = findViewById(R.id.time_textview_input);
        location = findViewById(R.id.address_textview_input);
        title = findViewById(R.id.event_info_title);

        title.setText(heading);
        time.setText(myDatabase.getTime(teamName, heading));
        location.setText(myDatabase.getLocation(teamName, heading));
    }
}