package com.skillbox.roomdao.ui.user.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.roomdao.model.user.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

class UserHolder(
    onItemClicked: (position: Int) -> Unit,
    onLongItemClicked: (position: Int) -> Unit,
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { onItemClicked(adapterPosition) }
        containerView.setOnLongClickListener {
            onLongItemClicked(adapterPosition)
            true
        }
    }

    fun bind(user: User) {
        val userDisplayName = user.firstName + ' ' + user.lastName
        textView.text = userDisplayName
    }
}
