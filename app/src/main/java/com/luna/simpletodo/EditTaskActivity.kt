package com.luna.simpletodo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task_activity)

        val editTaskField = findViewById<EditText>(R.id.editTask)
        editTaskField.setText(intent.getStringExtra("taskName"))

        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val editedTask = editTaskField.text.toString()
            intent.putExtra("editedTaskName", editedTask)
            intent.extras?.let { it1 -> intent.putExtra("position", it1.getInt("position")) }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}