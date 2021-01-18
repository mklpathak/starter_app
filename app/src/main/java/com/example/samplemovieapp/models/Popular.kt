package com.example.samplemovieapp.models

import androidx.room.*
import com.example.samplemovieapp.Constants
import com.google.gson.Gson

data class Popular(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) : BaseModel() {
    override fun getViewType(): Int {
        return Constants.MOVIES
    }

    @Entity(tableName = "popular")
    data class Result(
        val adult: Boolean,
        val backdrop_path: String,
        @TypeConverters(DataConverter::class)
        val genre_ids: List<Int>,
        @PrimaryKey
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int,
        var categories :String = ""
    ): BaseModel() {
        override fun getViewType(): Int {
            return Constants.MOVIE
        }
    }
}

