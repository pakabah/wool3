package com.overcoretech.troski.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pakabah on 27/05/16.
 */
public class DBHelper {


    class Troski extends SQLiteOpenHelper
    {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "troski.db";

        public static final String TABLE_TERMINALS = "terminals";
        public static final String COLUMN_TERMINAL_ID = "_id";
        public static final String COLUMN_AGENCY_NAME = "agency_name";
        public static final String COLUMN_AGENCY_ID = "agency_id";


        public static final String DATABASE_AGENCY = " create table "
                + TABLE_TERMINALS + "("+ COLUMN_TERMINAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AGENCY_ID + " text, "
                + COLUMN_AGENCY_NAME + "text);";


        public Troski(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_AGENCY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMINALS);
            this.onCreate(db);
        }
    }
}
