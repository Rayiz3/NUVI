package com.example.myapplication.ui.gallery

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.ImageItem

class GalleryAdapter(
    private var dataSet: List<ImageItem>,
    private val fragmentManager: FragmentManager
): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.imageView)
        }
    }

    /* overrided methods (invoked by the layout manager) */

    // 1. onCreateViewHolder()
    // Create new view holder & views
    // But there is no contents in the views yet
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view based on image_row_item.xml or text_row_item.xml
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // 2. onBindViewHolder()
    // Fill the contents of a view
    // Get element from the dataset at the position and
    // replace the contents of the view with that element
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.imageView.setImageResource(item.imageResId)

        // Show dialog on click
        viewHolder.imageView.setOnClickListener {
            DialogFragment(
                item.imageResId,
                item.title,
                item.address,
                item.description
            ) { updatedTitle, updatedAddress, updatedDescription ->
                // Save the updated values back to the dataset
                dataSet = dataSet.toMutableList().apply {
                    this[position] = item.copy(
                        title = updatedTitle,
                        address = updatedAddress,
                        description = updatedDescription
                    )
                }
                // Notify adapter to refresh the view
                notifyItemChanged(position)
            }.show(fragmentManager, "CustomDialog")
        }
    }

    // 3. getItemCount()
    // Return the size of your dataset
    override fun getItemCount() = dataSet.size

}