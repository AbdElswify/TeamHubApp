package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageView got_reg;

    EditText editTextEmail,editTextPassword;
    Button button_login;
    password_db myDatabasePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         myDatabasePassword = new password_db(this);


         editTextEmail = findViewById(R.id.email);
         editTextPassword = findViewById(R.id.password);
         button_login = findViewById(R.id.login);

        got_reg = findViewById(R.id.goto_reg);

        got_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

         button_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 attempt_login();
             }
         });


    }

    private void attempt_login() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!isEmailValid(email)) {
            Toast.makeText(this, "Email Invalid", Toast.LENGTH_SHORT).show();
        }
        if (!isPasswordValid(password)) {
            Toast.makeText(this, "Password Invalid", Toast.LENGTH_SHORT).show();
        }
        Cursor res = myDatabasePassword.login_user(email,password);
        if (res.getCount() > 0) {
            final Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            i.putExtra("email", email);
            startActivity(i);
        } else {
            Toast.makeText(this, "Invalid Account: " + res.getCount() + ", email: " + email + ", password: " + password, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}