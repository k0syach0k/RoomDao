package com.skillbox.roomdao.ui.email_address.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.skillbox.roomdao.R
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.ui.email_address.list.adapter.EmailAdapter
import kotlinx.android.synthetic.main.fragment_add_fab.*

class EmailAddressListFragment: Fragment(R.layout.fragment_add_fab) {

    private val viewModel by viewModels<EmailAddressListViewModel>()
    private var emailAdapter: EmailAdapter? = null
    private val args: EmailAddressListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailAdapter = EmailAdapter(
            onItemClicked = {

            },
            onLongItemClicked = { position ->
                navigateToAddEmailAddressFragment(viewModel.emailLiveData.value?.get(position))
            }
        )

        with(recyclerView) {
            this.adapter = emailAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }

        addFAB.setOnClickListener {
            navigateToAddEmailAddressFragment(EmailAddress(0, args.user.id, ""))
        }

        viewModel.emailLiveData.observe(viewLifecycleOwner){
            emailAdapter?.submitList(it)
        }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
        }

        Handler(Looper.getMainLooper()).post {
            viewModel.getAllEmailAddressForUser(args.user)
        }
    }

    private fun setVisibility(visibility: Boolean) {
        recyclerView.isEnabled = visibility.not()
        addFAB.isEnabled = visibility.not()
        progressBar.isVisible = visibility
    }

    private fun navigateToAddEmailAddressFragment(emailAddress: EmailAddress?) {
        val args = Bundle()
        args.putParcelable("emailAddress", emailAddress)
        findNavController().navigate(R.id.action_emailAddressListFragment_to_emailAddressAddFragment, args)
    }

    override fun onDestroy() {
        super.onDestroy()
        emailAdapter = null
    }
}