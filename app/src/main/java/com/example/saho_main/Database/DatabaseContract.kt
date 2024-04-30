package com.example.saho_main.Database

import android.provider.BaseColumns

object DatabaseContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val _ID = BaseColumns._ID
    }
}
