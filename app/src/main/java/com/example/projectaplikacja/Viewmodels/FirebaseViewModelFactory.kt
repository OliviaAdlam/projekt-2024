package com.example.projectaplikacja.Viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirebaseViewModelFactory(val application: Application):
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return FirebaseViewModel(application) as T
    }
}