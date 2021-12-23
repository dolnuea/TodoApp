package com.luna.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val listOfTasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfTasks.add("meow")

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        val adapter = TaskItemAdapter(listOfTasks)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    /**
     * Add
     */
    fun addTask(savedInstanceState: Bundle?){
        //todo
    }

    /**
     * Remove
     */
    fun removeTask(){
        //todo
    }

    /**
     * Edit
     */
    fun editTask(){

    }
}