package com.coffeetime.supplementshop.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.toDeferred
import com.coffeetime.supplementshop.AllUsersQuery
import com.coffeetime.supplementshop.databinding.FragmentHomeBinding
import com.coffeetime.supplementshop.network.User
import com.coffeetime.supplementshop.network.apolloClient
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

        val feed = mutableListOf<AllUsersQuery.AllUser>()
        val adapter = ResultListAdapter(feed)
        binding.homeFeed.adapter = adapter
        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.offer(Unit)
        adapter.onEndOfListReached = {
            channel.offer(Unit)
        }


        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                val response = try {

                    apolloClient(requireContext()).query(AllUsersQuery()).toDeferred().await()

                } catch (e: Exception) {
                    e.printStackTrace()

                    return@launchWhenResumed
                }

                val newFeed = response.data()!!.allUsers

                Log.i("RESPONSES", newFeed[0].toString())

                feed.addAll(newFeed)
                adapter.notifyDataSetChanged()
            }
        }



        return binding.root
    }


}