package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnnouncementInformationInputActivity extends AppCompatActivity {

    EditText heading;
    EditText description;

    Button submit;

    password_db myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_information_input);
        heading = findViewById(R.id.edit_text__announcement_heading);
        description = findViewById(R.id.edit_text_announcement_description);

        submit = findViewById(R.id.button_submit_announcement);
        myDatabase= new password_db(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String headingInput = heading.getText().toString();
                String desInput = description.getText().toString();
                String team_name = getIntent().getStringExtra("team_name");

                //Toast.makeText(EventInformationInputActivity.this, "Input: " + nameInput + ", " + positionInput + ",  " + phoneInput + ",  " + email + ",  " + team_name, Toast.LENGTH_SHORT).show();
                myDatabase.insertAnnouncementData(headingInput,desInput,team_name);

                Intent intent = new Intent(AnnouncementInformationInputActivity.this, AnnouncementsManagerActivity.class);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
            }
        });



    }
}