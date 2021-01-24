package com.skillbox.contentProvider.ui.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.contentProvider.data.PartialContact
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_contact.*

class ContactHolder(
    onItemClicked: (position: Int) -> Unit,
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { onItemClicked(adapterPosition) }
    }

    fun bind(partialContact: PartialContact) {
        nameTextView.text = partialContact.displayName
        /*lastNameTextView.text = contact.lastName
        phoneNumberTextView.text = contact.phoneNumbers.joinToString(separator = "\n")*/
    }
}
