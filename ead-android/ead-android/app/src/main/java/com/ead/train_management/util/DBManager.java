package com.ead.train_management.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Data Base Helper class
 */
public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TravelModel.db";
    private static final int DATABASE_VERSION = 1;

    // Define your table and column names
    private static final String TABLE_USERS = "users";

    // SQL statement to create the users table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE users ( _id INTEGER PRIMARY KEY AUTOINCREMENT,nic TEXT,uid TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database version upgrades if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}