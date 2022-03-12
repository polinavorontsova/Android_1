package com.example.feedthenyusha.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_results")
data class FeedResult(
    @NonNull @ColumnInfo(name = "player_name") val playerName: String,
    @NonNull @ColumnInfo(name = "time") val time: Long,
    @NonNull @ColumnInfo(name = "satiety") val satiety: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
