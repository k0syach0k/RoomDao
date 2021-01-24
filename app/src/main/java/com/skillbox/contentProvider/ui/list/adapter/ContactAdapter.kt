package com.skillbox.contentProvider.ui.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.data.PartialContact
import com.skillbox.contentProvider.utils.inflate

class ContactAdapter(private val onItemClicked: (position: Int) -> Unit) : ListAdapter<PartialContact, ContactHolder>(
    ContactDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(onItemClicked, parent.inflate(R.layout.item_list_contact))
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<PartialContact>() {
        override fun areItemsTheSame(oldItem: PartialContact, newItem: PartialContact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PartialContact, newItem: PartialContact): Boolean {
            return oldItem == newItem
        }
    }
}
