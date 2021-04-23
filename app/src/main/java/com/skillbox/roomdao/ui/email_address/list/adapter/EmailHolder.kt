package com.skillbox.roomdao.ui.email_address.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.roomdao.model.email_address.EmailAddress
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

class EmailHolder(
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

    fun bind(email: EmailAddress) {
        textView.text = email.emailAddress
    }
}
