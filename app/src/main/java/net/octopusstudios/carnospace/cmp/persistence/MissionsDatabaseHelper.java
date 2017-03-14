package net.octopusstudios.carnospace.cmp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.octopusstudios.carnospace.cmp.pojo.Mission;
import net.octopusstudios.carnospace.cmp.pojo.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Davide on 14/03/2017.
 */

public class MissionsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CarnoSpaceMissions";
    private static final int DATABASE_VERSION = 1;

    /** Mission table definition */
    private static final String TABLE_MISSION = "MISSION";
    private static final String K_MISSION_ID = "MISSION_ID";
    private static final String K_MISSION_NAME = "MISSION_NAME";
    private static final String K_MISSION_COST = "MISSION_COST";
    private static final String K_MISSION_DATE = "MISSION_DATE";

    private static final String CREATE_MISSION_TABLE =
            "CREATE TABLE " + TABLE_MISSION + " ( " +
            K_MISSION_ID + " INTEGER PRIMARY KEY, " +
            K_MISSION_NAME + " TEXT, " +
            K_MISSION_COST + " INTEGER, " +
            K_MISSION_DATE + " DATETIME" +
            " )";

    /** Stage table definition */
    private static final String TABLE_STAGE = "STAGE";
    private static final String K_STAGE_ID = "STAGE_ID";
    private static final String K_STAGE_NAME = "STAGE_NAME";
    private static final String K_STAGE_DIFFICULTY = "STAGE_DIFFICULTY";
    private static final String K_STAGE_PAYLOAD = "STAGE_PAYLOAD";
    private static final String K_STAGE_ROCKET_MASS = "STAGE_ROCKET_MASS";
    private static final String K_STAGE_COST = "STAGE_COST";
    private static final String K_STAGE_ROCKET_LIST = "STAGE_ROCKET_LIST";

    private static final String CREATE_STAGE_TABLE =
            "CREATE TABLE " + TABLE_STAGE + " ( " +
                    K_STAGE_ID + " INTEGER PRIMARY KEY, " +
                    K_STAGE_NAME + " TEXT, " +
                    K_STAGE_DIFFICULTY + " INTEGER, " +
                    K_STAGE_ROCKET_MASS + " INTEGER, " +
                    K_STAGE_PAYLOAD + " INTEGER, " +
                    K_STAGE_COST + " INTEGER, " +
                    K_STAGE_ROCKET_LIST + " TEXT" +
                    " )";

    public MissionsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MISSION_TABLE);
        db.execSQL(CREATE_STAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAGE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSION);

            // create new tables
            onCreate(db);
        }
    }

    public void saveMission(Mission mission) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(K_MISSION_NAME, mission.getName());
        values.put(K_MISSION_COST, mission.getTotalCost());
        values.put(K_MISSION_DATE, dateToString(mission.getDate().getTime()));

        // insert row
        db.insert(TABLE_MISSION, null, values);
    }

    public List<Mission> getMissionsList() {
        List<Mission> missions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_MISSION + " ORDER BY " + K_MISSION_DATE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mission m = new Mission();
                m.setName(c.getString(c.getColumnIndex(K_MISSION_NAME)));
                m.setTotalCost(c.getInt(c.getColumnIndex(K_MISSION_COST)));
                m.setDate(stringToDate(c.getString(c.getColumnIndex(K_MISSION_DATE))));
                m.setMissionStages(new ArrayList<Stage>()); //TODO this is going to be fun...

                missions.add(m);
            } while (c.moveToNext());
        }

        return missions;
    }

    private String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private Calendar stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date parsedDate = dateFormat.parse(date);
            Calendar cal = new GregorianCalendar();
            cal.setTime(parsedDate);
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
