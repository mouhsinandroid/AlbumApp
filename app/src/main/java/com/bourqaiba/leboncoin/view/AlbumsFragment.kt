package com.bourqaiba.leboncoin.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.databinding.FragmentAlbumsBinding
import com.bourqaiba.leboncoin.util.Resource
import com.bourqaiba.leboncoin.util.Status
import com.bourqaiba.leboncoin.view.adapter.AlbumAdapter
import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_albums.*
import timber.log.Timber


class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val TAG = AlbumsFragment::class.java.simpleName

    private lateinit var dataBinding: FragmentAlbumsBinding
    private lateinit var albumViewModel: AlbumViewModel

    private lateinit var albumAdapter: AlbumAdapter

    private val albumListDataObserver = Observer<Resource<Album>>{ list ->
        list?.let {
            albumAdapter.differ.submitList(it.data)
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumViewModel = (activity as HomeActivity).viewModel
        albumViewModel.album.observe(viewLifecycleOwner, albumListDataObserver)
        setupUI()
    }



    private fun setupUI() {
        setupRecyclerView()
        fetchAlbumsData()
        refresh()
    }

    private fun fetchAlbumsData() {
        albumViewModel.album.observe(viewLifecycleOwner, { response ->

            when (response.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    response.data?.let { albumsResponse ->
                        Timber.tag("albums").d("Size ::$albumsResponse.size")
                        for (item in albumsResponse) {
                            albumViewModel.saveAlbumItem(item)
                        }
                    }
                }
                Status.ERROR -> {
                    hideProgressBar()
                    getListAlbumsFromLocal()
                    response.message?.let {
                        Timber.tag(TAG).e(it)

                        val snack = Snackbar.make(
                            refreshLayout,
                            it,
                            Snackbar.LENGTH_LONG
                        )

                        snack.setAction("Try again") {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
                                snack.dismiss()
                            }
                        }

                        snack.show()
                    }
                }

                Status.LOADING -> {
                    showProgressBar()

                }

            }

        })
    }


    private fun setupRecyclerView() {
        albumAdapter = AlbumAdapter()
        albumList.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = albumAdapter

        }
    }

    private fun refresh() {
        refreshLayout.setOnRefreshListener {
            getAlbums()
            refreshLayout.isRefreshing = false
        }
    }

    private fun getListAlbumsFromLocal() {
        albumViewModel.getLocalAlbums()
                ?.observe(viewLifecycleOwner) { albums ->
                    if (albums != null) {
                        albumAdapter.differ.submitList(albums)
                        albumAdapter.notifyDataSetChanged()
                    }
                }

    }


    private fun getAlbums() {
        albumViewModel.getAlbum()
    }

    private fun hideProgressBar() {
        loadingProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        loadingProgressBar.visibility = View.VISIBLE
    }


}