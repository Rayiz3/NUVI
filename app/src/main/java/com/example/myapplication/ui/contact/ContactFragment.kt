package com.example.myapplication.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentContactBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)

        setUpFirstRecyclerView()
        setUpSecondRecyclerView()

        val textTitleView: TextView = binding.contactTitle
        val textSubtitleView: TextView = binding.contactSubtitle

        textTitleView.text = getString(R.string.title_contact)
        textSubtitleView.text = getString(R.string.subtitle_contact)

        return binding.root
    }

    private fun setUpFirstRecyclerView() {
        val firstData = parseJsonData<ContactFirstItem>("ContactFirst.json")
        binding.firstRecyclerView.adapter = ContactFirstAdapter(requireContext(), firstData ?: emptyList())
        binding.firstRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpSecondRecyclerView() {
        val secondData = parseJsonData<ContactSecondItem>("ContactSecond.json")
        binding.secondRecyclerView.adapter = ContactSecondAdapter(requireContext(), secondData ?: emptyList())
        binding.secondRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private inline fun <reified T> parseJsonData(fileName: String): List<T>? {
        return try {
            val inputStream = requireContext().assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson<List<T>>(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
