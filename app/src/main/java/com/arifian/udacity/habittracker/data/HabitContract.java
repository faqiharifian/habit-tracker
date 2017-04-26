package com.arifian.udacity.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by faqih on 23/04/17.
 */

public class HabitContract {
    private HabitContract(){}
    public static final class HabitEntry implements BaseColumns{
        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_HABIT_EVENT = "event";
        public static final String COLUMN_HABIT_TIMESTAMP = "timestamp";
    }
}
