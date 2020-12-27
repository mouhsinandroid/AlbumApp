package com.bourqaiba.leboncoin.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumItem(albumItem: AlbumItem): Long

    @Query("SELECT * FROM album_item GROUP BY albumId")
    fun getAlbum(): LiveData<List<AlbumItem>>?
}