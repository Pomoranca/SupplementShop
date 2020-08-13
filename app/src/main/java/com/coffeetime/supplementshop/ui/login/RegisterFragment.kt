package com.coffeetime.supplementshop.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.coffeetime.supplementshop.R
import com.coffeetime.supplementshop.databinding.FragmentRegisterBinding

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


        return binding.root
    }

}