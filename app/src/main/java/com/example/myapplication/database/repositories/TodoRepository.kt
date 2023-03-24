package com.example.myapplication.database.repositories

import com.example.myapplication.database.TodoDatabase
import com.example.myapplication.database.entities.Todo

class TodoRepository(private val db: TodoDatabase) {
    suspend fun insert(todo: Todo) = db.getTodoDao().insertToDo(todo)
    suspend fun delete(todo: Todo) = db.getTodoDao().delete(todo)
    fun getAllTodos() = db.getTodoDao().getAllTodos()


}