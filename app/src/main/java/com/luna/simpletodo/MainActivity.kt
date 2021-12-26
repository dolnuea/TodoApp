package com.luna.simpletodo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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

        //remove item
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                listOfTasks.removeAt(position)
                // notify the adapter the list has change
                adapter.notifyDataSetChanged()

                Toast.makeText(this@MainActivity, "Task is removed", Toast.LENGTH_SHORT).show()
                saveItems()
            }
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val editedTask = data?.getStringExtra("editedTaskName").toString()
                val position = data?.getIntExtra("position", 0)
                listOfTasks[position!!] = editedTask
                adapter.notifyItemChanged(position)
                saveItems()
                Toast.makeText(this@MainActivity, "Task is updated", Toast.LENGTH_SHORT).show()
            }
            else {
                Log.w("MainActivity", "Unknown call to onActivityResult");
            }
        }

        val intent = Intent(this, EditTaskActivity::class.java)

        //editing task
        val onClickListener = object : TaskItemAdapter.OnClickListener {
            override fun onItemClicked(position: Int) {
                intent.putExtra("taskName", listOfTasks[position])
                intent.putExtra("position", position)
                resultLauncher.launch(intent)
            }
        }

        loadItems() //before creating the recyclerview

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onClickListener)
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

            //reset text field every time new task is added
            inputTextField.setText("")
            Toast.makeText(this@MainActivity, "Task is added", Toast.LENGTH_SHORT).show()
            saveItems()
        }
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
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
    //save items by writing them into our data file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}