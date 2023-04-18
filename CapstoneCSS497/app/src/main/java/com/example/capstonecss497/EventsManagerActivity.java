package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EventsManagerActivity extends AppCompatActivity {

    ListView listView;

    String email;

    password_db myDatabase;
    String team_name;

    Button add;

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_manager);
        email = getIntent().getStringExtra("email");
        team_name = getIntent().getStringExtra("team_name");
        listView = findViewById(R.id.events_listview);
        add = findViewById(R.id.events_add_button);
        myDatabase = new password_db(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        Cursor listOfEvents = myDatabase.getListOfEvents(team_name);

        try {
            if (listOfEvents.moveToFirst()) {
                int teamNameIndex = listOfEvents.getColumnIndex("HEADING_E");
                if (teamNameIndex != -1) {
                    do {
                        String player = listOfEvents.getString(teamNameIndex);
                        list.add(player);
                        adapter.notifyDataSetChanged();
                    } while (listOfEvents.moveToNext());
                } else {
                    Log.e("Error", "Cursor does not contain column: DESCRIPTION_E");
                }
            }
        } finally {
            listOfEvents.close();
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsManagerActivity.this, EventInformationInputActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                String heading = (String) (adapter.getItemAtPosition(i));
                Intent intent;

                intent = new Intent(EventsManagerActivity.this, EventInformationDisplayActivity.class);


                //based on item add info to intent
                intent.putExtra("email", email);
                intent.putExtra("heading", heading);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
            }
        });


        listView.setAdapter(adapter);
    }
}