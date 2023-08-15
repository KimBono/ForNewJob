package com.example.addressbook.data

import android.app.Application
import androidx.room.Room
import com.example.addressbook.domain.AddressItem
import com.example.addressbook.domain.AddressItemRepository
import kotlinx.coroutines.flow.Flow

class AddressitemRepositoryImpl(
    application: Application
) : AddressItemRepository {

    private val db = Room.databaseBuilder(
        application,
        AddressItemDatabase::class.java,
    "address-db"
    ).build()

    override fun getItemList(): Flow<List<AddressItem>> {
        return db.addressItemDao().getList()
    }

    override suspend fun addItem(item: AddressItem) {
        return db.addressItemDao().insert(item)
    }

    override suspend fun deleteItem(item: AddressItem) {
        return db.addressItemDao().delete(item)
    }

    override suspend fun updateItem(item: AddressItem) {
        return db.addressItemDao().update(item)
    }
}