package com.example.capstonecss497;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    ListView listView;

    String email;

    password_db myDatabase;
    String team_name;

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        email = getIntent().getStringExtra("email");
        team_name = getIntent().getStringExtra("team_name");
        listView = findViewById(R.id.team_list_view);
        myDatabase= new password_db(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        Cursor listOfPlayers = myDatabase.getPlayersToAdapterListOfEmail(team_name);

        try {
            if (listOfPlayers.moveToFirst()) {
                int teamNameIndex = listOfPlayers.getColumnIndex("NAME");
                if (teamNameIndex != -1) {
                    do {
                        String player = listOfPlayers.getString(teamNameIndex);
                        list.add(player);
                        adapter.notifyDataSetChanged();
                    } while (listOfPlayers.moveToNext());
                } else {
                    Log.e("Error", "Cursor does not contain column: NAME");
                }
            }
        } finally {
            listOfPlayers.close();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                String nameInput = (String) (adapter.getItemAtPosition(i));
                Intent intent;

                intent = new Intent(TeamActivity.this, UserInformationDisplayActivity.class);

                String emailInput = myDatabase.getEmail(nameInput, team_name);
                //based on item add info to intent
                intent.putExtra("email", emailInput);
                intent.putExtra("name", nameInput);
                intent.putExtra("team_name", team_name);
                startActivity(intent);
            }
        });


        listView.setAdapter(adapter);
    }
}