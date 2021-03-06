package com.bourqaiba.leboncoin.view.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.util.Status
import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_albums.*
import timber.log.Timber


class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val TAG = AlbumsFragment::class.java.simpleName

    private lateinit var albumViewModel: AlbumViewModel

    private lateinit var albumAdapter: AlbumAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumViewModel = (activity as HomeActivity).viewModel
        albumViewModel.refreshData()
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
                        albumAdapter.differ.submitList(albumsResponse)
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

                        snack.setAction(getString(R.string.try_again)) {
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
        albumViewModel.getAlbumFromLocal()
                ?.observe(viewLifecycleOwner) { albums ->
                    if (albums != null) {
                        albumAdapter.differ.submitList(albums)
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