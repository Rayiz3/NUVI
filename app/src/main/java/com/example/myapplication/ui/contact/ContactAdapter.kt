package com.example.myapplication.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ContactItemBinding

class ContactAdapter(private val dataset: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contact) {
            with(binding) {
                name.text = data.name
                phone.text = data.phone
            }
        }
    }
}