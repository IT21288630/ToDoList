package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.VH>() {

    class VH(view : View) : RecyclerView.ViewHolder(view){

        val cbToDo : CheckBox
        val ivDelete : ImageView

        init {
            cbToDo = view.findViewById(R.id.cbToDo)
            ivDelete = view.findViewById(R.id.ivDelete)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)

        return VH(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.cbToDo.text = "Sample text"
    }
}