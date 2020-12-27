package com.bourqaiba.leboncoin.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.AlbumDatabase
import com.bourqaiba.leboncoin.data.repository.AlbumRepository
import com.bourqaiba.leboncoin.databinding.FragmentAlbumsBinding
import com.bourqaiba.leboncoin.util.Status
import com.bourqaiba.leboncoin.view.adapter.AlbumAdapter
import com.bourqaiba.leboncoin.viewmodel.factory.AlbumViewModelFactory
import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel
import kotlinx.android.synthetic.main.fragment_albums.*
import timber.log.Timber


class AlbumsFragment : Fragment() {
    private val TAG = AlbumsFragment::class.java.simpleName

    private lateinit var dataBinding: FragmentAlbumsBinding
    private lateinit var albumViewModel: AlbumViewModel

    private lateinit var albumAdapter: AlbumAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_albums,
            container,
            false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel(requireActivity().applicationContext)

        setupUI()
    }



    private fun initViewModel(context: Context) {
        val repository = AlbumRepository(AlbumDatabase(context))
        val viewModelProviderFactory = AlbumViewModelFactory(requireActivity().application, repository)
        albumViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            AlbumViewModel::class.java)

        albumViewModel.getAlbum()

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
                        Timber.tag("albums").d("Size ::"+albumsResponse.size)
                        for (item in albumsResponse) {
                            albumViewModel.saveAlbumItem(item)
                            getListAlbumsFromLocal()
                        }
                    }
                }
                Status.ERROR -> {
                    hideProgressBar()
                    getListAlbumsFromLocal()
                    response.message?.let {
                        Timber.tag(TAG).e(it)
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
            adapter = albumAdapter
            layoutManager = GridLayoutManager(activity, 2)

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