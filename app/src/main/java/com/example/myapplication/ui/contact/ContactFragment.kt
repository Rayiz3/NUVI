package com.example.myapplication.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentContactBinding
import com.example.myapplication.ui.calendar.CalendarViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)

        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textTitleView: TextView = binding.contactTitle
        val textSubtitleView: TextView = binding.contactSubtitle

        textTitleView.text = getString(R.string.title_contact)
        textSubtitleView.text = getString(R.string.subtitle_contact)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testdata = getJsonData("Contact.json")

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ContactAdapter(testdata!!)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun getJsonData(fileName: String): List<Contact>? {
        val assetManager = resources.assets
        var result: Contact? = null
        return try {
            val inputStream = assetManager.open(fileName)
            val reader = inputStream.bufferedReader()
            val gson = Gson()
            val listType = object : TypeToken<List<Contact>>() {}.type
            gson.fromJson(reader, listType)
        } catch (e:IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}