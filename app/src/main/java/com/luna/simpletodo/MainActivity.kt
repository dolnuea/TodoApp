package com.luna.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>() //values are variables that cannot be assigned again, so we changed it to variable
    lateinit var adapter : TaskItemAdapter // lateinit tells the compiler it'll be initialized later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                listOfTasks.removeAt(position)
                // notify the adapter the list has change
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        loadItems() //before creating the recyclerview

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskText)
        val button = findViewById<Button>(R.id.addTaskButton)

        //adding new task
        button.setOnClickListener{ v ->
            //grab the text the user has inputted to id add task field
            val userInputtedTask = inputTextField.text.toString()
            //add string to our list of tasks: list of tasks
            listOfTasks.add(userInputtedTask)

            //notify the adapter that list is updated
            adapter.notifyItemInserted(listOfTasks.size - 1) //last added item

            //reset text field everytime new task is added
            inputTextField.setText("")

            saveItems()
        }
     //editing task
    }

    //save the data the user has inputted
    //save a data by writing and reading from a file
    //create a method to get the file we need
    fun getDataFile() : File {
        //every line represents a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }
    //load the items by reading every line in the data files
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException : IOException){
            ioException.printStackTrace()
        }
    }
    //save items by writing them into our data file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException : IOException){
            ioException.printStackTrace()
        }
    }
}