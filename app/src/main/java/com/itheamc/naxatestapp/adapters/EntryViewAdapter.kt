package com.itheamc.naxatestapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itheamc.naxatestapp.databinding.EntryViewBinding
import com.itheamc.naxatestapp.models.Entry

private const val TAG = "EntryViewAdapter"

class EntryViewAdapter(val clickListener: (entry: Entry?) -> Unit) :
    PagingDataAdapter<Entry, EntryViewAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean =
                oldItem.id == newItem.id &&
                        oldItem.api == newItem.api &&
                        oldItem.desc == newItem.desc &&
                        oldItem.link == newItem.link &&
                        oldItem.category == newItem.category
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
            apiTextView.text = "API :  ${item?.api}"
            descTextVIew.text = "Descrition :  ${item?.desc}"
            authTextView.text = "Auth :  ${item?.auth}"
            httpsTextView.text = "Https :  ${item?.https}"
            corsTextVvew.text = "Cors :  ${item?.cors}"
            categoryTextVvew.text = "Category :  ${item?.category}"
            entryCard.setOnClickListener { clickListener(item) }
        }
    }
}