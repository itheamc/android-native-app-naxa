package com.itheamc.naxatestapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itheamc.naxatestapp.databinding.EntryViewBinding
import com.itheamc.naxatestapp.models.Entry

class EntryViewAdapter : PagingDataAdapter<Entry, EntryViewAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean =
                oldItem == newItem
        }
    }

    inner class MainViewHolder(val binding: EntryViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            EntryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            apiText.text = item?.api ?: "Hello"
            apiDesc.text = item?.desc ?: "n/a"
        }
    }
}