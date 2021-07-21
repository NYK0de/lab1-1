package com.curso.labs.readsms.fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SMSViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SMSViewModel::class.java)){
            return SMSViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}