package com.coffeetime.supplementshop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coffeetime.supplementshop.AllUsersQuery
import com.coffeetime.supplementshop.R
import com.coffeetime.supplementshop.databinding.ResultItemBinding

class ResultListAdapter(private val feed: List<AllUsersQuery.AllUser>) :
    RecyclerView.Adapter<ResultListAdapter.ViewHolder>() {


    class ViewHolder(val binding: ResultItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return feed.size

    }

    var onEndOfListReached: (() -> Unit)? = null
    var onItemClicked: ((AllUsersQuery.AllUser) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = feed[position]
        holder.binding.resultName.text = result.name
        holder.binding.resultSite.text = result.id

        Glide.with(holder.itemView.context)
            .load(R.drawable.user)
            .into(holder.binding.missionPatch)

        if (position == feed.size - 1) {
//            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(result)
        }
    }
}