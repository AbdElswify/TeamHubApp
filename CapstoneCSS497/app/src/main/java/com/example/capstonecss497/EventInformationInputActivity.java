package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EventInformationInputActivity extends AppCompatActivity {

    EditText heading;
    EditText time;

    EditText location;
    Button submit;

    password_db myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information_input);
        heading = findViewById(R.id.edit_text_event_heading);
        time = findViewById(R.id.edit_text_event_time);
        location = findViewById(R.id.edit_event_location);

        submit = findViewById(R.id.button_submit_event);
        myDatabase= new password_db(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameInput = heading.getText().toString();
                String timeInput = time.getText().toString();
                String locInput = location.getText().toString();
                //Toast.makeText(UserInformationInputActivity.this, "Input: " + nameInput + ", " + positionInput + ",  " + phoneInput, Toast.LENGTH_SHORT).show();
                String email = getIntent().getStringExtra("email");
                String team_name = getIntent().getStringExtra("team_name");

                //Toast.makeText(EventInformationInputActivity.this, "Input: " + nameInput + ", " + positionInput + ",  " + phoneInput + ",  " + email + ",  " + team_name, Toast.LENGTH_SHORT).show();
                myDatabase.insertEventData(nameInput,timeInput,team_name,locInput);

                Intent intent = new Intent(EventInformationInputActivity.this, EventsManagerActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
            }
        });



    }
}