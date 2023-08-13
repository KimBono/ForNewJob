package com.example.todolist.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY data DESC")
    fun todos() : Flow<List<Todo>>

    //쓰기는 오래걸리는작업으로 suspend
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insert(todo : Todo)

    @Update
    suspend fun update(todo : Todo)

    @Delete
    suspend fun delete(todo : Todo)
}