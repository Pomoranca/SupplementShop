package com.coffeetime.supplementshop

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


/** Binding adapter class to work with Data Binding*/
class BindingAdapter {

    @BindingAdapter("name")
    fun TextView.setName(item: AllUsersQuery.AllUser) {
        text = item.name
    }

    @BindingAdapter("listData")
    fun bindRecyclerView(
        recyclerView: RecyclerView,
        data: List<AllUsersQuery.AllUser>
    ) {

    }


}