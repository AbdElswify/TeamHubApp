package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    View back_to_login;
    EditText editTextEmail,editTextPassword,editTextConPassword;
    Button register_user;
    password_db myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDatabase = new password_db(this);

        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        editTextConPassword=findViewById(R.id.password_com);
        register_user = findViewById(R.id.register);

        Register_user();

        back_to_login = findViewById(R.id.back_to_login);

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void Register_user() {
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String con_pass = editTextConPassword.getText().toString();
                
                if (!password.equals(con_pass)) {
                    Toast.makeText(RegisterActivity.this, "The password doesn't match, try again", Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid(email)) {
                    Toast.makeText(RegisterActivity.this,"Is not valid email try again", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(password)) {
                    Toast.makeText(RegisterActivity.this,"Is not valid password. Must be longer than 7 characters.", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Did not fill in email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Did not fill in password", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = myDatabase.insertData(email, password);

                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message")
                            .setContentText("You are registered")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                  Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                  startActivity(i);
                                }
                            }).show();


                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    private boolean isEmailValid(String email) {
        if (!myDatabase.checkForDuplicateEmails(email) || !email.contains("@")) {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}