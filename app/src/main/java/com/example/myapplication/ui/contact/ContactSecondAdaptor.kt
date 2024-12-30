package com.example.myapplication.ui.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ContactItemSecondBinding

class ContactSecondAdapter(
    private val context: Context,
    private val itemList: List<ContactSecondItem>
) : RecyclerView.Adapter<ContactSecondAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ContactItemSecondBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(private val binding: ContactItemSecondBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactSecondItem) {
            binding.name.text = item.name
            binding.phone.text = item.phone
            }
        }
    }

fun setupRecyclerView(recyclerView: RecyclerView, context: Context, items: List<ContactSecondItem>) {
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = ContactSecondAdapter(context, items)
}

