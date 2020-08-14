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

        viewModel.navigateToHomeActivity.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                startActivity(
                    Intent(requireContext(), MainActivity::class.java)
                )
                viewModel.onNavigateToHomeActivityCompleted()
            }
        })

        viewModel.navigateToRegisterFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(FragmentLoginDirections.actionFragmentLoginToRegisterFragment())
                viewModel.onNavigateToRegisterFragmentCompleted()
            }
        })

        binding.loginButton.setOnClickListener {
            val username = binding.userNameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.loginUser(username, password)
        }

        binding.registerButton.setOnClickListener {
            viewModel.onNavigateToRegisterFragment()
        }

        return binding.root
    }


}