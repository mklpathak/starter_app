package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.models.DataConverter
import com.example.core.models.Popular

@Database(entities = [Popular.Result::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun popularDao(): PopularDao
}