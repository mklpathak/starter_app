package com.example.samplemovieapp.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromIntArrayList(value: ArrayList<Int>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Int>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntArrayList(value: String): ArrayList<Int> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Int>>() {}.type
        return gson.fromJson(value, type)
    }





}