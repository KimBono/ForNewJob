package com.example.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class Todo(
    val title : String,
    val data : Long = Calendar.getInstance().timeInMillis,
    val isDone : Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0
}