package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AnnouncementInformationDisplayActivity extends AppCompatActivity {


    TextView description;

    TextView title;

    password_db myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_information_display);
        String heading = getIntent().getStringExtra("heading");
        String teamName = getIntent().getStringExtra("team_name");
        myDatabase = new password_db(this);

        description = findViewById(R.id.description_textview_input);
        title = findViewById(R.id.announcement_heading_textview_input);

        title.setText(heading);
        description.setText(myDatabase.getDescription(teamName, heading));

    }
}