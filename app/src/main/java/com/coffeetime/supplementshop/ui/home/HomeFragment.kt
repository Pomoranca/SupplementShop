package com.coffeetime.supplementshop.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.toDeferred
import com.coffeetime.supplementshop.AllUsersQuery
import com.coffeetime.supplementshop.databinding.FragmentHomeBinding
import com.coffeetime.supplementshop.network.User
import com.coffeetime.supplementshop.network.apolloClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.channels.Channel
import java.lang.Exception


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        requireActivity().bottomNavigation.visibility = View.VISIBLE


        viewModel.users.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val adapter = ResultListAdapter(it)
                binding.homeFeed.adapter = adapter

                adapter.notifyDataSetChanged()
            }
        })

        binding.logOutButton.setOnClickListener {
            viewModel.logOutUser()
        }

        return binding.root
    }


}