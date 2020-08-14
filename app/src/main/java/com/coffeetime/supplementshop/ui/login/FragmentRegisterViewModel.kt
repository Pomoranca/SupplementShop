package com.coffeetime.supplementshop.ui.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.coroutines.toDeferred
import com.coffeetime.supplementshop.CreateUserMutation
import com.coffeetime.supplementshop.network.apolloClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/** Enum class to track Register mutation status*/

enum class RegisterStatus { LOADING, ERROR, DONE }

class FragmentRegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<RegisterStatus>()

    val status: LiveData<RegisterStatus>
        get() = _status

    val context = application.applicationContext

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToLoginFragment = MutableLiveData<Boolean>()

    val navigateToLoginFragment: LiveData<Boolean>
        get() = _navigateToLoginFragment

    fun onNavigateToLoginFragmentCompleted() {
        _navigateToLoginFragment.value = false
    }

    fun onNavigateToLoginFragment() {
        Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show()
        _navigateToLoginFragment.value = true

    }

    fun registerUser(name: String, email: String, password: String) {
        coroutineScope.launch {
            _status.value = RegisterStatus.LOADING
            val response =
                try {
                    apolloClient(context).mutate(CreateUserMutation(name, email, password))
                        .toDeferred()
                        .await()

                } catch (e: Exception) {
                    e.printStackTrace()
                    return@launch
                }
            _status.value = RegisterStatus.DONE

            if (response.hasErrors()) {
                _status.value = RegisterStatus.ERROR
                Toast.makeText(context, response.errors()[0].message(), Toast.LENGTH_SHORT).show()
                return@launch
            }
            onNavigateToLoginFragment()
        }
    }
}