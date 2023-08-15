package com.example.addressbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.addressbook.domain.AddressItem
import kotlinx.coroutines.internal.synchronized

@Database(entities = [AddressItem::class], version = 1)
abstract class AddressItemDatabase : RoomDatabase() {
    abstract fun addressItemDao(): AddressItemDao
}


