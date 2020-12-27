package com.bourqaiba.leboncoin.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bourqaiba.leboncoin.data.database.dao.AlbumDao
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlbumItemDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AlbumDatabase
    private lateinit var dao: AlbumDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getAlbumDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAlbumItem() = runBlocking {
        val albumItem = AlbumItem(
            albumId = "10",
            id = "2",
            title = "Album 2",
            url = "www.album.com",
            thumbnailUrl = "www.ds.com"
        )


        dao.insertAlbumItem(albumItem)

        val album = dao.getAlbum()?.getOrAwaitValue()

        assertThat(album).contains(albumItem)

    }
}