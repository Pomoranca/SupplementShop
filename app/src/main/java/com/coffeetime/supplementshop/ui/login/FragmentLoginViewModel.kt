package com.coffeetime.supplementshop.ui.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.coroutines.toDeferred
import com.coffeetime.supplementshop.SignInUserMutation
import com.coffeetime.supplementshop.network.User
import com.coffeetime.supplementshop.network.apolloClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/** Enum class to track Login mutation status */
enum class LoginStatus { LOADING, ERROR, DONE }

class FragmentLoginViewModel(application: Application) : AndroidViewModel(application) {


    val context = application.applicationContext

    private val _status = MutableLiveData<LoginStatus>()

    val status: LiveData<LoginStatus>
        get() = _status

    private val _navigateToHomeActivity = MutableLiveData<Boolean>()

    val navigateToHomeActivity: LiveData<Boolean>
        get() = _navigateToHomeActivity

    private val _navigateToRegisterFragment = MutableLiveData<Boolean>()

    val navigateToRegisterFragment: LiveData<Boolean>
        get() = _navigateToRegisterFragment

    private val viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun onNavigateToRegisterFragment() {
        _navigateToRegisterFragment.value = true
    }

    fun onNavigateToRegisterFragmentCompleted() {
        _navigateToRegisterFragment.value = false

    }

    fun onNavigateToHomeActivity() {
        _navigateToHomeActivity.value = true
    }

    fun onNavigateToHomeActivityCompleted() {
        _navigateToHomeActivity.value = false

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
                val token = response.data()!!.signinUser.token!!
                User.setToken(context, token)
                onNavigateToHomeActivity()
            }
        }
    }



}