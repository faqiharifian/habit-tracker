package com.arifian.udacity.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.arifian.udacity.habittracker.data.HabitContract.*;

/**
 * Created by faqih on 23/04/17.
 */

public class HabitDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "habit.db";
    public static final int DATABASE_VERSION = 1;

    public HabitDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE "+ HabitEntry.TABLE_NAME +" ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_EVENT + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_TIMESTAMP + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME +";");
        onCreate(db);
    }
}
