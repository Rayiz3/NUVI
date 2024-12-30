package com.example.myapplication.ui.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentContactBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Data Binding 초기화
        binding = FragmentContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ContactFragment를 Activity에 연결
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ContactFragment())
            .commit()
    }
}
