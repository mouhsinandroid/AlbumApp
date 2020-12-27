package com.bourqaiba.leboncoin.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "album_item"
)
@Parcelize
data class AlbumItem (
    val albumId: String,
    @PrimaryKey
    val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String
    ): Parcelable