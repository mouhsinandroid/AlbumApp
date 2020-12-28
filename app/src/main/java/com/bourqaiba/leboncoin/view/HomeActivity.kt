package com.bourqaiba.leboncoin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.AlbumDatabase
import com.bourqaiba.leboncoin.data.repository.AlbumRepository
import com.bourqaiba.leboncoin.viewmodel.factory.AlbumViewModelFactory
import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
    }

    private fun initViewModel() {
        val repository = AlbumRepository(AlbumDatabase(this))
        val viewModelProviderFactory = AlbumViewModelFactory(this.application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            AlbumViewModel::class.java)
    }
}