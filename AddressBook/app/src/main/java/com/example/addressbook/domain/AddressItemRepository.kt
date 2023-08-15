package com.example.addressbook.domain

import kotlinx.coroutines.flow.Flow

interface AddressItemRepository {

    fun getItemList() : Flow<List<AddressItem>>

    suspend fun addItem(item : AddressItem)

    suspend fun deleteItem(item : AddressItem)

    suspend fun updateItem(item : AddressItem)
}