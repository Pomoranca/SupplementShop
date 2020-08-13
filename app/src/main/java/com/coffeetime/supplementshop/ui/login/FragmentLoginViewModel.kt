package com.coffeetime.supplementshop.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.coffeetime.supplementshop.SignInUserMutation
import com.coffeetime.supplementshop.network.apolloClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class GraphQlStatus { LOADING, ERROR, DONE }

class FragmentLoginViewModel(application: Application) : AndroidViewModel(application) {


    val context = application.applicationContext

    private val _status = MutableLiveData<GraphQlStatus>()

    val status: LiveData<GraphQlStatus>
        get() = _status

    private val _navigateToOverviewFragment = MutableLiveData<Boolean>()

    val navigateToOverviewFragment: LiveData<Boolean>
        get() = _navigateToOverviewFragment

    private val _navigateToRegisterFragment = MutableLiveData<Boolean>()

    val navigateToRegisterFragment: LiveData<Boolean>
        get() = _navigateToRegisterFragment

    val viewModelJob = Job()

    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val token = ""


    fun onNavigateToRegisterFragment() {
        _navigateToRegisterFragment.value = true
    }

    fun onNavigateToRegisterFragmentCompleted() {
        _navigateToRegisterFragment.value = false

    }

    fun onNavigateToOverviewFragment() {
        _navigateToOverviewFragment.value = true
    }

    fun onNavigateToOverviewFragmentCompleted() {
        _navigateToOverviewFragment.value = false

    }


    fun loginUser(email: String, password: String) {
        coroutineScope.launch {
            val response = try {
                apolloClient(context).mutate(SignInUserMutation(email, password)).toDeferred()
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
                return@launch
            }


            if (response.hasErrors()) {
                Toast.makeText(context, response.errors()[0].message(), Toast.LENGTH_SHORT).show()
                return@launch
            } else {
                _navigateToOverviewFragment.value = true
            }
        }

    }


}