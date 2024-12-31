package com.example.myapplication.ui.gallery

import SharedViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentGalleryBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textTitleView: TextView = binding.galleryTitle
        val textSubtitleView: TextView = binding.gallerySubtitle
        val textViewMain: TextView = binding.textMain

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        val spacing = resources.getDimensionPixelSize(R.dimen.margin_gallery_image)
        val dataSet = listOf(
            ImageItem(R.drawable.img1, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img2, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img3, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img4, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img5, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img6, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img7, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img8, "새로운 사진", "새로운 주소", ""),
            ImageItem(R.drawable.img9, "새로운 사진", "새로운 주소", ""),
        )

        // Initialize gallery descriptions in the SharedViewModel
        sharedViewModel.initializeGalleryDescriptions(dataSet)

        // Set the title
        textTitleView.text = getString(R.string.title_gallery)
        textSubtitleView.text = getString(R.string.subtitle_gallery)

        // Set the RecyclerView's layout manager and adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = GalleryAdapter(dataSet, parentFragmentManager, sharedViewModel)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount = 2, spacing = spacing))

        sharedViewModel.galleryDescriptions.observe(viewLifecycleOwner) { descriptions ->
            val updatedDataSet = descriptions.values.toList()
            // Set the main text
            textViewMain.text = getString(R.string.main_gallery, updatedDataSet.size)
            recyclerView.adapter = GalleryAdapter(updatedDataSet, parentFragmentManager, sharedViewModel)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}