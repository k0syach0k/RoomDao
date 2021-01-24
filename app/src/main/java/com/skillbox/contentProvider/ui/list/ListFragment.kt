package com.skillbox.contentProvider.ui.list

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.ui.list.adapter.ContactAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class ListFragment : Fragment(R.layout.fragment_list) {
    private val viewModel by viewModels<ListViewModel>()
    private var contactAdapter: ContactAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        contactAdapter = ContactAdapter { position ->
            val args = Bundle()
            args.putParcelable("partialContact", viewModel.contactsLiveData.value?.get(position))
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, args)
        }

        with(recyclerView) {
            this.adapter = contactAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }

        addContactFAB.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactNeverAskAgain,
                requiresPermission = { viewModel.loadContactList() }
            ).launch()
        }

        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            contactAdapter?.submitList(it)
        }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
        }
    }

    private fun setVisibility(visibility: Boolean) {
        recyclerView.isEnabled = visibility.not()
        addContactFAB.isEnabled = visibility.not()
        progressBar.isVisible = visibility
    }

    private fun onContactShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionDenied() {
        Toast.makeText(requireContext(), R.string.contactPermissionDeniedToast, Toast.LENGTH_LONG)
            .show()
    }

    private fun onContactNeverAskAgain() {
        Toast.makeText(requireContext(), R.string.contactPermissionNeverAskAgain, Toast.LENGTH_LONG)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        contactAdapter = null
    }
}
