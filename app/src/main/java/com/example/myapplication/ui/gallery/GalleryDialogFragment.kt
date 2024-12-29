package com.example.myapplication.ui.gallery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class DialogFragment(private val imageResId: Int, private val title: String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.fragment_gallery_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the image and title
        val imageView: ImageView = view.findViewById(R.id.dialogImage)
        val titleView: TextView = view.findViewById(R.id.dialogTitle)

        imageView.setImageResource(imageResId)
        titleView.text = title
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(320.dpToPx(requireContext()), 600.dpToPx(requireContext()))
        dialog?.setCanceledOnTouchOutside(true)
    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()
}