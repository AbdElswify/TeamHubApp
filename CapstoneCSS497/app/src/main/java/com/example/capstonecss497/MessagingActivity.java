package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    password_db myDatabase;
    LinearLayout listView;

    EditText input;
    Button submit;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        myDatabase= new password_db(this);
        String team_name = getIntent().getStringExtra("team_name");
        String email = getIntent().getStringExtra("email");
        listView = findViewById(R.id.list_message);
        submit = findViewById(R.id.send_button);
        input = findViewById(R.id.message_input);
        name = myDatabase.getName(email, team_name);

        Cursor listOfMessages = myDatabase.getMessagesFromTeam(team_name);

        try {
            if (listOfMessages.moveToFirst()) {
                int messageIndex = listOfMessages.getColumnIndex("MESSAGE");
                int nameIndex = listOfMessages.getColumnIndex("USERNAME");
                if ((messageIndex != -1) || (nameIndex != -1)) {
                    do {
                        String messageName = listOfMessages.getString(messageIndex);
                        String userName = listOfMessages.getString(nameIndex);
                        TextView newTextViewName = new TextView(this);
                        TextView newTextViewMessage = new TextView(this);
                        newTextViewName.setTypeface(Typeface.DEFAULT_BOLD);
                        newTextViewName.setText(userName + ": \n");
                        newTextViewMessage.setText(messageName + "\n\n\n\n");
                        if (userName.equals(name)) {
                            newTextViewName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                            newTextViewMessage.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                        } else {
                            newTextViewName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                            newTextViewMessage.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                        }
                        listView.addView(newTextViewName);
                        listView.addView(newTextViewMessage);
                    } while (listOfMessages.moveToNext());
                } else {
                    Log.e("Error", "Cursor does not contain column");
                }
            }
        } finally {
            listOfMessages.close();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = input.getText().toString();
                input.setText("");
                //String name = myDatabase.getName(email, team_name);
                myDatabase.insertMessageData(email,team_name,name,msg);
                TextView newTextViewName = new TextView(MessagingActivity.this);
                TextView newTextViewMessage = new TextView(MessagingActivity.this);
                newTextViewName.setTypeface(Typeface.DEFAULT_BOLD);
                //newTextView.setBackgroundColor(getResources().getColor(R.color.blue));
                newTextViewName.setText(name + ": \n");
                newTextViewMessage.setText(msg + "\n\n\n\n");
                newTextViewName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                newTextViewMessage.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                listView.addView(newTextViewName);
                listView.addView(newTextViewMessage);
            }
        });
    }
}