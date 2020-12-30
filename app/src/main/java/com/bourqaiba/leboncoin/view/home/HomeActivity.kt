package com.bourqaiba.leboncoin.view.home

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bourqaiba.leboncoin.BuildConfig
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.AlbumDatabase
import com.bourqaiba.leboncoin.data.repository.AlbumRepository
import com.bourqaiba.leboncoin.viewmodel.factory.AlbumViewModelFactory
import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel
import timber.log.Timber

class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.albumFragment -> { setToolbarColor(R.color.colorPrimary)}
                R.id.albumDetailFragment -> { setToolbarColor(R.color.colorPrimary)}}}


        initViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun setToolbarColor(colorId : Int){
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, colorId)))
    }

    private fun initViewModel() {
        val repository = AlbumRepository(AlbumDatabase(this))
        val viewModelProviderFactory = AlbumViewModelFactory(this.application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            AlbumViewModel::class.java)
    }
}