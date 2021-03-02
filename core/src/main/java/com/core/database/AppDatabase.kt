package com.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.core.models.DataConverter
import com.core.models.Popular

@Database(entities = [Popular.Result::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun popularDao(): PopularDao
}