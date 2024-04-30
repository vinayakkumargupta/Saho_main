package com.example.saho_main.Database

// DatabaseInitializer.kt
import android.content.Context
import androidx.room.Room

object DatabaseInitializer {
    private lateinit var database: AppDatabase

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }
}
