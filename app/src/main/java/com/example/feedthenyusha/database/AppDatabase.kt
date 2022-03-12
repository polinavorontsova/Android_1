package com.example.feedthenyusha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.feedthenyusha.database.dao.FeedResultDao
import com.example.feedthenyusha.database.model.FeedResult

@Database(entities = [FeedResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): FeedResultDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
