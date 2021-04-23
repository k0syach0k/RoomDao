package com.skillbox.roomdao.ui.user.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.skillbox.roomdao.R
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.ui.user.list.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_add_fab.*

class UserListFragment : Fragment(R.layout.fragment_add_fab) {
    private val viewModel by viewModels<UserListViewModel>()
    private var userAdapter: UserAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter(
            onItemClicked = { position ->
                val args = Bundle()
                args.putParcelable("user", viewModel.userLiveData.value?.get(position))
                findNavController().navigate(R.id.action_userListFragment_to_emailAddressListFragment, args)
            },
            onLongItemClicked = { position ->
                navigateToAddUserFragment(viewModel.userLiveData.value?.get(position))
            }
        )

        with(recyclerView) {
            this.adapter = userAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }

        addFAB.setOnClickListener {
            navigateToAddUserFragment(User(0, "", "", ""))
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            userAdapter?.submitList(it)
        }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
        }

        Handler(Looper.getMainLooper()).post {
            viewModel.getAllUsers()
        }
    }

    private fun setVisibility(visibility: Boolean) {
        recyclerView.isEnabled = visibility.not()
        addFAB.isEnabled = visibility.not()
        progressBar.isVisible = visibility
    }

    private fun navigateToAddUserFragment(user: User?) {
        val args = Bundle()
        args.putParcelable("user", user)
        findNavController().navigate(R.id.action_userListFragment_to_userAddFragment, args)
    }

    override fun onDestroy() {
        super.onDestroy()
        userAdapter = null
    }
}