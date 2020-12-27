package com.bourqaiba.leboncoin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bourqaiba.leboncoin.data.database.dao.AlbumDao
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem

@Database(
    entities = [AlbumItem::class],
    version = 1
)
abstract class AlbumDatabase: RoomDatabase() {
    abstract fun getAlbumDao(): AlbumDao

    companion object {
        @Volatile
        private var instance : AlbumDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also{ instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AlbumDatabase::class.java,
                "album_db.db")
                .build()
    }
}