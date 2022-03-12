package com.example.feedthenyusha

import android.app.Application
import com.example.feedthenyusha.database.AppDatabase

class FeedTheCatApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}