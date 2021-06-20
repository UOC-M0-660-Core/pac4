package edu.uoc.pac4.data.streams.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.uoc.pac4.data.streams.datasource.model.StreamDbModel

/**
 * Created by alex on 20/6/21.
 */
@Dao
interface StreamDao {
    @Query("SELECT * FROM streamdbmodel")
    suspend fun getAll(): List<StreamDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(streams: List<StreamDbModel>)

    @Query("DELETE FROM streamdbmodel")
    fun deleteAll()
}