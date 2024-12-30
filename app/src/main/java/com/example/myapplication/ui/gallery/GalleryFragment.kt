package com.example.myapplication.ui.gallery

import AppDatabase
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
import com.example.myapplication.data.ImageItem

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

        val textTitleView: TextView = binding.galleryTitle
        val textSubtitleView: TextView = binding.gallerySubtitle
        val textViewMain: TextView = binding.textMain

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        val spacing = resources.getDimensionPixelSize(R.dimen.margin_gallery_image)
        var dataSet = listOf(
            ImageItem(0,R.drawable.img1, "title1", "address", "description"),
            ImageItem(0,R.drawable.img2, "title2", "address", "description"),
            ImageItem(0,R.drawable.img3, "title3", "address", "description"),
            ImageItem(0,R.drawable.img4, "title4", "address", "description"),
            ImageItem(0,R.drawable.img5, "title5", "address", "description"),
        )
        // Set the title
        textTitleView.text = getString(R.string.title_gallery)
        textSubtitleView.text = getString(R.string.subtitle_gallery)

        // Set the main text
        // viewLifecycleOwner : Ensures that observation stops when the Fragment's view is destroyed.
        // it : latest value of LiveData.text
        galleryViewModel.text_main.observe(viewLifecycleOwner) {
            textViewMain.text = getString(R.string.main_gallery, dataSet.size)
        }

        // Set the RecyclerView's layout manager and adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = GalleryAdapter(dataSet, parentFragmentManager)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount = 2, spacing = spacing))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}