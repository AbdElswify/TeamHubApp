package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserInformationDisplayActivity extends AppCompatActivity {

    TextView email;
    TextView position;
    TextView phone;

    TextView title;

    password_db myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_display);
        String emailInput = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        String teamName = getIntent().getStringExtra("team_name");
        myDatabase= new password_db(this);

        email = findViewById(R.id.email_textviewinput);
        position = findViewById(R.id.position_textviewinput);
        phone = findViewById(R.id.phone_textviewinput);
        title = findViewById(R.id.user_info_title);

        title.setText(name + "'s Information");
        email.setText(emailInput);
        position.setText(myDatabase.getPosition(teamName, name, emailInput));
        phone.setText(myDatabase.getPhone(teamName, name, emailInput));

    }
}