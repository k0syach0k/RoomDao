package com.skillbox.contentProvider.ui.detail

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.data.FullContact
import kotlinx.android.synthetic.main.fragment_detail.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<DetailViewModel>()

    private val args: DetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactNeverAskAgain,
                requiresPermission = { viewModel.loadContactData(args.partialContact.id) }
            ).launch()
        }

        deleteContactFAB.setOnClickListener {
            deleteContact()
        }

        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            bind(it)
        }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.contactSuccessfullyDeleted.observe(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }
    }

    private fun deleteContact() {
        constructPermissionsRequest(
            Manifest.permission.WRITE_CONTACTS,
            onShowRationale = ::onContactShowRationale,
            onPermissionDenied = ::onContactPermissionDenied,
            onNeverAskAgain = ::onContactNeverAskAgain,
            requiresPermission = {
                AlertDialog.Builder(this.context)
                    .setTitle(R.string.alertDialogTitle)
                    .setMessage(getString(R.string.alertDialogText))
                    .setPositiveButton(R.string.alertDialogOk) { dialog, _ ->
                        viewModel.deleteContactById(args.partialContact.id)
                        dialog.cancel()
                    }
                    .setNegativeButton(R.string.alertDialogCancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
        ).launch()
    }

    private fun setVisibility(visibility: Boolean) {
        deleteContactFAB.isVisible = visibility.not()
        nameTextView.isVisible = visibility.not()
        lastNameTextView.isVisible = visibility.not()
        emailTextView.isVisible = visibility.not()
        phoneNumberTextView.isVisible = visibility.not()
        progressBar.isVisible = visibility
    }

    private fun bind(fullContact: FullContact) {
        bindTextToView(fullContact.prefixName, prefixTextView)
        bindTextToView(fullContact.firstName, nameTextView)
        bindTextToView(fullContact.middleName, middleNameTextView)
        bindTextToView(fullContact.lastName, lastNameTextView)
        bindTextToView(fullContact.suffixName, suffixTextView)

        bindListToView(fullContact.phoneNumbers, phoneNumberTextView)
        bindListToView(fullContact.email, emailTextView)
    }

    private fun bindTextToView(text: String, view: TextView){
        if (text.isNotBlank()) {
            view.isVisible = true
            view.text = text
        } else view.isVisible = false
    }

    private fun bindListToView(list: List<String>, view: TextView){
        if (list.isNotEmpty()){
            view.isVisible = true
            view.text = list.joinToString(separator = "\n")
        } else view.isVisible = false
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
}
