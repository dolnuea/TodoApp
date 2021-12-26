package com.luna.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * bridge that displays data given on recycleview
 */
class TaskItemAdapter (private val listOfItems: List<String>,
                       val longClickListener: OnLongClickListener,
                       val clickListener: OnClickListener)
                        : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface  OnLongClickListener {
        // pass in position of the item that is long clicked
        fun onItemLongClicked(position : Int)
    }

    interface OnClickListener {
        fun onItemClicked(position : Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(android.R.id.text1)

        init {

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
            }
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: TaskItemAdapter.ViewHolder, position: Int) {

        //get data model based on pos
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

}