package com.example.addressbook.ui

import android.app.Application
import android.location.Address
import android.provider.Telephony.Mms.Addr
import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.addressbook.R
import com.example.addressbook.domain.AddressItem
import com.example.addressbook.domain.AddressItemRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val addressItemRepository: AddressItemRepository
) : AndroidViewModel(application){

    private var _items = mutableStateOf(emptyList<AddressItem>())
    val items : State<List<AddressItem>> = _items

    lateinit var _DetailInfo : AddressItem

    init {
        viewModelScope.launch {
            addressItemRepository.getItemList().collect{
                it -> _items.value = it
            }
        }
    }

    fun addItem(item : AddressItem){
        viewModelScope.launch {
            addressItemRepository.addItem(item)
        }
    }

    fun deleteItem(uid : Int){
        val addressItem = _items.value.find{ addressItem -> addressItem.uid == uid}
        addressItem?.let{
            viewModelScope.launch {
                addressItemRepository.deleteItem(it)
            }
        }
    }

    fun changePhoneNum(name : String, changedPhoneNumber: String){
        val addressItem = _items.value.find{
                addressItem -> addressItem.name == name
        }
        addressItem?.let{
            viewModelScope.launch {
                addressItemRepository.updateItem(it.copy(phoneNumber = changedPhoneNumber).apply {
                    this.uid = it.uid
                })
            }
        }
    }

    fun searchAddressItem(name : String) : AddressItem? {
        val addressItem = _items.value.find{
                addressItem -> addressItem.name == name
        }
        if(addressItem != null){
            return  addressItem
        }
        return null
    }

    fun setItem(item : AddressItem)  {
        _DetailInfo= item
    }

    fun getDetailItemInfo() : AddressItem {
        return _DetailInfo
    }

}