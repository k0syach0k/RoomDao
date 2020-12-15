package com.skillbox.github.ui.current_user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.models.UserProfile
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*

class UserHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(user: UserProfile) {
        ownerNameTextView.text = user.login
        Glide.with(containerView)
            .load(user.avatar)
            .placeholder(R.drawable.ic_no_image)
            .into(avatarImageView)
    }
}
