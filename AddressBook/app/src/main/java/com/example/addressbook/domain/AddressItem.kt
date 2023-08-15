package com.example.addressbook.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressItem(
    val name : String,
    val phoneNumber : String
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
}