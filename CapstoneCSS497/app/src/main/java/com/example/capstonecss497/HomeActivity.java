package com.example.capstonecss497;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstonecss497.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    Button button2;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String email;

    password_db myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDatabase= new password_db(this);


        email = getIntent().getStringExtra("email");
        button = findViewById(R.id.btnAdd);
        button2 = findViewById(R.id.btnAdd2);
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        Cursor listOfTeams = myDatabase.getTeamsToAdapterListOfEmail(email);

        try {
            if (listOfTeams.moveToFirst()) {
                int teamNameIndex = listOfTeams.getColumnIndex("TNAME");
                if (teamNameIndex != -1) {
                    do {
                        String teamName = listOfTeams.getString(teamNameIndex);
                        list.add(teamName);
                        adapter.notifyDataSetChanged();
                    } while (listOfTeams.moveToNext());
                } else {
                    Log.e("Error", "Cursor does not contain column: TNAME");
                }
            }
        } finally {
            listOfTeams.close();
        }

        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random = returnRandomNum();
                if(editText.getText().toString().equals("")) {
                    Toast.makeText(HomeActivity.this, "Please write your team name, don't leave it blank", Toast.LENGTH_SHORT).show();
                }
                else if(!myDatabase.checkForDuplicateTeamsWithName(editText.getText().toString())) {
                    Toast.makeText(HomeActivity.this, "Team already exists ", Toast.LENGTH_SHORT).show();
                }else {
                    myDatabase.insertTeamData(email, editText.getText().toString(), random, 1);

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Invitation code");
                    i.putExtra(Intent.EXTRA_TEXT, "Send this code to your team members: " + random);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(HomeActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                    list.add(editText.getText().toString());
                    editText.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDatabase.checkCodeForTeam((editText.getText().toString()));
                String t_name = "";

                try {
                    if (res.moveToFirst()) {
                        int teamNameIndex = res.getColumnIndex("TNAME");
                        if (teamNameIndex != -1) {
                                t_name = res.getString(teamNameIndex);
                        } else {
                            Log.e("Error", "Cursor does not contain column: TNAME");
                        }
                    }
                } finally {
                    res.close();
                }

                if(myDatabase.checkIfEmailAlreadyInTeam(t_name,email)) {
                    Toast.makeText(HomeActivity.this, "Team already exists ", Toast.LENGTH_SHORT).show();
                } else if (res.getCount() > 0) {
                    myDatabase.insertTeamData(email, t_name, Integer.parseInt(editText.getText().toString()), 0);
                    list.add(t_name);
                    editText.setText("");
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(HomeActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                String team_n = (String) (adapter.getItemAtPosition(i));
                Intent intent;
                Cursor res = myDatabase.checkEntryStatus(email,team_n);
                int status = -1;

                try {
                    if (res.moveToFirst()) {
                        int teamNameIndex = res.getColumnIndex("ENTRYSTATUS");
                        if (teamNameIndex != -1) {
                            status = Integer.parseInt(res.getString(teamNameIndex));
                        } else {
                            Log.e("Error", "Cursor does not contain column: TNAME");
                        }
                    }
                } finally {
                    res.close();
                }

                if (status == 1) {
                    intent = new Intent(HomeActivity.this, NavigationActivity.class);
                } else {
                    intent = new Intent(HomeActivity.this, UserInformationInputActivity.class);

                }
                //based on item add info to intent
                intent.putExtra("email", email);
                intent.putExtra("team_name", team_n);
                startActivity(intent);
            }
        });



        listView.setAdapter(adapter);
    }

    private int returnRandomNum() {
        int max = 99999;
        int min = 10000;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int)(Math.random() * range) + min;

        // Output is different everytime this code is executed
        System.out.println(rand);

        return rand;
    }
}