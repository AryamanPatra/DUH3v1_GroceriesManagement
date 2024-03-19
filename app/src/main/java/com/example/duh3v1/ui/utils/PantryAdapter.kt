package com.example.duh3v0.ui.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duh3v1.R
import com.example.duh3v1.data.models.Item

class PantryAdapter(
    context: Context,
    private var dataSet: MutableList<Item>,
) :
    RecyclerView.Adapter<PantryAdapter.ViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTvPgi: TextView
        val quantityTvPgi: TextView
        val categoryTvPgi: TextView

        init {
            // Define click listener for the ViewHolder's View
            titleTvPgi = view.findViewById(R.id.titleTvPgi)
            quantityTvPgi = view.findViewById(R.id.quantityTvPgi)
            categoryTvPgi = view.findViewById(R.id.categoryTvPgi)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.pantry_grid_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val element = dataSet[position]
        viewHolder.titleTvPgi.text = element.name
        val displayQuantity = element.qLeft.toString() + element.unit.toString()
        viewHolder.quantityTvPgi.text = displayQuantity
        viewHolder.categoryTvPgi.text = element.category
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun setData(dataSet: List<Item>){
        this.dataSet =  dataSet.toMutableList()
        notifyDataSetChanged()
    }

}