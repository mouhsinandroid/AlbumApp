package com.bourqaiba.leboncoin.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.databinding.FragmentAlbumDetailBinding


class AlbumDetailFragment : Fragment() {

    private var itemAlbum: AlbumItem? = null
    private lateinit var dataBinding: FragmentAlbumDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_album_detail,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            itemAlbum = AlbumDetailFragmentArgs.fromBundle(it).albumItem
        }

        dataBinding.albumItem = itemAlbum

    }
}