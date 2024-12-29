package com.example.myapplication.data

import ImageItem
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDAO {
    @Query("SELECT * FROM images")
    fun getAll(): List<ImageItem>

    @Insert
    fun insertAll(vararg images: ImageItem)

    @Delete
    fun delete(user: ImageItem)
}