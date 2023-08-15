package com.example.addressbook.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.addressbook.data.AddressitemRepositoryImpl
import com.example.addressbook.domain.AddressItemRepository
import com.example.addressbook.ui.MainViewModel

class ViewModelFactory(
    private val application : Application,
    private val repository : AddressItemRepository = AddressitemRepositoryImpl(application)
)  : ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application,repository) as T
        }
        return super.create(modelClass)
    }
}