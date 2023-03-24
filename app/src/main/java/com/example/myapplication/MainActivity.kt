package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.ToDoAdapter
import com.example.myapplication.database.TodoDatabase
import com.example.myapplication.database.entities.Todo
import com.example.myapplication.database.repositories.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = TodoRepository(TodoDatabase.getInstance(this))
        val ui = this
        val recyclerView: RecyclerView = findViewById(R.id.rvToDoList)
        val adapter = ToDoAdapter()
        val btnAddTodo = findViewById<Button>(R.id.btnAddToDo)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        CoroutineScope(Dispatchers.IO).launch{
            val data = repository.getAllTodos()
            adapter.setData(data, ui)
        }

        btnAddTodo.setOnClickListener{
            displayDialog(repository, adapter)
        }
    }

    fun displayDialog(repository: TodoRepository, adapter: ToDoAdapter){
        //create a new instance of Alert.Dialog.Builder
        val builder = AlertDialog.Builder(this)

        //set the alert dialog title and the message
        builder.setTitle("Enter New Todo item:")
        builder.setMessage("Enter the todo item below:")

        //create an EditText input field
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        //set the positive button action
        builder.setPositiveButton("OK"){ dialog, which->
            //get the input text and display a Toast message
            val item = input.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Todo(item))
                val data = repository.getAllTodos()
                runOnUiThread{
                    adapter.setData(data, this@MainActivity)
                }
            }
        }

        //set the negative button action
        builder.setNegativeButton("Cancel") { dialog, which->
            dialog.cancel()
        }

        //create and show the alert dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }
}