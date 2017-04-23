package com.arifian.udacity.habittracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arifian.udacity.habittracker.data.HabitContract;
import com.arifian.udacity.habittracker.data.HabitDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private HabitDBHelper mDbHelper;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(dialogView);

        builder.setTitle(getString(R.string.whats_happen));
        builder.setPositiveButton(getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) dialogView.findViewById(R.id.input_event);
                insertPet(editText.getText().toString());
                displayDatabaseInfo();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_button_negative), null);

        builder.setCancelable(true);
        dialog = builder.create();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        mDbHelper = new HabitDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,   // The table to query
                null,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_EVENT + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_EVENT);
            int breedColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP);

            SimpleDateFormat sdfFormatter = new SimpleDateFormat("HH:mm:ss dd/MMM/yyyy");
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                Date date = new Date();
                date.setTime(Long.valueOf(currentBreed));

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        sdfFormatter.format(date)));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertPet(String event) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_EVENT, event);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP, String.valueOf((new Date()).getTime()));

        db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }
}
