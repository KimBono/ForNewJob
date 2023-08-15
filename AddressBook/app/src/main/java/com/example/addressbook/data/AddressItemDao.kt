package com.example.addressbook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.addressbook.domain.AddressItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (item : AddressItem)

    @Delete
    suspend fun delete (item : AddressItem)

    @Update
    suspend fun update(item : AddressItem)

    @Query("SELECT * FROM addressitem")
    fun getList() : Flow<List<AddressItem>>
}