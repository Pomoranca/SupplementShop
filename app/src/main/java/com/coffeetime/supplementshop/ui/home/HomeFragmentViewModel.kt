package com.coffeetime.supplementshop.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext

    val viewModelJob = Job()

    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun showData() {
        coroutineScope.launch {
//            val response =

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}