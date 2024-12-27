package com.example.myapplication.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text_main = MutableLiveData<String>().apply {
        value = "Gallery"
    }
    val text_main: LiveData<String> = _text_main
}