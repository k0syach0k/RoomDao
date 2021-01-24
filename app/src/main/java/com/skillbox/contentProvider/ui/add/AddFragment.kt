package com.skillbox.contentProvider.ui.add

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.data.FullContact
import kotlinx.android.synthetic.main.fragment_add.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class AddFragment : Fragment(R.layout.fragment_add) {

    private val viewModel by viewModels<AddViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        submitButton.setOnClickListener {
            constructPermissionsRequest(
                Manifest.permission.WRITE_CONTACTS,
                onShowRationale = ::onContactShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactNeverAskAgain,
                requiresPermission = {
                    viewModel.saveContact(
                        FullContact(
                            0,
                            prefixNameEditText.text.toString(),
                            nameEditText.text.toString(),
                            middleNameEditText.text.toString(),
                            lastNameEditText.text.toString(),
                            suffixNameEditText.text.toString(),
                            listOf(phoneNumberEditText.text.toString()),
                            listOf(emailEditText.text.toString())
                        )
                    )
                }
            ).launch()
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

        viewModel.contactSuccessfullyAdded.observe(viewLifecycleOwner){
            if (it) {
                nameEditText.text.clear()
                lastNameEditText.text.clear()
                phoneNumberEditText.text.clear()
                emailEditText.text.clear()
            }
        }
    }

    private fun setVisibility(visibility: Boolean) {
        progressBar.isVisible = visibility
        nameEditText.isEnabled = visibility.not()
        lastNameEditText.isEnabled = visibility.not()
        phoneNumberEditText.isEnabled = visibility.not()
        emailEditText.isEnabled = visibility.not()
        submitButton.isEnabled = visibility.not()
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
