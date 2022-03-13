package com.example.feedthenyusha.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.feedthenyusha.database.model.FeedResult
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedResultDao {
    @Query("SELECT * FROM feed_results WHERE player_name = :playerName ORDER BY time DESC LIMIT 1")
    fun getLastResultByPlayerName(playerName: String): Flow<FeedResult?>

    @Query("SELECT * FROM feed_results WHERE player_name = :playerName ORDER BY time DESC")
    fun getResultsByPlayerName(playerName: String): Flow<List<FeedResult>>

    @Insert(entity = FeedResult::class)
    fun insertNewFeedResult(feedResult: FeedResult)
}
