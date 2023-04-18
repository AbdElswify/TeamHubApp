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

public class AnnouncementsActivity extends AppCompatActivity {

    ListView listView;

    String email;

    password_db myDatabase;
    String team_name;

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        email = getIntent().getStringExtra("email");
        team_name = getIntent().getStringExtra("team_name");
        listView = findViewById(R.id.announcement_list);
        myDatabase = new password_db(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        Cursor listOfAnnouncements = myDatabase.getListOfAnnouncements(team_name);

        try {
            if (listOfAnnouncements.moveToFirst()) {
                int teamNameIndex = listOfAnnouncements.getColumnIndex("HEADING_A");
                if (teamNameIndex != -1) {
                    do {
                        String player = listOfAnnouncements.getString(teamNameIndex);
                        list.add(player);
                        adapter.notifyDataSetChanged();
                    } while (listOfAnnouncements.moveToNext());
                } else {
                    Log.e("Error", "Cursor does not contain column: HEADING_A");
                }
            }
        } finally {
            listOfAnnouncements.close();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                String heading = (String) (adapter.getItemAtPosition(i));
                Intent intent;

                intent = new Intent(AnnouncementsActivity.this, AnnouncementInformationDisplayActivity.class);


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