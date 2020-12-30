package com.bourqaiba.leboncoin.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.databinding.AlbumItemBinding

class AlbumAdapter: RecyclerView.Adapter<AlbumAdapter.AlbumItemViewHolder>(),
    AlbumItemClickListener {
    inner class AlbumItemViewHolder(var view: AlbumItemBinding): RecyclerView.ViewHolder(view.root)

    private val differCallback = object: DiffUtil
    .ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(
                oldItem: AlbumItem,
                newItem: AlbumItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
                oldItem: AlbumItem,
                newItem: AlbumItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<AlbumItemBinding>(inflater, R.layout.album_item, parent, false)

        return AlbumItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        holder.view.albumItem = differ.currentList[position]
        holder.view.listener = this

    }


    override fun onClick(v: View) {
        goToDetails(v)
    }

    private fun goToDetails(v: View) {
        for (item in differ.currentList) {
            if (v.tag == item.title) {
                val action = AlbumsFragmentDirections.actionDetail(item)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }



}