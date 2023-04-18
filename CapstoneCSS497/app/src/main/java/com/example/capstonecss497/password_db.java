package com.example.capstonecss497;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class password_db extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="DB";
    public static final String TABLE_NAME="user";
    public static final String COL_2="EMAIL";
    public static final String COL_3="PASSWORD";


    public static final String TEAM_TABLE_NAME="Team";
    public static final String TEAM_COL_1="UEMAIL";
    public static final String TEAM_COL_2="TNAME";
    public static final String TEAM_COL_3="TCODE";
    public static final String TEAM_COL_4 ="MSTATUS";
    public static final String TEAM_COL_5 ="ENTRYSTATUS";
    public static final String TEAM_COL_6 ="POSITION";
    public static final String TEAM_COL_7 = "NAME";
    public static final String TEAM_COL_8 = "PHONE";

    public static final String MESSAGES_TABLE_NAME="Messages";
    public static final String MESSAGES_COL_1="M_EMAIL";
    public static final String MESSAGES_COL_2="USERNAME";
    public static final String MESSAGES_COL_3="TEAM_NAME";
    public static final String MESSAGES_COL_4="MESSAGE";

    public static final String ANNOUNCEMENTS_TABLE_NAME="ANNOUNCEMENTS";
    public static final String ANNOUNCEMENTS_COL_1="HEADING_A";
    public static final String ANNOUNCEMENTS_COL_2="DESCRIPTION";
    public static final String ANNOUNCEMENTS_COL_3="TEAM_NAME";

    public static final String EVENTS_TABLE_NAME="EVENTS";
    public static final String EVENTS_COL_1="HEADING_E";
    public static final String EVENTS_COL_2="TIME";
    public static final String EVENTS_COL_3="TEAM_NAME";
    public static final String EVENTS_COL_4="LOCATION";





    public password_db(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TEAM_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, UEMAIL TEXT, TNAME TEXT, TCODE INTEGER, MSTATUS INTEGER, ENTRYSTATUS INTEGER, POSITION TEXT, NAME TEXT, PHONE TEXT ) " );
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , EMAIL TEXT , PASSWORD TEXT ) ");
        db.execSQL("create table " + MESSAGES_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , M_EMAIL TEXT , USERNAME TEXT, TEAM_NAME TEXT, MESSAGE TEXT ) ");
        db.execSQL("create table " + ANNOUNCEMENTS_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , HEADING_A TEXT , DESCRIPTION TEXT, TEAM_NAME TEXT) ");
        db.execSQL("create table " + EVENTS_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , HEADING_E TEXT , TIME TEXT, TEAM_NAME TEXT, LOCATION TEXT ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEAM_TABLE_NAME);
        onCreate(db);
    }

    //registration handler
    public boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }




    public boolean insertTeamData(String email, String team_name, int team_code, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_COL_1, email);
        contentValues.put(TEAM_COL_2, team_name);
        contentValues.put(TEAM_COL_3, team_code);
        contentValues.put(TEAM_COL_4, status);
        contentValues.put(TEAM_COL_5, 0);
        contentValues.put(TEAM_COL_6, "NONE");
        contentValues.put(TEAM_COL_7, "NONE");
        contentValues.put(TEAM_COL_8, "NONE");
        long result = db.insert(TEAM_TABLE_NAME, null,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertMessageData(String email, String team_name, String user_name, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGES_COL_1, email);
        contentValues.put(MESSAGES_COL_2, user_name);
        contentValues.put(MESSAGES_COL_3, team_name);
        contentValues.put(MESSAGES_COL_4, message);

        long result = db.insert(MESSAGES_TABLE_NAME, null,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertAnnouncementData(String heading, String description, String tname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ANNOUNCEMENTS_COL_1, heading);
        contentValues.put(ANNOUNCEMENTS_COL_2, description);
        contentValues.put(ANNOUNCEMENTS_COL_3, tname);
        long result = db.insert(ANNOUNCEMENTS_TABLE_NAME, null,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertEventData(String heading, String time, String tname, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_COL_1, heading);
        contentValues.put(EVENTS_COL_2, time);
        contentValues.put(EVENTS_COL_3, tname);
        contentValues.put(EVENTS_COL_4, address);

        long result = db.insert(EVENTS_TABLE_NAME, null,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //login handler
    public Cursor login_user(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where EMAIL='" + email + "'AND PASSWORD='" + password + "' ", null);
        return res;
    }

    public Cursor checkCodeForTeam(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TEAM_TABLE_NAME + " WHERE TCODE = ?";
        Cursor res = db.rawQuery(query, new String[]{code});
        return res;
    }

    public Cursor getAllDataUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public Cursor getTeamName(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT TNAME FROM " + TEAM_TABLE_NAME + " WHERE TCODE = ?";
        Cursor res = db.rawQuery(query, new String[]{code});
        return res;
    }
    public boolean checkForDuplicateTeamsWithName(String team_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT TNAME FROM " + TEAM_TABLE_NAME + " WHERE TNAME = ?";
        Cursor res = db.rawQuery(query, new String[]{team_name});
        if (res.getCount() > 0) {
            return false;
        }
        return true;
    }
    public boolean checkForDuplicateEmails(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT EMAIL FROM " + TABLE_NAME + " WHERE EMAIL = ?";
        Cursor res = db.rawQuery(query, new String[]{email});
        if (res.getCount() > 0) {
            return false;
        }
        return true;
    }


    public boolean checkIfEmailAlreadyInTeam(String t_name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select UEMAIL from " + TEAM_TABLE_NAME + " where UEMAIL='" + email + "'AND TNAME='" + t_name + "' ", null);
        if (res.getCount() > 0) {
            return true;
        }
        return false;
    }

    public Cursor getTeamsToAdapterListOfEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT TNAME FROM " + TEAM_TABLE_NAME + " WHERE UEMAIL = ?";
        Cursor res = db.rawQuery(query, new String[]{email});
        return res;
    }

    public Cursor getPlayersToAdapterListOfEmail(String team_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT NAME FROM " + TEAM_TABLE_NAME + " WHERE TNAME = ?";
        Cursor res = db.rawQuery(query, new String[]{team_name});
        return res;
    }

    public Cursor checkEntryStatus(String email, String team_n) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ENTRYSTATUS from " + TEAM_TABLE_NAME + " where UEMAIL='" + email + "'AND TNAME='" + team_n + "' ", null);
        return res;
    }

    public void informationHasBeenInputted(String email, String team_n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ENTRYSTATUS", 1);
        db.update(TEAM_TABLE_NAME, contentValues, "UEMAIL=? AND TNAME=?", new String[]{email, team_n});
    }

    public boolean updateTeamData(String email, String teamName, String name, String position, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_COL_8, phone);
        contentValues.put(TEAM_COL_7, name);
        contentValues.put(TEAM_COL_6, position);
        String whereClause = TEAM_COL_2 + " = ? AND " + TEAM_COL_1 + " = ?";
        String[] whereArgs = {teamName, email};
        int result = db.update(TEAM_TABLE_NAME, contentValues, whereClause, whereArgs);
        return (result > 0);
    }

    public String getPosition(String tname, String name, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String position = null;
        Cursor cursor = null;
        try {
            String[] columns = new String[]{TEAM_COL_6};
            String selection = TEAM_COL_2 + "=? AND " + TEAM_COL_7 + "=? AND " + TEAM_COL_1 + "=?";
            String[] selectionArgs = new String[]{tname, name, email};
            cursor = db.query(TEAM_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(TEAM_COL_6);
                if (index != -1) {
                    position = cursor.getString(index);
                }
            }

        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }



        return position;
    }

    public String getPhone(String team_name, String name, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {TEAM_COL_8};
        String selection = TEAM_COL_2 + " = ? AND " + TEAM_COL_7 + " = ? AND " + TEAM_COL_1 + " = ?";
        String[] selectionArgs = {team_name, name, email};
        String phone = "";
        Cursor cursor = db.query(TEAM_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(TEAM_COL_8);
            if (index != -1) {
                phone = cursor.getString(index);
            }
            cursor.close();
            return phone;
        } else {
            cursor.close();
            return null;
        }
    }

    public Cursor getMessagesFromTeam(String team_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MESSAGE, USERNAME FROM " + MESSAGES_TABLE_NAME + " WHERE TEAM_NAME = ?";
        Cursor res = db.rawQuery(query, new String[]{team_name});
        return res;
    }

    public String getName(String email, String teamName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";

        Cursor cursor = db.rawQuery("SELECT NAME FROM " + TEAM_TABLE_NAME + " WHERE UEMAIL = ? AND TNAME = ?", new String[]{email, teamName});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("NAME");
                name = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return name;
    }

    public String getCode(String email, String teamName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";

        Cursor cursor = db.rawQuery("SELECT TCODE FROM " + TEAM_TABLE_NAME + " WHERE UEMAIL = ? AND TNAME = ?", new String[]{email, teamName});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("TCODE");
                name = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return name;
    }

    public String getManagerStatus(String email, String teamName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";

        Cursor cursor = db.rawQuery("SELECT MSTATUS FROM " + TEAM_TABLE_NAME + " WHERE UEMAIL = ? AND TNAME = ?", new String[]{email, teamName});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("MSTATUS");
                name = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return name;
    }

    public Cursor getListOfEvents(String team_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT HEADING_E FROM " + EVENTS_TABLE_NAME + " WHERE TEAM_NAME = ?";
        Cursor res = db.rawQuery(query, new String[]{team_name});
        return res;
    }

    public String getTime(String teamName, String heading) {
        SQLiteDatabase db = this.getReadableDatabase();
        String time = "";

        Cursor cursor = db.rawQuery("SELECT TIME FROM " + EVENTS_TABLE_NAME + " WHERE HEADING_E = ? AND TEAM_NAME = ?", new String[]{heading, teamName});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("TIME");
                time = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return time;
    }

    public String getLocation(String teamName, String heading) {
        SQLiteDatabase db = this.getReadableDatabase();
        String location = "";

        Cursor cursor = db.rawQuery("SELECT LOCATION FROM " + EVENTS_TABLE_NAME + " WHERE HEADING_E = ? AND TEAM_NAME = ?", new String[]{heading, teamName});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("LOCATION");
                location = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return location;
    }

    public Cursor getListOfAnnouncements(String team_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT HEADING_A FROM " + ANNOUNCEMENTS_TABLE_NAME + " WHERE TEAM_NAME = ?";
        Cursor res = db.rawQuery(query, new String[]{team_name});
        return res;
    }

    public String getDescription(String teamName, String heading) {
        SQLiteDatabase db = this.getReadableDatabase();
        String description = "";

        Cursor cursor = db.rawQuery("SELECT DESCRIPTION FROM " + ANNOUNCEMENTS_TABLE_NAME + " WHERE HEADING_A = ? AND TEAM_NAME = ?", new String[]{heading, teamName});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("DESCRIPTION");
                description = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return description;
    }

    public String getEmail(String nameInput, String team_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";

        Cursor cursor = db.rawQuery("SELECT UEMAIL FROM " + TEAM_TABLE_NAME + " WHERE NAME = ? AND TNAME = ?", new String[]{nameInput, team_name});

        try {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("UEMAIL");
                name = cursor.getString(nameIndex);
            }
        } finally {
            cursor.close();
        }

        return name;
    }
}

