package com.skillbox.github.ui.current_user

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.skillbox.github.R
import com.skillbox.github.models.UserProfile
import com.skillbox.github.utils.inflate

class UserAdapter() : ListAdapter<UserProfile, UserHolder>(UserAdapter.LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(parent.inflate(R.layout.item_user))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationDiffCallback : DiffUtil.ItemCallback<UserProfile>() {
        override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
            return oldItem == newItem
        }
    }
}
