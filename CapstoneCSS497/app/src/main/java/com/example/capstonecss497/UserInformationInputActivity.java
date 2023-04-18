package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class UserInformationInputActivity extends AppCompatActivity {

    EditText name;
    EditText position;

    EditText phone;
    Button submit;

    password_db myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_input);
        name = findViewById(R.id.edit_text_name);
        position = findViewById(R.id.edit_text_position);
        phone = findViewById(R.id.edit_phone);

        submit = findViewById(R.id.button_submit);
        myDatabase= new password_db(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameInput = name.getText().toString();
                String positionInput = position.getText().toString();
                String phoneInput = phone.getText().toString();
                //Toast.makeText(UserInformationInputActivity.this, "Input: " + nameInput + ", " + positionInput + ",  " + phoneInput, Toast.LENGTH_SHORT).show();
                String email = getIntent().getStringExtra("email");
                String team_name = getIntent().getStringExtra("team_name");

                Toast.makeText(UserInformationInputActivity.this, "Input: " + nameInput + ", " + positionInput + ",  " + phoneInput + ",  " + email + ",  " + team_name, Toast.LENGTH_SHORT).show();
                boolean check = myDatabase.updateTeamData(email,team_name,nameInput,positionInput,phoneInput);
                if (check == true) {
                    myDatabase.informationHasBeenInputted(email,team_name);
                }
                Intent intent = new Intent(UserInformationInputActivity.this, NavigationActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
            }
        });



    }
}