package com.p.randomstringgenerator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StringGenDao {
    @Insert
    suspend fun insert(string: GeneratedString)

    @Query("SELECT * FROM generated_strings ORDER BY timestamp DESC")
    fun getAllStrings(): Flow<List<GeneratedString>>

    @Query("DELETE FROM generated_strings")
    suspend fun deleteAllStrings()

    @Query("DELETE FROM generated_strings WHERE id = :id")
    suspend fun deleteStringById(id: Long)
}