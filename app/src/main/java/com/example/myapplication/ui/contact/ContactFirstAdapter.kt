package com.example.myapplication.ui.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ContactItemFirstBinding

class ContactFirstAdapter(
    val context: Context,
    val itemList: List<ContactFirstItem>
): RecyclerView.Adapter<ContactFirstAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ContactItemFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(var binding: ContactItemFirstBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactFirstItem) {
            binding.name.text = item.name
            binding.phone.text = item.phone
        }
    }

}