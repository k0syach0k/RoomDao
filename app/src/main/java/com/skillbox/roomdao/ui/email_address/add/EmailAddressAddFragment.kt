package com.skillbox.roomdao.ui.email_address.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.skillbox.roomdao.R
import com.skillbox.roomdao.model.email_address.EmailAddress
import kotlinx.android.synthetic.main.fragment_add_email_address.*

class EmailAddressAddFragment: Fragment(R.layout.fragment_add_email_address) {

    private val args: EmailAddressAddFragmentArgs by navArgs()
    private val viewModel by viewModels<EmailAddressAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (args.emailAddress.id == 0){
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.addEmailAddressFragmentTitle)
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.editEmailAddressFragmentTitle)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteButton.isEnabled = args.emailAddress.id != 0

        if (savedInstanceState == null && args.emailAddress.id != 0)
            emailAddressEditText.setText( args.emailAddress.emailAddress )

        submitButton.setOnClickListener {
            viewModel.saveEmailAddress(
                EmailAddress(
                    args.emailAddress.id,
                    args.emailAddress.userId,
                    emailAddressEditText.text.toString()
                )
            )
        }

        deleteButton.setOnClickListener { viewModel.deleteEmailAddress(args.emailAddress) }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
            if (it.not()) deleteButton.isEnabled = args.emailAddress.id != 0
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.emailAddressDeleted.observe(viewLifecycleOwner){
            if (it) findNavController().popBackStack()
        }
    }

    private fun setVisibility(visibility: Boolean) {
        emailAddressEditText.isEnabled = visibility.not()
        submitButton.isEnabled = visibility.not()
        deleteButton.isEnabled = visibility.not()
        progressBar.isVisible = visibility
    }
}