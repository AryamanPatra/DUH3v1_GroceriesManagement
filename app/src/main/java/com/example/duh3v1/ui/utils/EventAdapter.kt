package com.example.duh3v1.ui.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duh3v1.R
import com.example.duh3v1.data.models.FoodEvent
import com.example.duh3v1.data.models.Item
import java.util.Calendar

class EventAdapter(
    context: Context,
    private val dataSet: Array<FoodEvent>,
) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTvEli: TextView
        val descTvEli: TextView
        val notifCountTvEli: TextView
        val startDateTvEli: TextView
        val endDateTvEli: TextView
        val itemTagsRvEli: RecyclerView

        init {
            // Define click listener for the ViewHolder's View
            titleTvEli = view.findViewById(R.id.titleTvEli)
            descTvEli = view.findViewById(R.id.descriptionTvEli)
            notifCountTvEli = view.findViewById(R.id.notifCountTvEli)
            startDateTvEli = view.findViewById(R.id.startDateTvEli)
            endDateTvEli = view.findViewById(R.id.endDateTvEli)
            itemTagsRvEli = view.findViewById(R.id.itemTagsRvEli)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.event_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val element = dataSet[position]
        viewHolder.titleTvEli.text = element.title
        viewHolder.descTvEli.text = element.description
        val displayQuantity = element.reminderInterval.toString() + "hr"
        viewHolder.notifCountTvEli.text = displayQuantity
        var dateFormated =
            element.startTime.get(Calendar.DAY_OF_MONTH).toString() + "-" + (element.startTime.get(
                Calendar.MONTH
            ) + 1).toString() + "-" + element.startTime.get(Calendar.YEAR).toString()
        viewHolder.startDateTvEli.text = dateFormated
        dateFormated =
            element.endTime.get(Calendar.DAY_OF_MONTH).toString() + "-" + (element.endTime.get(
                Calendar.MONTH
            ) + 1).toString() + "-" + element.endTime.get(Calendar.YEAR).toString()
        viewHolder.endDateTvEli.text = dateFormated
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}