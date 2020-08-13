package com.coffeetime.supplementshop.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.coffeetime.supplementshop.R
import com.coffeetime.supplementshop.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.result_item.*

class RegisterFragment : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this).get(FragmentRegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentRegisterBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = nameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.registerUser(name, username, password)
        }

        viewModel.navigateToLoginFragment.observe(viewLifecycleOwner, Observer {
            if(it == true){
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToFragmentLogin())
                viewModel.onNavigateToLoginFragmentCompleted()
            }
        })


        return binding.root
    }

}