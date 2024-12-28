package com.example.myapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentGalleryBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        val textViewMain: TextView = binding.textMain

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        val dataSet = arrayOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5
        )
        //val dataSet = arrayOf("Image 1", "Image 2", "Image 3", "Image 4", "Image 5") // Example data

        // Set the main text
        // viewLifecycleOwner : Ensures that observation stops when the Fragment's view is destroyed.
        // it : latest value of LiveData.text
        galleryViewModel.text_main.observe(viewLifecycleOwner) {
            textView.text = it
            textViewMain.text = getString(R.string.main_gallery, dataSet.size)
        }

        // Set the RecyclerView's layout manager and adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3) // 3 columns
        recyclerView.adapter = GalleryAdapter(dataSet)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}