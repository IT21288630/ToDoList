package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.util.joinIntoString
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.database.TodoDatabase
import com.example.myapplication.database.entities.Todo
import com.example.myapplication.database.repositories.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    lateinit var data: List<Todo>
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbToDo: CheckBox
        val ivDelete: ImageView

        init {
            cbToDo = view.findViewById(R.id.cbToDo)
            ivDelete = view.findViewById(R.id.ivDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cbToDo.text = data[position].item
        holder.ivDelete.setOnClickListener {

            if (holder.cbToDo.isChecked) {
                val repository = TodoRepository(TodoDatabase.getInstance(context))
                holder.ivDelete.setImageResource(R.drawable.delete_im)
                holder.cbToDo.isChecked = false

                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(data[position])
                    val data = repository.getAllTodos()

                    withContext(Dispatchers.Main) {
                        setData(data, context)
                        holder.ivDelete.setImageResource(R.drawable.delete_im)
                    }
                }
            } else {

                Toast.makeText(context, "Cannot delete unmarked Todo items", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun setData(data: List<Todo>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
}