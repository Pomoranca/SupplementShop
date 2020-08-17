package com.coffeetime.supplementshop.ui.home

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.coroutines.toDeferred
import com.coffeetime.supplementshop.AllUsersQuery
import com.coffeetime.supplementshop.LoginActivity
import com.coffeetime.supplementshop.network.User
import com.coffeetime.supplementshop.network.apolloClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {


    val context = application.applicationContext

    val viewModelJob = Job()

    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _users = MutableLiveData<List<AllUsersQuery.AllUser>>()

    val users: LiveData<List<AllUsersQuery.AllUser>>
        get() = _users

    init {
        showData()
    }


    private fun showData() {
        coroutineScope.launch {

            val response = apolloClient(context).query(AllUsersQuery()).toDeferred().await()

            if (response.hasErrors()) {
                Toast.makeText(context, response.errors().toString(), Toast.LENGTH_SHORT).show()
            }
            try {
                _users.value = response.data()!!.allUsers

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun logOutUser() {
        User.removeToken(context)
        val intent = Intent(
            context,
            LoginActivity::class.java
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(context, intent, null)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}