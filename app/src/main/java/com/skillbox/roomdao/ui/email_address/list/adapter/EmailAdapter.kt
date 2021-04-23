package com.skillbox.roomdao.ui.email_address.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.skillbox.roomdao.R
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.utils.inflate

class EmailAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    private val onLongItemClicked: (position: Int) -> Unit
) : ListAdapter<EmailAddress, EmailHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailHolder {
        return EmailHolder(onItemClicked, onLongItemClicked, parent.inflate(R.layout.item_list))
    }

    override fun onBindViewHolder(holder: EmailHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<EmailAddress>() {
        override fun areItemsTheSame(oldItem: EmailAddress, newItem: EmailAddress): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EmailAddress, newItem: EmailAddress): Boolean {
            return oldItem == newItem
        }
    }
}
