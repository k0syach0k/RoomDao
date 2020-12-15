package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.models.UserProfile
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CurrentUserFragment : Fragment(R.layout.fragment_user) {
    private val userViewModel: CurrentUserViewModel by viewModels()
    private var userAdapter: UserAdapter? = null

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userAdapter = UserAdapter()
        with(recyclerView) {
            this.adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }

        userViewModel.exception.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        userViewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
        }

        userViewModel.user.observe(viewLifecycleOwner) {
            bindUserProfile(it)
        }

        userViewModel.followingUsers.observe(viewLifecycleOwner) {
            userAdapter?.submitList(it)
        }

        userViewModel.getData()
    }

    private fun bindUserProfile(user: UserProfile) {
        Glide.with(this)
            .load(user.avatar)
            .placeholder(R.drawable.ic_no_image)
            .into(avatarImageView)
        ownerNameTextView.text = user.login
        locationTextView.text = user.location
    }

    private fun setVisibility(visibility: Boolean) {
        avatarImageView.isVisible = visibility.not()
        ownerNameTextView.isVisible = visibility.not()
        locationTextView.isVisible = visibility.not()
        progressBar.isVisible = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        userAdapter = null
    }
}
