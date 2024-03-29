package com.core.database

import androidx.room.*
import com.core.models.Popular
import kotlinx.coroutines.flow.Flow


@Dao
interface PopularDao {

    @Query("SELECT * FROM popular")
    fun getAll(): Flow<List<Popular.Result>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<Popular.Result>)


    @Query("SELECT * from popular WHERE id= :id")
    suspend fun getItemById(id: Int): List<Popular.Result>

    @Delete
    suspend fun delete(user: Popular.Result)


    @Query("UPDATE popular SET categories = :categories WHERE id = :id")
    suspend fun updateCategories(id: Int,categories:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: Popular.Result)

    @Query("SELECT * FROM popular WHERE categories LIKE :search")
    fun fetchByCategory(search: String?):Flow<List<Popular.Result>>



}