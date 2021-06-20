package edu.uoc.pac4.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.uoc.pac4.data.streams.datasource.StreamDao
import edu.uoc.pac4.data.streams.datasource.model.StreamDbModel

/**
 * Created by alex on 20/6/21.
 */
@Database(entities = arrayOf(StreamDbModel::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun streamDao(): StreamDao
}