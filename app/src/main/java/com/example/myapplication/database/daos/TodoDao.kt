package com.example.myapplication.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entities.Todo

@Dao
interface TodoDao {
    @Insert
    suspend fun insertToDo(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM Todo")
    fun getAllTodos(): List<Todo>

}