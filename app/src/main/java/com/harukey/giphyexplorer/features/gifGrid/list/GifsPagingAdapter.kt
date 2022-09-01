package com.harukey.giphyexplorer.features.gifGrid.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harukey.giphyexplorer.databinding.GifGridItemBinding
import com.harukey.giphyexplorer.features.gifGrid.model.GifImageGridItemModel

class GifsPagingAdapter :
    PagingDataAdapter<GifImageGridItemModel, GifsPagingAdapter.GifsViewHolder>(GifImageDiffUtil()) {

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding = GifGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding)
    }

    class GifsViewHolder(
        private val binding: GifGridItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GifImageGridItemModel?) {
            item?.also {
                Glide.with(binding.gif)
                    .load(it.previewUrl)
                    .into(binding.gif)
            } ?: run {
                binding.gif.setImageBitmap(null)
            }
        }
    }

    private class GifImageDiffUtil : DiffUtil.ItemCallback<GifImageGridItemModel>() {
        override fun areItemsTheSame(oldItem: GifImageGridItemModel, newItem: GifImageGridItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifImageGridItemModel, newItem: GifImageGridItemModel): Boolean {
            return oldItem == newItem
        }

    }
}
