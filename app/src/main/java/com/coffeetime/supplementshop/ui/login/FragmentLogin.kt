package com.coffeetime.supplementshop.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.coffeetime.supplementshop.MainActivity
import com.coffeetime.supplementshop.databinding.FragmentLoginBinding


class FragmentLogin : Fragment() {

    /** Create viewmodel instance first time it get's called*/
    val viewModel by lazy {
        ViewModelProvider(this).get(FragmentLoginViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLoginBinding.inflate(inflater)
        binding.viewModel
        binding.lifecycleOwner = this

        /** When user is logged in, go to Main Activity*/
        viewModel.navigateToHomeActivity.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                startActivity(
                    Intent(requireContext(), MainActivity::class.java)
                )
                viewModel.onNavigateToHomeActivityCompleted()
            }
        })
        binding.loginButton.setOnClickListener {
            val username = binding.userNameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.loginUser(username, password)
        }


        /** Button Register click event handling*/
        /** When button is clicked go to Register Activity*/
        viewModel.navigateToRegisterFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(FragmentLoginDirections.actionFragmentLoginToRegisterFragment())
                viewModel.onNavigateToRegisterFragmentCompleted()
            }
        })
        binding.registerButton.setOnClickListener {
            viewModel.onNavigateToRegisterFragment()
        }

        return binding.root
    }


}