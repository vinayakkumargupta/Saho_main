package com.example.saho_main.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "User.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DatabaseContract.UserEntry.TABLE_NAME} (" +
                    "${DatabaseContract.UserEntry._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContract.UserEntry.COLUMN_NAME_USERNAME} TEXT," +
                    "${DatabaseContract.UserEntry.COLUMN_NAME_PASSWORD} TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseContract.UserEntry.TABLE_NAME}"
    }
}


