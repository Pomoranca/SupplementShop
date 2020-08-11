package com.coffeetime.supplementshop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coffeetime.supplementshop.FeedResultQuery
import com.coffeetime.supplementshop.databinding.ResultItemBinding

class ResultListAdapter(val feed: List<FeedResultQuery.Result>) :
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
    var onItemClicked: ((FeedResultQuery.Result) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = feed[position]
        holder.binding.resultName.text = result.name
        holder.binding.resultSite.text = result.species

        Glide.with(holder.itemView.context)
            .load(result.image)
            .into(holder.binding.missionPatch)

        if (position == feed.size - 1) {
            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(result)
        }
    }
}