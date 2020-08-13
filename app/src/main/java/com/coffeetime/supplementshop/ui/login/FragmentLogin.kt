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

        viewModel.navigateToOverviewFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(FragmentLoginDirections.actionFragmentLoginToHomeFragment())
                viewModel.onNavigateToOverviewFragmentCompleted()
            }
        })

        binding.loginButton.setOnClickListener {
            viewModel.loginUser("aaa", "aaa")
        }

        return binding.root
    }


}