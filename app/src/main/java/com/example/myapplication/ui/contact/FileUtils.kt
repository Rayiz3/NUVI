package com.example.myapplication.ui.contact

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object FileUtils {

    fun appendToJsonFile(context: Context, fileName: String, newData: ContactFirstItem) {
        val gson = Gson()
        val file = File(context.filesDir, fileName)


        // 기존 데이터 읽기
        val existingData: MutableList<ContactFirstItem> = if (file.exists()) {
            val jsonString = file.readText()
            val type = object : TypeToken<MutableList<ContactFirstItem>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            mutableListOf()
        }

        // 새로운 데이터 추가
        existingData.add(newData)

        // 업데이트된 데이터 저장
        val updatedJsonString = gson.toJson(existingData)
        file.writeText(updatedJsonString)
    }

    fun appendToJsonFile2(context: Context, fileName: String, newData2: ContactSecondItem) {
        val gson = Gson()
        val file2 = File(context.filesDir, fileName)


        // 기존 데이터 읽기
        val existingData: MutableList<ContactSecondItem> = if (file2.exists()) {
            val jsonString = file2.readText()
            val type = object : TypeToken<MutableList<ContactSecondItem>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            mutableListOf()
        }

        // 새로운 데이터 추가
        existingData.add(newData2)

        // 업데이트된 데이터 저장
        val updatedJsonString2 = gson.toJson(existingData)
        file2.writeText(updatedJsonString2)
    }

    fun <T> updateJsonFile(context: Context, fileName: String, data: List<T>) {
        val gson = Gson()
        val file = File(context.filesDir, fileName)

        try {
            val jsonString = gson.toJson(data)
            file.writeText(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T> updateJsonFile2(context: Context, fileName: String, data: List<T>) {
        val gson = Gson()
        val file = File(context.filesDir, fileName)

        try {
            val jsonString = gson.toJson(data)
            file.writeText(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
