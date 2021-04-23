package com.skillbox.roomdao.ui.user.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.skillbox.roomdao.R
import com.skillbox.roomdao.utils.inflate

import com.skillbox.roomdao.model.user.User

class UserAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    private val onLongItemClicked: (position: Int) -> Unit
) : ListAdapter<User, UserHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(onItemClicked, onLongItemClicked, parent.inflate(R.layout.item_list))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
