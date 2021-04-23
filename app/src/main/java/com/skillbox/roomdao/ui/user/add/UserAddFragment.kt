package com.skillbox.roomdao.ui.user.add

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
import com.skillbox.roomdao.model.user.User
import kotlinx.android.synthetic.main.fragment_add_user.*

class UserAddFragment: Fragment(R.layout.fragment_add_user) {

    private val args: UserAddFragmentArgs by navArgs()
    private val viewModel by viewModels<UserAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (args.user.id == 0){
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.addUserFragmentTitle)
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.editUserFragmentTitle)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteButton.isEnabled = args.user.id != 0

        if (savedInstanceState == null) initTextView(args.user)

        submitButton.setOnClickListener {
            viewModel.saveUser(
                User(
                    args.user.id,
                    firstNameEditText.text.toString(),
                    lastNameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }

        deleteButton.setOnClickListener { viewModel.deleteUser(args.user) }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setVisibility(it)
            if (it.not()) deleteButton.isEnabled = args.user.id != 0
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.userDeleted.observe(viewLifecycleOwner){
            if (it) findNavController().popBackStack()
        }

    }

    private fun initTextView(user: User){
        if (user.id != 0){
            firstNameEditText.setText(user.firstName)
            lastNameEditText.setText(user.lastName)
            passwordEditText.setText(user.passwordHash)
        }
    }

    private fun setVisibility(visibility: Boolean) {
        firstNameEditText.isEnabled = visibility.not()
        lastNameEditText.isEnabled = visibility.not()
        passwordEditText.isEnabled = visibility.not()
        submitButton.isEnabled = visibility.not()
        deleteButton.isEnabled = visibility.not()
        progressBar.isVisible = visibility
    }
}